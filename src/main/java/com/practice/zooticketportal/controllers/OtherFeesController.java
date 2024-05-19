package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.Establishment;
import com.practice.zooticketportal.entity.OtherFees;
import com.practice.zooticketportal.entity.Ticket;
import com.practice.zooticketportal.repositories.FeesRepo;
import com.practice.zooticketportal.repositories.OtherFeesRepo;
import com.practice.zooticketportal.service.EstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class OtherFeesController {
    @Autowired
    private OtherFeesRepo otherFeesRepo;
    @Autowired
    private EstablishmentService establishmentService;



}
