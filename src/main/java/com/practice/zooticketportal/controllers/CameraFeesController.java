package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.repositories.FeesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CameraFeesController {
    @Autowired
    private FeesRepo feesRepo;
    //Calculation and fetching of camera fees of particular establishment
    @GetMapping("/fetchCameraFees")
    @ResponseBody
    public Map<String, Long> calculateCameraFees(@RequestParam Long establishmentId,
                                                 @RequestParam Long numberOfCameras) {
        // Fetch camera fees from the service
        Long feesPerCamera = feesRepo.getCameraFees(establishmentId);

        // Calculate total camera fees
        Long totalCameraFees = feesPerCamera * numberOfCameras;

        System.out.println("Camera Fees:" + totalCameraFees);

        // Create a map to hold the response data
        Map<String, Long> responseData = new HashMap<>();
        responseData.put("feesPerCamera", feesPerCamera);
        responseData.put("totalCameraFees", totalCameraFees);

        return responseData;
    }


}
