package com.practice.zooticketportal.service;

import com.practice.zooticketportal.entity.Images;
import com.practice.zooticketportal.repositories.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class ImageService {

    @Autowired
    private ImageRepo imageRepo;

    public String getImageData(Long establishmentId) {
        Images image = imageRepo.findFirstByEstablishment_EstablishmentId(establishmentId);
        if (image != null && image.getImageData() != null) {
            return Base64.getEncoder().encodeToString(image.getImageData());
        }
        return null;
    }
}
