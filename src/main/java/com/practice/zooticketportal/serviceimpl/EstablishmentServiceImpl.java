package com.practice.zooticketportal.serviceimpl;


import com.practice.zooticketportal.entity.Establishment;
import com.practice.zooticketportal.entity.MasterEstablishment;
import com.practice.zooticketportal.repositories.EstablishmentRepo;
import com.practice.zooticketportal.service.EstablishmentService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class EstablishmentServiceImpl implements EstablishmentService{

    @Autowired
    private EstablishmentRepo establishmentRepository;


    public EstablishmentServiceImpl(EstablishmentRepo stablishmentRepository) {
        this.establishmentRepository = establishmentRepository;
    }


    @Override
    public List<Establishment> getAllEstablishments() {
        return establishmentRepository.findAll();
    }

    @Override
    public Establishment saveEstablishment(Establishment establishment) {
        return establishmentRepository.save(establishment);
    }

    @Override
    public Establishment findById(Long id) {
        return establishmentRepository.findById(id).orElse(null);
    }

    @Override
    public Establishment getEstablishmentById(Long id) {
        Optional<Establishment> optionalEstablishment = establishmentRepository.findById(id);
        return optionalEstablishment.orElse(null);
    }




    @Override
    public Establishment updateEstablishment(Establishment establishment) {
        // Use the repository to save the updated entity
        return establishmentRepository.save(establishment);
    }
    @Override
    public void deleteEstablishmentById(Long id) {
        establishmentRepository.deleteById(id);
    }
    //Methods for downloading reports
    public ResponseEntity<String> exportReport(String format) throws FileNotFoundException, JRException {
        String path="C:\\Users\\HP1\\IdeaProjects\\reports";
        List<Establishment> establishments= establishmentRepository.findAll();//getAllStudents();
        //Load file and compile it
        File file = ResourceUtils.getFile("classpath:masterestablishments.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(establishments);
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
    @Override
    public byte[] getEstablishmentImageById(Long id) throws IOException {
        Optional<Establishment> optionalEstablishment = establishmentRepository.findById(id);
        if (optionalEstablishment.isPresent()) {
            Establishment establishment = optionalEstablishment.get();
            if (establishment.getProfileImage() != null) {
                return establishment.getProfileImage();
            } else {
                throw new IOException("Profile image not found for establishment with ID: " + id);
            }
        } else {
            throw new IOException("Establishment not found with ID: " + id);
        }
    }
    @Override
    public void updateStatus(Long establishmentId) {
        Establishment establishment = establishmentRepository.findById(establishmentId).orElse(null);
        if (establishment != null) {
            establishment.updateStatus();
            establishmentRepository.save(establishment);
        }
    }

    @Override
    public List<Establishment> getAllEstablishmentsByStatus(boolean status) {
        return establishmentRepository.findByStatus(status);
    }

}
