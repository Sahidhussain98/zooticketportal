package com.practice.zooticketportal.controllers;


import com.practice.zooticketportal.entity.*;
import com.practice.zooticketportal.repositories.*;
import com.practice.zooticketportal.service.AllUserService;
import com.practice.zooticketportal.service.EstablishmentService;
import com.practice.zooticketportal.service.TicketService;
import com.practice.zooticketportal.serviceimpl.NonWorkingDaysService;
import jakarta.validation.constraints.Email;
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

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class TicketController {

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


    @PostMapping("/saveTicket/{establishmentId}")
    public String processForm(
            @PathVariable Long establishmentId,
            @RequestParam("dateTime") String dateTime,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            Model model) {

        // Retrieve establishment name
        Establishment establishment = establishmentService.getEstablishmentById(establishmentId);
        String establishmentName = establishment.getName();

        // Generate random serial number
        int serialNumber = generateRandomSerialNumber();

        // Create a new Ticket object and set the fields
        Ticket theTicket = new Ticket();
        theTicket.setDateTime(LocalDate.parse(dateTime));
        theTicket.setName(name);
        theTicket.setEmail(email);
        theTicket.setPhoneNumber(Long.valueOf(phoneNumber));
        theTicket.setBookingId(establishmentName + "-" + serialNumber);
        theTicket.setEnteredOn(LocalDateTime.now());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        String enteredBy = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")) ? "admin" : currentUserName;
        theTicket.setEnteredBy(enteredBy);

        // Log the ticket details
        System.out.print("Ticket details: " + theTicket.toString());

        // Save the ticket details to the database using the repository
        Ticket savedTicket = ticketRepository.save(theTicket);

        // Add establishment object and the saved ticket to the model
        model.addAttribute("establishment", establishment);
        model.addAttribute("theTicket", savedTicket);

        return "ticketDownload";
    }

    private int generateRandomSerialNumber() {
        // Implement your logic to generate a random serial number
        return (int) (Math.random() * 1000); // Example logic: Generate a random number between 0 and 999
    }

    @GetMapping("/showEditForm/{ticketId}")
    public String showEditForm(@PathVariable Long ticketId, Model model) {
        // Retrieve ticket details by ID
        Ticket theTicket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        // Retrieve the establishment associated with the ticket
        Establishment establishment = theTicket.getEstablishment();

        // Retrieve the fees associated with the establishment

        // Retrieve the nationality and category associated with the ticket

        // Add ticket object to the model
        model.addAttribute("theTicket", theTicket);
        List<Category> categories = categoryRepo.findAll();
        List<Nationality> nationalities1 = nationalityRepo.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("nationalities", nationalities1);


        return "ticket";
    }


    @PostMapping("/processEditForm")
    public String processEditForm(@ModelAttribute("theTicket") Ticket updatedTicket) {
        // Retrieve the existing ticket from the database
        Ticket existingTicket = ticketRepository.findById(updatedTicket.getId()).orElseThrow(() -> new RuntimeException("Ticket not found"));

        // Update the existing ticket with new details
//        existingTicket.setName(updatedTicket.getname());
//        existingTicket.setLastName(updatedTicket.getLastName());
//        existingTicket.setCategory(updatedTicket.getCategory());
//        existingTicket.setCountry(updatedTicket.getCountry());
        existingTicket.setDateTime(updatedTicket.getDateTime());


        // Save the updated ticket to the database
        ticketRepository.save(existingTicket);

        return "checkoutConfirmation-form";
    }

    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPdfReport(@RequestParam("id") Long id) {
        Ticket ticket = ticketRepository.findTicketById(id);
        if (ticket == null) {
            return ResponseEntity.badRequest().body(("Ticket not found for ID: " + id).getBytes());
        }

        byte[] pdfBytes = null;
        boolean emailSent = false;
        String emailErrorMessage = "";

        try {
            // Export PDF report
            pdfBytes = ticketService.exportReport("pdf", ticket).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error exporting PDF report".getBytes());
        }

        try {
            // Send confirmation email
            ticketService.confirmBooking(ticket.getEmail(), pdfBytes);
            emailSent = true;
        } catch (Exception e) {
            e.printStackTrace();
            emailErrorMessage = "Error sending confirmation email.";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "ticket.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        headers.add("X-Email-Sent", String.valueOf(emailSent));

        if (!emailErrorMessage.isEmpty()) {
            headers.add("X-Email-Error", emailErrorMessage);
        }

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
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



















