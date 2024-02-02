package com.practice.zooticketportal.service;

import com.practice.zooticketportal.entity.Ticket;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.util.List;


public interface TicketService {
    List<Ticket> getAllTicket();

    ResponseEntity<String> exportReport(String format) throws FileNotFoundException, JRException;
}
