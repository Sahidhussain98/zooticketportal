package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.OtherFees;
import com.practice.zooticketportal.repositories.FeesRepo;
import com.practice.zooticketportal.repositories.OtherFeesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OtherFeesController {
    @Autowired
    private OtherFeesRepo otherFeesRepo;
    @GetMapping("/fetchOtherFees")
    public ResponseEntity<List<OtherFees>> fetchOtherFees(@RequestParam Long establishmentId) {
        List<OtherFees> otherFeesList = otherFeesRepo.findByEstablishmentEstablishmentId(establishmentId);
        if (!otherFeesList.isEmpty()) {
            System.out.println("fees"+otherFeesList);
            return ResponseEntity.ok().body(otherFeesList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



//    @GetMapping("/fetchOtherFees")
//    public String fetchOtherFees(@RequestParam Long establishmentId, Model model) {
//        // Assuming you have a method to retrieve OtherFees by establishmentId from your repository
//        List<OtherFees> otherFees = otherFeesRepo.findByEstablishmentEstablishmentId(establishmentId); // Change the method name accordingly
//
//        // Add the list of OtherFees to the model
//        model.addAttribute("otherFees", otherFees);
//
//        // Return the name of the Thymeleaf template to render
//        return ""; // Replace "your_template_name" with the actual name of your Thymeleaf template
//    }
//    @GetMapping("/fetchOtherFees")
//    public ResponseEntity<Map<String, Double>> fetchOriginalFees(@RequestParam("establishmentId") Long establishmentId,
//                                                                 @RequestParam("otherFeesTypeId") Long otherFeesTypeId,
//                                                                 @RequestParam("numberOfItems") Long numberOfItems) {
//        // Query the database to retrieve the list of other fees based on the provided establishment ID and other fees type ID
//        List<OtherFees> otherFeesList = otherFeesRepo.findByEstablishmentEstablishmentId(establishmentId);
//
//        // Create a response map to hold the fees per item and total item fees
//        Map<String, Double> responseMap = new HashMap<>();
//
//        // Check if other fees are retrieved successfully
//        if (!otherFeesList.isEmpty()) {
//            // Get the original fees from the first other fee (assuming the combination is unique)
//            double originalFees = otherFeesList.get(0).getFees();
//
//            // Calculate the total item fees
//            double totalItemFees = originalFees * numberOfItems;
//
//            // Put the fees per item and total item fees into the response map
//            responseMap.put("fees", originalFees);
//            responseMap.put("totalItemFees", totalItemFees);
//
//            // Return the response map
//            return ResponseEntity.ok(responseMap);
//        } else {
//            // If no other fees are retrieved, return an error response
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }


}