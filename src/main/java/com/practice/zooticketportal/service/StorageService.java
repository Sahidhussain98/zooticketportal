package com.practice.zooticketportal.service;

import com.practice.zooticketportal.entity.Establishment;
import com.practice.zooticketportal.entity.Images;
import com.practice.zooticketportal.repositories.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class StorageService {

    @Autowired
    private ImageRepo imageRepo;

    public void uploadImage(MultipartFile file, Long establishmentId) throws IOException {
        Images image = new Images();
        image.setImageData(file.getBytes());

        Establishment establishment = new Establishment();
        establishment.setEstablishmentId(establishmentId); // Set the establishment ID
        image.setEstablishment(establishment);

        imageRepo.save(image);
    }

    public List<Images> getImagesByEstablishmentId(Long establishmentId) {
        return imageRepo.findByEstablishmentEstablishmentId(establishmentId);
    }

}
