package com.zoo.service;





import com.practice.zooticketportal.entity.MasterEstablishment;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
@Service
public interface MasterEstablishmentService {
    List<MasterEstablishment> getAllMasterEstablishments();

   MasterEstablishment saveMasterEstablishment(MasterEstablishment masterEstablishment);



    MasterEstablishment getMasterEstablishmentById(Long id);


    MasterEstablishment updateMasterEstablishment(MasterEstablishment masterEstablishment);



    void deleteMasterEstablishmentById(Long id);
    ResponseEntity<String> exportReport(String format) throws FileNotFoundException, JRException;


}



