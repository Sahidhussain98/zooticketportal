package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.NonWorkingDays;
import com.practice.zooticketportal.repositories.NonWorkingDaysRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller // Change to @Controller if not using @RestController
public class NonWorkingDaysController {
    @Autowired
    private NonWorkingDaysRepo nonWorkingDaysRepo;

//    @GetMapping("/fetchNonWorkingDays/{establishmentId}")
//    @ResponseBody
//    public List<NonWorkingDays> getNonWorkingDaysByEstablishmentId(@PathVariable Long establishmentId) {
//        System.out.println("Fetching non-working days for establishment ID: " + establishmentId);
//        List<NonWorkingDays> nonWorkingDays = nonWorkingDaysRepo.findByEstablishmentEstablishmentId(establishmentId);
//        System.out.println("Non-working days fetched: " + nonWorkingDays);
//        return nonWorkingDays;
//    }
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

}