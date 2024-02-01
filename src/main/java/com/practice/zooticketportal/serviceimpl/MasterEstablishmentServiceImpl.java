package com.practice.zooticketportal.serviceimpl;


import com.practice.zooticketportal.entity.MasterEstablishment;
import com.practice.zooticketportal.repositories.MasterEstablishmentRepository;
import com.zoo.service.MasterEstablishmentService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class MasterEstablishmentServiceImpl implements MasterEstablishmentService {

    @Autowired
    private MasterEstablishmentRepository masterEstablishmentRepository;


    public MasterEstablishmentServiceImpl(MasterEstablishmentRepository masterEstablishmentRepository) {
        this.masterEstablishmentRepository = masterEstablishmentRepository;
    }


    @Override
    public List<MasterEstablishment> getAllMasterEstablishments() {
        return masterEstablishmentRepository.findAll();
    }

    @Override
    public MasterEstablishment saveMasterEstablishment(MasterEstablishment masterEstablishment) {
        return masterEstablishmentRepository.save(masterEstablishment);
    }

    @Override
    public MasterEstablishment getMasterEstablishmentById(Long id) {
        Optional<MasterEstablishment> optionalEstablishment = masterEstablishmentRepository.findById(id);
        return optionalEstablishment.orElse(null);
    }




    @Override
    public MasterEstablishment updateMasterEstablishment(MasterEstablishment masterEstablishment) {
        // Use the repository to save the updated entity
        return masterEstablishmentRepository.save(masterEstablishment);
    }
    @Override
    public void deleteMasterEstablishmentById(Long id) {
        masterEstablishmentRepository.deleteById(id);
    }
    //Methods for downloading reports
    public ResponseEntity<String> exportReport(String format) throws FileNotFoundException, JRException {
        String path="C:\\Users\\HP1\\IdeaProjects\\reports";
        List<MasterEstablishment> masterestablishments= masterEstablishmentRepository.findAll();//getAllStudents();
        //Load file and compile it
        File file = ResourceUtils.getFile("classpath:masterestablishments.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(masterestablishments);
        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("createdBy", "Admin");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if(format.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path+"\\masterestablishments.html");
            return ResponseEntity.ok("Report generated successfully in HTML format. Path:" + path + "\\masterestablishments.html");
        }
        if(format.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint, path+"\\masterestablishments.pdf");
            return ResponseEntity.ok("Report generated successfully in PDF format. Path:" + path +"\\masterestablishments.pdf");

        }
        return ResponseEntity.badRequest().body("Invalid Report format specified.");
    }
}
