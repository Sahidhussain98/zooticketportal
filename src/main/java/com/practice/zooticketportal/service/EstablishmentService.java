package com.practice.zooticketportal.service;


import com.practice.zooticketportal.entity.Establishment;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
@Service
public interface EstablishmentService {
    List<Establishment> getAllEstablishments();

   Establishment saveEstablishment(Establishment establishment);



    Establishment getEstablishmentById(Long id);


    Establishment updateEstablishment(Establishment establishment);



    void deleteEstablishmentById(Long id);
    ResponseEntity<String> exportReport(String format) throws FileNotFoundException, JRException;

    byte[] getEstablishmentImageById(Long id) throws IOException;

    void updateStatus(Long establishmentId);
}



