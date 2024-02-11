package com.practice.zooticketportal.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.zooticketportal.entity.*;
import com.practice.zooticketportal.repositories.*;
import com.practice.zooticketportal.service.EstablishmentService;
import com.practice.zooticketportal.service.StorageService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@ Controller
@RequestMapping("/establishments")
public class EstablishmentController {

    @Autowired
    private EstablishmentService establishmentService;

    @Autowired
    private MasterEstablishmentRepo masterEstablishmentRepo;
    @Autowired
    private StateRepo stateRepo;
    @Autowired
    private StorageService storageService;
    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private DistrictRepo districtRepo;

    @Autowired
    private BlockRepo blockRepo;

    ObjectMapper objectMapper = new ObjectMapper();

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
        List<MasterEstablishment> establishmentTypes = masterEstablishmentRepo.findAll();
        List<State> states = stateRepo.findAll();


        model.addAttribute("establishmentTypes", establishmentTypes);
        model.addAttribute("states", states);


        Establishment establishment = new Establishment();
        model.addAttribute("establishment", establishment);
        return "create-establishments";
    }

    @PostMapping
    public String saveEstablishment(@RequestParam("name") String name,
                                    @RequestParam("type") String type,
                                    @RequestParam("image") MultipartFile imageFile,
                                    Model model) {
        try {
            // Create the establishment object
            Establishment establishment = new Establishment();
            establishment.setName(name);
            establishment.setType(type);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String enteredBy = authentication.getName();
            establishment.setEnteredBy(enteredBy);

            // Save the establishment first
            establishmentService.saveEstablishment(establishment);

            // Save the image and get its imageId
            Long imageId = storageService.uploadImage(imageFile, establishment.getEstablishmentId()); // Pass the establishment ID to link the image with the establishment

            // Optionally, you can add a success message to the model
            model.addAttribute("message", "Establishment created successfully!");
        } catch (IOException e) {
            // Handle file upload error
            model.addAttribute("error", "Failed to upload image. Please try again.");
            return "errorPage"; // Return an error page or handle the error accordingly
        }

        return "redirect:/establishments"; // Redirect to the home page or any other appropriate page
    }
    @GetMapping("/districts/{stateCode}")
    @ResponseBody
    public ResponseEntity<List<District>> getDistrictsByStateCode(@PathVariable Long stateCode) {
        State state = stateRepo.findByStateCode(stateCode);
        if (state == null) {
            return ResponseEntity.notFound().build();
        }
        List<District> districts = state.getDistrict();
        return ResponseEntity.ok(districts);
    }

    @GetMapping("get")
    public ResponseEntity<?> get(){
        return ResponseEntity.ok("bhhbc");
    }
    @GetMapping("/blocks/{districtId}")
    public ResponseEntity<?> getBlocksByDistrictId(@PathVariable Long districtId) throws Exception {

        //System.out.println(districtId);
//        District district = districtRepo.findByDistrictId(districtId);
//        System.out.println(district);
//        if (district == null){
//            return ResponseEntity.notFound().build();
//        }
//        List<Block> blocks = district.getBlock();
//        for(Block b : blocks){
//            System.out.println(b);
//        }

//        List<Block> blocks=blockRepo.findByDistrictDistrictId(districtId);
//        for (Block b : blocks){
//            System.out.println(b);
//        }
        List<Block> blocks=Optional.ofNullable(blockRepo.findByDistrictDistrictId(districtId)).orElseThrow(()->new Exception("District not Found"));
        return ResponseEntity.ok(blocks);
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
//        existingEstablishment.setEnteredBy(establishment.getEnteredBy());

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





