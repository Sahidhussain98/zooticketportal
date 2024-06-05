package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.Establishment;
import com.practice.zooticketportal.entity.Images;
import com.practice.zooticketportal.service.EstablishmentService;
import com.practice.zooticketportal.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;
    @GetMapping("/{imageId}")
    @ResponseBody
    public ResponseEntity<byte[]> getImageData(@PathVariable Long imageId) {
        byte[] imageData = imageService.getImageDataById(imageId);
        if (imageData != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}


