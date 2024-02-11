package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Base64;

@Controller
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/{establishmentId}")
    @ResponseBody
    public ResponseEntity<byte[]> getImageData(@PathVariable Long establishmentId) {
        String imageData = imageService.getImageData(establishmentId);
        if (imageData != null) {
            byte[] decodedImageData = Base64.getDecoder().decode(imageData);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG); // Adjust content type if necessary
            return new ResponseEntity<>(decodedImageData, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
