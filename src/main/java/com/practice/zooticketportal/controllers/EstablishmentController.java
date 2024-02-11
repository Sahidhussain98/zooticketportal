package com.practice.zooticketportal.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.ExceptionConverter;
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
    @Autowired
    private VillageRepo villageRepo;

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
        Optional<State> state = stateRepo.findById(16L); // Fetch state with ID 16 from the database
        String stateName = state.isPresent() ? state.get().getStateName() : "Unknown"; // Get the state name, or use a default value
        model.addAttribute("establishmentTypes", establishmentTypes);
        model.addAttribute("stateName", stateName); // Pass the state name to the model
        Establishment establishment = new Establishment();
        model.addAttribute("establishment", establishment);
        return "create-establishments";
    }


    @PostMapping
    public String saveEstablishment(@RequestParam("name") String name,
                                    @RequestParam("typeId") Long typeId, // Change type parameter to typeId
                                    @RequestParam("villageId") Long villageId,
                                    @RequestParam("image") MultipartFile imageFile,
                                    Model model) {
        try {
            // Create the establishment object
            Establishment establishment = new Establishment();
            establishment.setName(name);

            // Fetch the MasterEstablishment entity by typeId
            MasterEstablishment masterEstablishment = masterEstablishmentRepo.findById(typeId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid establishment type id"));
            establishment.setMasterEstablishment(masterEstablishment);

            establishment.setVillage(villageRepo.findById(villageId).orElse(null));
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
    @GetMapping("/blocks/{districtId}")
    public ResponseEntity<?> getBlocksByDistrictId(@PathVariable Long districtId) throws Exception {
        List<Block> blocks=Optional.ofNullable(blockRepo.findByDistrictDistrictId(districtId)).orElseThrow(()->new Exception("District not Found"));
        return ResponseEntity.ok(blocks);
    }

    @GetMapping("/villages/{blockId}")
    public ResponseEntity<?> getVillageByBlockId(@PathVariable Long blockId) throws Exception {
        List<Village> villages=Optional.ofNullable(villageRepo.findByBlockBlockId(blockId)).orElseThrow(()->new Exception("Block id not found"));
        return ResponseEntity.ok(villages);
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
        // Remove setting type here since it's not part of the Establishment entity

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
