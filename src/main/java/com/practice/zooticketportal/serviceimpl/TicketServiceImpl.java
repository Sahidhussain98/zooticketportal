package com.practice.zooticketportal.serviceimpl;


import com.practice.zooticketportal.entity.Ticket;
import com.practice.zooticketportal.repositories.TicketRepository;
import com.practice.zooticketportal.service.TicketService;
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

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public List<Ticket> getAllTicket() {
        return ticketRepository.findAll();
    }
    @Override
    public ResponseEntity<String> exportReport(String format) throws FileNotFoundException, JRException {
        String path="D:\\reports";
        List<Ticket> ticket= ticketRepository.findAll();//getAllStudents();
        //Load file and compile it
        File file = ResourceUtils.getFile("classpath:tickets.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ticket);
        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("createdBy", "Admin");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if(format.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path+"\\ticket.html");
            return ResponseEntity.ok("Report generated successfully in HTML format. Path:" + path + "\\ticket.html");
        }
        if(format.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint, path+"\\ticket.pdf");
            return ResponseEntity.ok("Report generated successfully in PDF format. Path:" + path +"\\ticket.pdf");

        }
        return ResponseEntity.badRequest().body("Invalid Report format specified.");
    }

}
