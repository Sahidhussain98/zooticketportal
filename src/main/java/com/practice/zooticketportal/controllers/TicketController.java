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

    @GetMapping("/showCheckoutForm/{establishmentId}")
    public String showCheckoutForm(@PathVariable Long establishmentId,
                                   Model model,
                                   Principal principal) {
        // Retrieve authenticated user's username
        String username = principal.getName();
//        System.out.println(username);

        // Retrieve user details from the database using the username
        AllUser user = allUserService.findByUsername(username);

        // Placeholder for phone number and email
        String email = user.getEmail();
        String phoneNumber = String.valueOf(user.getPhoneNumber());
        // Check if any user was found
//        System.out.print("phoneNumber"+phoneNumber);
//        System.out.print("email"+email);

        // Here, you can add logic to retrieve the establishment details
        // based on the establishmentId from the database
        Establishment establishment = establishmentService.getEstablishmentById(establishmentId);

        // Create a ticket object
        Ticket ticket = new Ticket();
        ticket.setEmail(email);
        ticket.setPhoneNumber(Long.parseLong(phoneNumber));


        // Add ticket and establishment objects to the model
        model.addAttribute("theTicket", ticket);
        System.out.println("Ticket "+ticket);
        model.addAttribute("establishment", establishment);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("email", email);

        // add the list of countries to the model
        // Fetch nationalities and categories with associated entry fees
        List<Nationality> nationalitiesWithFees = feesRepo.findNationalitiesWithFees(establishmentId);
        List<Category> categoriesWithFees = feesRepo.findCategoriesWithFees(establishmentId);
        List<OtherFeesType> otherFeesTypeWithFees = otherFeesRepo.findOtherFeesTypeWithFees(establishmentId);


        // Add filtered nationalities and categories to the model
        model.addAttribute("nationalities", nationalitiesWithFees);
        model.addAttribute("categories", categoriesWithFees);
        model.addAttribute("otherFeesType", otherFeesTypeWithFees);

        return "ticket";
    }


    @PostMapping("/processCheckoutForm/{establishmentId}")
    public String processForm(@PathVariable Long establishmentId, @ModelAttribute("theTicket") Ticket theTicket, Model model) {
        // Retrieve establishment name
        Establishment establishment = establishmentService.getEstablishmentById(establishmentId);
        String establishmentName = establishment.getName();

        System.out.print("print please "+theTicket.toString());

        // Generate random serial number (you can use your preferred method to generate the serial number)
        int serialNumber = generateRandomSerialNumber();

        // Generate bookingId using establishment name and serial number
        theTicket.setBookingId(new Ticket(establishmentName, serialNumber).getBookingId());

        // Save the ticket details to the database using the repository
        ticketRepository.save(theTicket);

        // Add establishment object to the model
        model.addAttribute("establishment", establishment);

        // Log the input data
//        System.out.println("theTicket: " + theTicket.getname() + " " + theTicket.ame());

        return "ticketDownload";
    }

//     Method to generate random serial number (you can implement your own logic)
    private int generateRandomSerialNumber() {
        // Implement your logic to generate a random serial number
        return (int) (Math.random() * 1000); // Example logic: Generate a random number between 0 and 999
    }

//    @PostMapping("/processCheckoutForm/{establishmentId}")
//    public String processCheckoutForm(@PathVariable("establishmentId") Long establishmentId, @ModelAttribute Map<String, Object> formData, Model model) {
//        // Process the form data (save to database, perform calculations, etc.)
//
//        // Pass the establishment ID to the confirmation page
//        model.addAttribute("establishmentId", establishmentId);
//        // Pass the form data to the confirmation page
//        model.addAttribute("formData", formData);
//
//        // Redirect to the confirmation page
//        return "redirect:/ticketConfirmation";
//    }



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

    //    @GetMapping("/export/pdf")
//    public ResponseEntity<byte[]> exportPdfReport(@RequestParam("id") Long id ) throws JRException, FileNotFoundException {
//        Ticket ticket = ticketRepository.findTicketById(id);
//        if (ticket == null) {
//            // Handle the case where the ticket with the given ID is not found
//            return ResponseEntity.badRequest().body(("Ticket not found for ID: " + id).getBytes());
//        }
//        // Send confirmation email
//        sendConfirmationEmail(ticket.getEmail());
//        return ResponseEntity.ok("Confirmation email sent for ticket ID: " + id);
//
////        return ticketService.exportReport("pdf", ticket);
//    }
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPdfReport(@RequestParam("id") Long id) {
        Ticket ticket = ticketRepository.findTicketById(id);
        if (ticket == null) {
            // Handle the case where the ticket with the given ID is not found
            return ResponseEntity.badRequest().body(("Ticket not found for ID: " + id).getBytes());
        }

        try {
            // Export PDF report
            ResponseEntity<byte[]> pdfResponse = ticketService.exportReport("pdf", ticket);
            // If the PDF export is successful, send confirmation email
//        ticketService.confirmBooking(ticket.getEmail());
//         If the PDF export is successful, send confirmation email
            ticketService.confirmBooking(ticket.getEmail(), pdfResponse.getBody());
            // Return the PDF as an attachment in the response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "ticket.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            // Add a custom header to indicate that the email has been sent
            headers.add("X-Email-Sent", "true");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfResponse.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error exporting PDF report or sending confirmation email".getBytes());
        }
    }

    @GetMapping("/fetchFee")
    public ResponseEntity<Map<String, Double>> fetchFee(@RequestParam("nationalityId") Long nationalityId,
                                                        @RequestParam("categoryId") Long categoryId,
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
//    @GetMapping("/fetchFees")
//    public ResponseEntity<Map<String, Double>> fetchFees(
//            @RequestParam("establishmentId") Long establishmentId,
//            @RequestParam(value = "nationalityId", required = false) Long nationalityId,
//            @RequestParam(value = "categoryId", required = false) Long categoryId,
//            @RequestParam(value = "otherFeesTypeId", required = false) Long otherFeesTypeId,
//            @RequestParam(value = "numberOfPeople", required = false) Long numberOfPeople,
//            @RequestParam(value = "numberOfItems", required = false) Long numberOfItems) {
//
//        Map<String, Double> responseMap = new HashMap<>();
//
//        if (nationalityId != null && categoryId != null && numberOfPeople != null) {
//            // Fetch entry fees
//            List<Fees> feesList = feesRepo.findByNationalityNationalityIdAndCategoryCategoryIdAndEstablishmentEstablishmentId(nationalityId, categoryId, establishmentId);
//            if (!feesList.isEmpty()) {
//                double entryFee = feesList.get(0).getEntryFee();
//                double totalFees = entryFee * numberOfPeople;
//                responseMap.put("entryFee", entryFee);
//                responseMap.put("totalFees", totalFees);
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//            }
//        }
//
//        if (otherFeesTypeId != null && numberOfItems != null) {
//            // Fetch other fees
//            List<OtherFees> otherFeesList = otherFeesRepo.findByEstablishmentEstablishmentIdAndOtherFeesTypeOtherFeesTypeId(establishmentId, otherFeesTypeId);
//            if (!otherFeesList.isEmpty()) {
//                double originalFees = otherFeesList.get(0).getFees();
//                double totalItemFees = originalFees * numberOfItems;
//                responseMap.put("totalItemFees", totalItemFees);
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//            }
//        }
//
//        return ResponseEntity.ok(responseMap);
//    }


}



















