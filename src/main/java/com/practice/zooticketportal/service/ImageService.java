package com.practice.zooticketportal.service;

import com.practice.zooticketportal.entity.Images;
import com.practice.zooticketportal.repositories.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Autowired
    private ImageRepo imageRepo;
    public List<Images> getImagesByEstablishmentId(Long establishmentId) {
        return imageRepo.findByEstablishmentEstablishmentId(establishmentId);
    }


    public byte[] getImageDataById(Long imageId) {
        Images image = imageRepo.findById(imageId).orElse(null);
        if (image != null) {
            return image.getImageData();
        }
        return null;
    }

}
