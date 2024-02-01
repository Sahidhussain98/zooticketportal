package com.zoo.service;

import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;


public interface TicketService {

    ResponseEntity<String> exportReport(String format) throws FileNotFoundException, JRException;
}
