package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.Establishment;
import com.practice.zooticketportal.entity.Fees;
import com.practice.zooticketportal.entity.OtherFees;
import com.practice.zooticketportal.entity.Ticket;
import com.practice.zooticketportal.repositories.EstablishmentRepo;
import com.practice.zooticketportal.repositories.FeesRepo;
import com.practice.zooticketportal.repositories.OtherFeesRepo;
import com.practice.zooticketportal.service.EstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class OtherFeesController {
    @Autowired
    private OtherFeesRepo otherFeesRepo;
    @Autowired
    private EstablishmentService establishmentService;
    @Autowired
    private EstablishmentRepo establishmentRepo;
    @GetMapping("/fetchOtherFees")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> fetchOtherFees(@RequestParam Long establishmentId) {
        List<OtherFees> feesList = otherFeesRepo.findByEstablishmentEstablishmentId(establishmentId);
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (OtherFees fees : feesList) {
            Map<String, Object> response = new HashMap<>();
            response.put("feesType", fees.getFeesType());
            response.put("feePerItem", fees.getFees());
            responseList.add(response);
        }

        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/addOtherFees")
    public String saveOtherFees(@RequestParam("establishmentId") Long establishmentId,
                                @RequestParam("feesType") String feesType,
                                @RequestParam("fees") Double fees,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        try {
            System.out.println("feesType"+feesType);
            // Fetch the existing establishment object from the database
            Establishment establishment = establishmentService.getEstablishmentById(establishmentId);

            // Create OtherFees object and link it to the establishment
            OtherFees otherFees = new OtherFees();
            otherFees.setFeesType(feesType);
            otherFees.setFees(fees);
            otherFees.setEstablishment(establishment);
            otherFees.setEnteredOn(LocalDateTime.now());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            String enteredBy = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")) ? "admin" : currentUserName;
            otherFees.setEnteredBy(enteredBy);


            // Save the OtherFees object to the database
            otherFeesRepo.save(otherFees);
        } catch (Exception e) {
            // Handle any exceptions here, e.g., database errors
            model.addAttribute("error", "Failed to save other fees. Please try again.");
            return "errorPage"; // Return an error page or handle the error accordingly
        }
        redirectAttributes.addAttribute("id", establishmentId);

        // Redirect to the establishment details page with the establishmentId
        return "redirect:/establishments/viewEstablishment/{id}";
    }

}
