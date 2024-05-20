package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.Establishment;
import com.practice.zooticketportal.entity.OtherFees;
import com.practice.zooticketportal.entity.Ticket;
import com.practice.zooticketportal.repositories.FeesRepo;
import com.practice.zooticketportal.repositories.OtherFeesRepo;
import com.practice.zooticketportal.service.EstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
//    @GetMapping("/fetchOtherFees")
//    public ResponseEntity<Map<String, Object>> fetchFeePerItem(
//            @RequestParam Long establishmentId,
//            @RequestParam String feesType) {
//
//        // Use the repository to get the fee per item based on feeType and establishmentId
//        OtherFees otherFees = otherFeesRepo.findByFeesTypeAndEstablishmentEstablishmentId(feesType, establishmentId);
//
//        double fees = 0.0;
//        if (otherFees != null) {
//            fees = otherFees.getFees(); // Assuming there is a method getFeePerItem() in OtherFees entity
//        }
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("fees", fees);
//
//        return ResponseEntity.ok(response);
//    }
@GetMapping("/fetchOtherFees")
@ResponseBody
public ResponseEntity<Double> fetchFee(@RequestParam String feesType, @RequestParam Long establishmentId) {
    System.out.println("fetchingFees");
    OtherFees fees = otherFeesRepo.findByFeesTypeAndEstablishment_EstablishmentId(feesType, establishmentId);
    if (fees != null) {
        System.out.println("fees"+fees);
        return ResponseEntity.ok(fees.getFees()); // Assuming getFees() returns the fee value
    } else {
        return ResponseEntity.notFound().build();
    }
}

//    @GetMapping("/fetchOtherFees")
//    public ResponseEntity<Map<String, Double>> fetchOriginalFees(@RequestParam("establishmentId") Long establishmentId,
//                                                                 @RequestParam("feeType") String feeType,
//                                                                 @RequestParam("numberOfItems") Long numberOfItems) {
//        // Query the database to retrieve the list of other fees based on the provided establishment ID and fee type
//        List<OtherFees> otherFeesList = otherFeesRepo.findByFeesTypeAndEstablishmentEstablishmentId(feeType,establishmentId);
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
