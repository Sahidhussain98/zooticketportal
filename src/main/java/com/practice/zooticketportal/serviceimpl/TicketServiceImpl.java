package com.practice.zooticketportal.serviceimpl;


import com.practice.zooticketportal.entity.Ticket;
import com.practice.zooticketportal.repositories.TicketRepository;
import com.practice.zooticketportal.service.TicketService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public List<Ticket> getAllTicket() {
        return ticketRepository.findAll();

    }

    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findTicketById(id);
    }

    @Override
    public ResponseEntity<byte[]> exportReport(String format, Ticket ticket) throws FileNotFoundException, JRException {
        File file = ResourceUtils.getFile("classpath:tickets.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // Create a map to store parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", ticket.getId());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(List.of(ticket));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        byte[] reportBytes;

        if (format.equalsIgnoreCase("pdf")) {
            reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "ticket.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(reportBytes, headers, HttpStatus.OK);
        }

        return ResponseEntity.badRequest().body("Invalid Report format specified.".getBytes());
    }

    @Override
    public void confirmBooking(String toEmail, byte[] attachmentBytes, String establishmentName) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail);
            helper.setSubject("Booking Confirmed");
            helper.setText("Your booking is confirmed. Thank you for your attention.");

            // Add attachment
            helper.addAttachment("ticket.pdf", new ByteArrayResource(attachmentBytes));

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}









