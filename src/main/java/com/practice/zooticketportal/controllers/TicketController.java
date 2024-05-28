package com.practice.zooticketportal.controllers;


import com.practice.zooticketportal.entity.*;
import com.practice.zooticketportal.repositories.*;
import com.practice.zooticketportal.service.AllUserService;
import com.practice.zooticketportal.service.EstablishmentService;
import com.practice.zooticketportal.service.TicketService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.io.InputStream;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class TicketController {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private EstablishmentService establishmentService;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private NationalityRepo nationalityRepo;
    @Autowired
    private EstablishmentRepo establishmentRepo;
    @Autowired
    private AllUserService allUserService;
    @Autowired
    private FeesRepo feesRepo;
    @Autowired
    private OtherFeesRepo otherFeesRepo;
    @Autowired
    private AllUserRepo allUserRepo;
    @Autowired
    private categoriesForTicketRepo categoriesForTicketRepo;
    @Autowired
    private OtherFeesForTicketsRepo otherFeesForTicketsRepo;

    @GetMapping("/showCheckoutForm/{establishmentId}")
    public String showCheckoutForm(@PathVariable Long establishmentId,
                                   Model model,
                                   Principal principal) {
        if (principal != null) {
            String phoneNumberStr = principal.getName(); // The phone number is used as the principal
            Long phoneNumber = Long.parseLong(phoneNumberStr);

            // Retrieve user details based on the authenticated user's phone number
            AllUser user = allUserRepo.findByPhoneNumber(phoneNumber);

            // Placeholder for email
            String email = user.getEmail();

            // Retrieve establishment details based on establishmentId
            Establishment establishment = establishmentService.getEstablishmentById(establishmentId);

            // Create a ticket object
            Ticket ticket = new Ticket();
            ticket.setEmail(email);
            ticket.setPhoneNumber(phoneNumber);

            // Add ticket and establishment objects to the model
            model.addAttribute("theTicket", ticket);
            model.addAttribute("establishment", establishment);
            model.addAttribute("phoneNumber", phoneNumber);
            model.addAttribute("email", email);

            // Fetch nationalities and categories with associated entry fees
            List<Nationality> nationalitiesWithFees = feesRepo.findNationalitiesWithFees(establishmentId);
            List<Category> categoriesWithFees = feesRepo.findCategoriesWithFees(establishmentId);
            List<OtherFees> otherFees = otherFeesRepo.findByEstablishmentEstablishmentId(establishmentId);

            // Add filtered nationalities and categories to the model
            model.addAttribute("nationalities", nationalitiesWithFees);
            model.addAttribute("categories", categoriesWithFees);
            model.addAttribute("otherFees", otherFees);

            return "ticket";
        } else {
            // Handle case when principal is null
            return "error"; // or whatever error handling you need
        }
    }
    @PostMapping("/saveTicket")
    public String saveTicket(@RequestParam("establishmentId") Long establishmentId,
                             @RequestParam("dateTime") LocalDate dateTime,
                             @RequestParam("userName") String userName,
                             @RequestParam("email") String email,
                             @RequestParam("phoneNumber") String phoneNumber,
                             @RequestParam("nationalityId") List<Long> nationalityIds,
                             @RequestParam("categoryId") List<Long> categoryIds,
                             @RequestParam("numberOfPeople") List<Long> numberOfPeople,
                             @RequestParam("feesType") List<String> feesType,
                             Model model) {
        Establishment establishment = establishmentService.getEstablishmentById(establishmentId);
        String establishmentName = establishment.getName();
        int serialNumber = generateRandomSerialNumber();
        try {
            Ticket theTicket = new Ticket();
            theTicket.setDateTime(dateTime.atStartOfDay());
            theTicket.setUserName(userName);
            theTicket.setEmail(email);
            theTicket.setPhoneNumber(Long.valueOf(phoneNumber));
            theTicket.setBookingId(establishmentName + "-" + serialNumber);
            theTicket.setEstablishment(establishment);

            ticketRepository.save(theTicket);

            for (int i = 0; i < nationalityIds.size(); i++) {
                Long nationalityId = nationalityIds.get(i);
                Long categoryId = categoryIds.get(i);
                categoriesForTicket entryFee = new categoriesForTicket();
                entryFee.setTicket(theTicket);
                entryFee.setNationality(nationalityRepo.findById(nationalityId).orElse(null));
                entryFee.setCategory(categoryRepo.findById(categoryId).orElse(null));
                entryFee.setQuantity(numberOfPeople.get(i));
                categoriesForTicketRepo.save(entryFee);
            }
//
            for (int i = 0; i < feesType.size(); i++) {
                OtherFeesForTickets otherFee = new OtherFeesForTickets();
                otherFee.setTicket(theTicket);

                String otherfeess = feesType.get(i);
                System.out.println(otherfeess);
                OtherFees otherFees = otherFeesRepo.findByFeesTypeAndEstablishmentId(otherfeess, establishmentId);

                if (otherFees != null) {
                    System.out.println("Found fees type: " + otherFees.getFeesType());
                    otherFee.setOtherFees(otherFees);
                    otherFeesForTicketsRepo.save(otherFee);
                } else {
                    System.out.println("Fees type not found: " + otherfeess);
                    throw new Exception("Fees type not found: " + otherfeess);
                }
            }

            model.addAttribute("theTicket", theTicket);
            model.addAttribute("message", "Ticket saved successfully!");
            return "ticketDownload";
        } catch (Exception e) {
            e.printStackTrace(); // This will print the stack trace to the server logs
            model.addAttribute("error", "Error saving ticket: " + e.getMessage());
            return  "/errorpage/error";
        }
    }
    private int generateRandomSerialNumber() {
        // Implement your logic to generate a random serial number
        return (int) (Math.random() * 1000); // Example logic: Generate a random number between 0 and 999
    }




    @GetMapping("/export/pdf")
    public void generateTicketReport(@RequestParam("id") Long ticketId, HttpServletResponse response) {
        try {
            // Load JrXML template
            InputStream jrxmlInput = getClass().getResourceAsStream("/static/reports/ZooTicket.jrxml");
            // Compile JrXML template
            JasperDesign jasperDesign = JRXmlLoader.load(jrxmlInput);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            // Fill the report with data
            Map<String, Object> parameters = new HashMap<>();
            System.out.println(ticketId);
            parameters.put("id", ticketId);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());
            // Export report to PDF
            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            // Set response headers
            response.setContentType("application/pdf");
            response.setContentLength(pdfBytes.length);
            response.setHeader("Content-Disposition", "attachment; filename=\"ticket_report.pdf\"");
            // Write PDF content to response
            System.out.println(pdfBytes);
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/fetchFee")
    public ResponseEntity<Map<String, Double>> fetchFee(@RequestParam(required = false) Long nationalityId,
                                                        @RequestParam(required = false) Long categoryId,
                                                        @RequestParam("establishmentId") Long establishmentId,
                                                        @RequestParam("numberOfPeople") Long numberOfPeople) {
        // Query the database to retrieve the list of entry fees based on the provided nationality ID, category ID, and establishment ID
        List<Fees> feesList = feesRepo.findByNationalityNationalityIdAndCategoryCategoryIdAndEstablishmentEstablishmentId(nationalityId, categoryId, establishmentId);

        // Check if fees are retrieved successfully
        if (!feesList.isEmpty()) {
            // Get the entry fee from the first fee (assuming the combination is unique)
            double entryFee = feesList.get(0).getEntryFee();

            // Calculate the total fees
            double totalFees = entryFee * numberOfPeople;

            // Create a map to hold the entry fee and total fees
            Map<String, Double> responseMap = new HashMap<>();
            responseMap.put("entryFee", entryFee);
            responseMap.put("totalFees", totalFees);

            // Return the entry fee and total fees
            return ResponseEntity.ok(responseMap);
        } else {
            // If no fees are retrieved, return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}