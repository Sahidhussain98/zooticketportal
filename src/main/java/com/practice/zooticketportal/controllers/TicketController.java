package com.practice.zooticketportal.controllers;


import com.practice.zooticketportal.entity.Ticket;
import com.practice.zooticketportal.repositories.TicketRepository;
import com.practice.zooticketportal.service.TicketService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;
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
        existingTicket.setCategory(updatedTicket.getCategory());
        existingTicket.setCountry(updatedTicket.getCountry());
        existingTicket.setDateTime(updatedTicket.getDateTime());



        // Save the updated ticket to the database
        ticketRepository.save(existingTicket);

        return "checkoutConfirmation-form";
    }
    @GetMapping("/export/pdf")
    public ResponseEntity<String> exportPdfReport() throws JRException, FileNotFoundException {
//        System.out.println("pdf");
        return ticketService.exportReport("pdf");
    }



}








