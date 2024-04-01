package com.practice.zooticketportal.controllers;


import com.practice.zooticketportal.entity.*;
import com.practice.zooticketportal.repositories.*;
import com.practice.zooticketportal.service.AllUserService;
import com.practice.zooticketportal.service.EstablishmentService;
import com.practice.zooticketportal.service.TicketService;
import com.practice.zooticketportal.serviceimpl.NonWorkingDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
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

    @GetMapping("/showCheckoutForm/{establishmentId}")
    public String showCheckoutForm(@PathVariable Long establishmentId, Model model, Authentication authentication) {
        // Retrieve authenticated user's username
        String username = authentication.getName();

        // Retrieve user details from the database using the username
        List<AllUser> users = allUserService.findByUsername(username);

        // Placeholder for phone number and email
        String phoneNumber = "";
        String email = "";

        // Check if any user was found
        if (!users.isEmpty()) {
            AllUser user = users.get(0); // Assuming there's only one user per username
            phoneNumber = user.getPhoneNumber().toString();
            email = user.getEmail();
        }

        // Here, you can add logic to retrieve the establishment details
        // based on the establishmentId from the database
        Establishment establishment = establishmentService.getEstablishmentById(establishmentId);

        // Create a ticket object
        Ticket ticket = new Ticket();

        // Add ticket and establishment objects to the model
        model.addAttribute("theTicket", ticket);
        model.addAttribute("establishment", establishment);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("email", email);

        // add the list of countries to the model
        List<Category> categories = categoryRepo.findAll();
        List<Nationality> nationalities1 = nationalityRepo.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("nationalities", nationalities1);
        return "ticket";
    }
//    @GetMapping("/ticketConfirmation/{establishmentId}")
//    public String showTicketConfirmationPage(@PathVariable Long establishmentId, Model model) {
//        System.out.print("TICKETconfirm");
//        Establishment establishment = establishmentService.getEstablishmentById(establishmentId);
//        if (establishment == null) {
//            // Handle the situation where the establishment is not found
//            return "establishmentNotFound"; // Return a view indicating that the establishment is not found
//        }
//        model.addAttribute("establishment", establishment);
//        return "ticketConfirmation"; // Assuming your Thymeleaf template is named "ticketConfirmation.html"
//    }
//@GetMapping("/ticketConfirmation")
//    public String showTicketConfirmationPage(Model model) {
//        System.out.print("TICKETconfirm");
//        return "ticketConfirmation"; // Assuming your Thymeleaf template is named "ticketConfirmation.html"
//    }

    @PostMapping("/processCheckoutForm/{establishmentId}")
    public String processForm(@PathVariable Long establishmentId, @ModelAttribute("theTicket") Ticket theTicket, Model model) {
        // Retrieve establishment name
        Establishment establishment = establishmentService.getEstablishmentById(establishmentId);
        String establishmentName = establishment.getName();
        System.out.print("print please");

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

        return "checkoutConfirmation-form";
    }

    // Method to generate random serial number (you can implement your own logic)
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
    public ResponseEntity<?> fetchFee(@RequestParam("nationalityId") List<Long> nationalityIds,
                                      @RequestParam("categoryId") List<Long> categoryIds,
                                      @RequestParam("establishmentId") Long establishmentId,
                                      @RequestParam("numberOfPeople") List<Long> numberOfPeople) {
        System.out.println("-----------");

        // Initialize total fees
        double totalFees = 0.0;

        // Iterate through each combination of nationality, category, and number of people
        for (int i = 0; i < nationalityIds.size(); i++) {
            Long nationalityId = nationalityIds.get(i);
            Long categoryId = categoryIds.get(i);
            Long people = numberOfPeople.get(i);

            // Query the database to retrieve the entry fee based on the provided nationality ID, category ID, and establishment ID
            List<Fees> fees = feesRepo.findByNationalityNationalityIdAndCategoryCategoryIdAndEstablishmentEstablishmentId(nationalityId, categoryId, establishmentId);

            // Check if the fee is retrieved successfully
            if (fees != null) {
                System.out.println("inside if");
                // Calculate the total fees for this combination based on the entry fee and the number of people
                for (Fees fee : fees) {
                    totalFees += fee.getEntryFee() * people; // Multiply by the number of people
                }
            } else {
                // If the fee cannot be retrieved, return an error response
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        // Return the total fees amount in the response body
        return ResponseEntity.ok(totalFees);
    }





}









