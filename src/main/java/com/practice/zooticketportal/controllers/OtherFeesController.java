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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    public ResponseEntity<Map<String, Object>> fetchFee(@RequestParam String feesType, @RequestParam Long establishmentId, @RequestParam(required = false) Long numItems) {
        OtherFees fees = otherFeesRepo.findByFeesTypeAndEstablishment_EstablishmentId(feesType, establishmentId);
        if (fees != null) {
            Map<String, Object> response = new HashMap<>();
            Double feePerItem = fees.getFees();
            response.put("feePerItem", feePerItem);

            if (numItems != null && numItems > 0) {
                Double totalItemFees = numItems * feePerItem;
                response.put("totalItemFees", totalItemFees);
            }
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }

        //PostMapping for  saving otherFees from admin part modal

    }
    @PostMapping("/addOtherFees")
    public String saveOtherFees(@RequestParam("establishmentId") Long establishmentId,
                                @RequestParam("feesType") String feesType,
                                @RequestParam("fees") Double fees,
                                Model model) {
        try {
            // Fetch the existing establishment object from the database
            Establishment establishment = establishmentService.getEstablishmentById(establishmentId);

            // Create OtherFees object and link it to the establishment
            OtherFees otherFees = new OtherFees();
            otherFees.setFeesType(feesType);
            otherFees.setFees(fees);
            otherFees.setEstablishment(establishment);

            // Save the OtherFees object to the database
            otherFeesRepo.save(otherFees);
        } catch (Exception e) {
            // Handle any exceptions here, e.g., database errors
            model.addAttribute("error", "Failed to save other fees. Please try again.");
            return "errorPage"; // Return an error page or handle the error accordingly
        }

        return "redirect:/edit-establishments"; // Redirect to the establishment details page
    }

}
