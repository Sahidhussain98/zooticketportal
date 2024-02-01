package com.zoo.controllers;


import com.zoo.entity.MasterEstablishment;
import com.zoo.service.MasterEstablishmentService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@Controller
@RequestMapping("/establishments")
public class MasterEstablishmentController {
    @Autowired
    private MasterEstablishmentService masterEstablishmentService;

    @Autowired
    public MasterEstablishmentController(MasterEstablishmentService masterEstablishmentService) {
        this.masterEstablishmentService = masterEstablishmentService;
    }

    @GetMapping
    public String listEstablishments(Model model) {
        model.addAttribute("masterEstablishmentList", masterEstablishmentService.getAllMasterEstablishments());
        return "establishments";
    }

    @GetMapping("/new")
    public String createMasterEstablishmentForm(Model model) {
        MasterEstablishment masterEstablishment = new MasterEstablishment();
        model.addAttribute("masterEstablishment", masterEstablishment);
        return "create-establishments";
    }

    @PostMapping
    public String saveMasterEstablishment(@ModelAttribute("masterEstablishment") MasterEstablishment masterEstablishment) {
        masterEstablishmentService.saveMasterEstablishment(masterEstablishment);
        return "redirect:/establishments";
    }

    @GetMapping("/edit/{id}")
    public String editMasterEstablishmentForm(@PathVariable Long id, Model model) {
        MasterEstablishment existingMasterEstablishment = masterEstablishmentService.getMasterEstablishmentById(id);
        model.addAttribute("masterEstablishment", existingMasterEstablishment);
        return "edit-establishments";
    }

    @PostMapping("/{id}")
    public String updateMasterEstablishment(@PathVariable Long id,
                                            @ModelAttribute("masterEstablishment") MasterEstablishment masterEstablishment) {
        MasterEstablishment existingMasterEstablishment = masterEstablishmentService.getMasterEstablishmentById(id);
        existingMasterEstablishment.setEstablishmentName(masterEstablishment.getEstablishmentName());
        existingMasterEstablishment.setEstablishmentType(masterEstablishment.getEstablishmentType());
        existingMasterEstablishment.setEnteredBy(masterEstablishment.getEnteredBy());

        masterEstablishmentService.updateMasterEstablishment(existingMasterEstablishment);
        System.out.printf("update1");
        return "redirect:/establishments";
    }


    @GetMapping("/delete/{id}")
    public String deleteEstablishment(@PathVariable Long id) {
        System.out.printf("delete establishment`1");
        masterEstablishmentService.deleteMasterEstablishmentById(id);
        System.out.printf("delete establishment2");
        return "redirect:/establishments";
    }
    @GetMapping("/export/{format}")
    public ResponseEntity<String> exportReport(@PathVariable String format) throws JRException, FileNotFoundException {
//        System.out.printf("fromat");
        return masterEstablishmentService.exportReport(format);
    }
    @GetMapping("/export/pdf")
    public ResponseEntity<String> exportPdfReport() throws JRException, FileNotFoundException {
//        System.out.println("pdf");
        return masterEstablishmentService.exportReport("pdf");
    }
}





