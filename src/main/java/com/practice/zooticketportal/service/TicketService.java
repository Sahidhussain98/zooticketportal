package com.practice.zooticketportal.service;

import com.practice.zooticketportal.entity.Establishment;
import com.practice.zooticketportal.entity.Ticket;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.util.List;


public interface TicketService {
    List<Ticket> getAllTicket();

    Ticket getTicketById(Long id);


    ResponseEntity<byte[]> exportReport(String format, Ticket ticket) throws FileNotFoundException, JRException;
    void confirmBooking(String toEmail, byte[] attachmentBytes);
}
