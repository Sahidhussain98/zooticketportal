package com.practice.zooticketportal.controllers;


import com.practice.zooticketportal.entity.Ticket;
import com.practice.zooticketportal.repositories.TicketRepository;
import com.practice.zooticketportal.service.TicketService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketService ticketService;

    @Value("${countries}")
    private List<String> countries;

    @Value("${categories}")
    private List<String> categories;

//    @GetMapping("/showCheckoutForm")
//    public String showCheckoutForm(Model theModel) {
//        // Create a ticket object
//        Ticket theTicket = new Ticket();
//
//        // Generate booking ID
//        String bookingId = generateBookingId(theTicket.getEstablishment().getName(), LocalDateTime.now());
//
//        // Set booking ID to the ticket
//        theTicket.setBookingId(bookingId);
//
//        // Add ticket object to the model
//        theModel.addAttribute("theTicket", theTicket);
//
//        // Add the list of countries to the model
//        theModel.addAttribute("countries", countries);
//
//        // Add the list of categories to the model
//        theModel.addAttribute("categories", categories);
//
//        return "checkout-form";
//    }
//
//    private String generateBookingId(String establishmentName, LocalDateTime bookingDateTime) {
//        // Generate booking ID based on establishment name, booking date/time, and a serial number
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//        String formattedDateTime = bookingDateTime.format(formatter);
//        // You can add additional logic to generate a unique serial number
//        String serialNumber = "001"; // For example, you can start with 001
//        return establishmentName.substring(0, 3) + formattedDateTime + serialNumber;
//    }


    @GetMapping("/showCheckoutForm")
    public String showCheckoutForm(Model theModel) {

        // create a student object
        Ticket theTicket = new Ticket();

        // add student object to the model
        theModel.addAttribute("theTicket", theTicket);

        // add the list of countries to the model
        theModel.addAttribute("countries", countries);

        //   add the list of categories to the model
        theModel.addAttribute("categories", categories);


        return "checkout-form";
    }

    @PostMapping("/processCheckoutForm")
    public String processForm(@ModelAttribute("theTicket") Ticket theTicket) {
        // Save the ticket details to the database using the repository
        ticketRepository.save(theTicket);


        // log the input data
        System.out.println("theTicket: " + theTicket.getFirstName() + " " + theTicket.getLastName());

        return "checkoutConfirmation-form";
    }

    @GetMapping("/showEditForm")
    public String showEditForm(@RequestParam("ticketId") Long ticketId, Model theModel) {
        // Retrieve ticket details by ID
        Ticket theTicket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));

        // Add ticket object to the model
        theModel.addAttribute("theTicket", theTicket);

        // Add the list of countries and categories to the model
        theModel.addAttribute("countries", countries);
        theModel.addAttribute("categories", categories);

        return "checkout-form";
    }

    @PostMapping("/processEditForm")
    public String processEditForm(@ModelAttribute("theTicket") Ticket updatedTicket) {
        // Retrieve the existing ticket from the database
        Ticket existingTicket = ticketRepository.findById(updatedTicket.getId()).orElseThrow(() -> new RuntimeException("Ticket not found"));

        // Update the existing ticket with new details
        existingTicket.setFirstName(updatedTicket.getFirstName());
        existingTicket.setLastName(updatedTicket.getLastName());
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
}








