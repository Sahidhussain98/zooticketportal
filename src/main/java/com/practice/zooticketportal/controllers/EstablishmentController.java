package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.Establishment;
import com.practice.zooticketportal.service.EstablishmentService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/establishments")
public class EstablishmentController {

    @Autowired
    private EstablishmentService establishmentService;

    @Autowired
    public EstablishmentController(EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
    }

    @GetMapping
    public String listEstablishments(Model model) {
        List<Establishment> establishments = establishmentService.getAllEstablishments();
        model.addAttribute("establishmentList", establishments);
        return "establishments";
    }



    @GetMapping("/new")
    public String createEstablishmentForm(Model model) {
        Establishment establishment = new Establishment();
        model.addAttribute("establishment", establishment);
        return "create-establishments";
    }

    @PostMapping
    public String saveEstablishment(@ModelAttribute("establishment") Establishment establishment) {
        establishmentService.saveEstablishment(establishment);
        return "redirect:/establishments";
    }

    @GetMapping("/edit/{id}")
    public String editEstablishmentForm(@PathVariable Long id, Model model) {
        Establishment existingEstablishment = establishmentService.getEstablishmentById(id);
        model.addAttribute("establishment", existingEstablishment);
        return "edit-establishments";
    }

    @PostMapping("/{id}")
    public String updateEstablishment(@PathVariable Long id,
                                            @ModelAttribute("establishment") Establishment establishment) {
        Establishment existingEstablishment = establishmentService.getEstablishmentById(id);
        existingEstablishment.setName(establishment.getName());
        existingEstablishment.setType(establishment.getType());
        existingEstablishment.setEnteredBy(establishment.getEnteredBy());

        establishmentService.updateEstablishment(existingEstablishment);
        System.out.printf("update1");
        return "redirect:/establishments";
    }


    @GetMapping("/delete/{id}")
    public String deleteEstablishment(@PathVariable Long id) {
        System.out.printf("delete establishment`1");
        establishmentService.deleteEstablishmentById(id);
        System.out.printf("delete establishment2");
        return "redirect:/establishments";
    }
    @GetMapping("/export/{format}")
    public ResponseEntity<String> exportReport(@PathVariable String format) throws JRException, FileNotFoundException {
//        System.out.printf("fromat");
        return establishmentService.exportReport(format);
    }
    @GetMapping("/export/pdf")
    public ResponseEntity<String> exportPdfReport() throws JRException, FileNotFoundException {
//        System.out.println("pdf");
        return establishmentService.exportReport("pdf");
    }
}





