package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.NonWorkingDays;
import com.practice.zooticketportal.repositories.NonWorkingDaysRepo;
import com.practice.zooticketportal.service.NonWorkingDaysService;
import com.practice.zooticketportal.serviceimpl.NonWorkingDaysServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller // Change to @Controller if not using @RestController
public class NonWorkingDaysController {
    @Autowired
    private NonWorkingDaysRepo nonWorkingDaysRepo;
    @Autowired
    private NonWorkingDaysServiceImpl nonWorkingDaysServiceImpl;

@GetMapping("/fetchNonWorkingDays/{establishmentId}")
@ResponseBody
public List<Map<String, String>> getNonWorkingDaysByEstablishmentId(@PathVariable Long establishmentId) {
    System.out.println("Fetching non-working days for establishment ID: " + establishmentId);

    // Fetch non-working days from the repository
    List<NonWorkingDays> nonWorkingDaysList = nonWorkingDaysRepo.findByEstablishmentEstablishmentId(establishmentId);

    // Create a list to hold pairs of non-working dates, reasons, and ids
    List<Map<String, String>> response = new ArrayList<>();

    // Populate the list with non-working dates, reasons, and ids
    for (NonWorkingDays nonWorkingDay : nonWorkingDaysList) {
        Map<String, String> entry = new HashMap<>();
        entry.put("id", String.valueOf(nonWorkingDay.getNonWorkingDayId())); // Add the id to the response
        entry.put("nonWorkingDate", nonWorkingDay.getNonWorkingDate().toString());
        entry.put("reason", nonWorkingDay.getReason());
        response.add(entry);
    }

    System.out.println("Non-working days fetched: " + response);
    return response;
}


    @DeleteMapping("/deleteNonWorkingDay/{nonWorkingDayId}")
    public ResponseEntity<?> deleteNonWorkingDay(@PathVariable("nonWorkingDayId") Long nonWorkingDayId) {
        try {
            nonWorkingDaysServiceImpl.deleteNonWorkingDay(nonWorkingDayId);
            return ResponseEntity.ok("Non-working day deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting non-working day: " + e.getMessage());
        }
    }





}