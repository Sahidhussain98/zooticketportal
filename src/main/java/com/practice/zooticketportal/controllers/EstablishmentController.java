package com.practice.zooticketportal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.zooticketportal.entity.*;
import com.practice.zooticketportal.repositories.*;
import com.practice.zooticketportal.service.EstablishmentService;
import com.practice.zooticketportal.service.StorageService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
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

    @Autowired
    private NationalityRepo nationalityRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private FeesRepo feesRepo;

    @Autowired
    private EstablishmentRepo establishmentRepo;
    ObjectMapper objectMapper = new ObjectMapper();// needed to load establishmnet page

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
        List<Nationality> nationalities = nationalityRepo.findAll();
        List<Category> categories = categoryRepo.findAll();
        Optional<State> state = stateRepo.findById(16L); // Fetch state with ID 16 from the database
        String stateName = state.isPresent() ? state.get().getStateName() : "Unknown"; // Get the state name, or use a default value
        model.addAttribute("establishmentTypes", establishmentTypes);
        model.addAttribute("nationalities", nationalities);
        model.addAttribute("categories", categories);
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

            // Read image data from MultipartFile
            byte[] imageData = imageFile.getBytes();
            establishment.setProfileImage(imageData); // Set image data to the profileImage field


            // Save the establishment first
            establishmentService.saveEstablishment(establishment);


            // Save the image and get its imageId

            model.addAttribute("message", "Establishment created successfully!");
//            return ResponseEntity.ok(String.valueOf(establishment.getEstablishmentId()));
            //showEstablishmentDetails(establishment.getEstablishmentId());
            System.out.println(establishment.getEstablishmentId());
            return "redirect:/establishments/show?id=" + establishment.getEstablishmentId();

        } catch (IOException e) {
            // Handle file upload error
            model.addAttribute("error", "Failed to upload image. Please try again.");
            return "errorPage"; // Return an error page or handle the error accordingly
        }

//        return "redirect:/establishments"; // Redirect to the home page or any other appropriate page
    }

    @GetMapping("/show")
    public String showEstablishmentDetails(@RequestParam("id") Long establishmentId, Model model) {
        Establishment establishment = establishmentRepo.findById(establishmentId).orElse(null);

        if (establishment != null) {
            model.addAttribute("establishment", establishment);

            // Retrieve additional data from createEstablishmentForm2 method
            List<Category> categories = categoryRepo.findAll();
            List<Nationality> nationalities1 = nationalityRepo.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("nationalities", nationalities1);

            return "createestablishment2"; // Return the HTML template with the establishment details
        } else {
            // Handle case where establishment is not found
            return "error"; // For example, return an error page
        }
    }


    @PostMapping("/save2")
    public String saveEstablishment2(@RequestParam("establishmentId") Long establishmentId,
                                     @RequestParam("address") String address,
                                     @RequestParam("openingTime") String openingTimeStr,
                                     @RequestParam("closingTime") String closingTimeStr,
                                     @RequestParam("image") MultipartFile imageFile,
                                     @RequestParam("nationalityId") List<Long> nationalityIds,
                                     @RequestParam("categoryId") List<Long> categoryIds,
                                     @RequestParam("entryFee") List<Double> entryFees,
                                     Model model) {
        try {
            // Fetch the existing establishment object from the database
            Establishment establishment = establishmentService.getEstablishmentById(establishmentId);

            SimpleDateFormat hM = new SimpleDateFormat("HH:mm");
            // Update the establishment object with the new data
            establishment.setAddress(address);// Convert String to LocalDateTime
            LocalTime openingTime = LocalTime.parse(openingTimeStr, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime closingTime = LocalTime.parse(closingTimeStr, DateTimeFormatter.ofPattern("HH:mm"));
            establishment.setOpeningTime(openingTime);
            establishment.setClosingTime(closingTime);
            establishment.updateStatus();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String enteredBy = authentication.getName();
            establishment.setEnteredBy(enteredBy);

            // Save the establishment first
            establishmentService.saveEstablishment(establishment);

            // Save the image and get its imageId
            Long imageId = storageService.uploadImage(imageFile, establishmentId); // Pass the establishment ID to link the image with the establishment

            for (int i = 0; i < nationalityIds.size(); i++) {
                Long nationalityId = nationalityIds.get(i);
                Long categoryId = categoryIds.get(i);
                Double entryFee = entryFees.get(i);
                // Create Fees object and link to establishment
                Fees fees = new Fees();
                fees.setEntryFee(entryFee);
                fees.setNationality(nationalityRepo.findById(nationalityId).orElse(null));
                fees.setCategory(categoryRepo.findById(categoryId).orElse(null));
                fees.setEstablishment(establishment);

                fees.setEnteredOn(LocalDateTime.now());
//                fees.setupdateEnteredOn(LocalDateTime.now());
                Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
                String enteredBy1 = authentication1.getName();
                fees.setEnteredBy(enteredBy1);

                feesRepo.save(fees);
            }
            // Optionally, you can add a success message to the model
            model.addAttribute("message", "Establishment updated successfully!");
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
                                      @ModelAttribute("establishment") Establishment establishment,
                                      @RequestParam("entryFee") List<Double> entryFees) {
        Establishment existingEstablishment = establishmentService.getEstablishmentById(id);
        existingEstablishment.setName(establishment.getName());
        // Remove setting type here since it's not part of the Establishment entity

        // Update establishment fields
        existingEstablishment.setAddress(establishment.getAddress());
        existingEstablishment.setOpeningTime(establishment.getOpeningTime());
        existingEstablishment.setClosingTime(establishment.getClosingTime());

        // Update fees
        List<Fees> feeList = existingEstablishment.getFees();
        for (int i = 0; i < feeList.size(); i++) {
            Fees fee = feeList.get(i);
            if (i < entryFees.size()) {
                fee.setEntryFee(entryFees.get(i));
            }
        }

        System.out.println(establishmentService.updateEstablishment(existingEstablishment));

        establishmentService.updateEstablishment(existingEstablishment);
        System.out.printf("update1");
        return "redirect:/establishments";
    }




//    @PostMapping("/{id}")
//    public String updateEstablishment(@PathVariable Long id,
//                                      @ModelAttribute("establishment") Establishment establishment) {
//        Establishment existingEstablishment = establishmentService.getEstablishmentById(id);
//        existingEstablishment.setName(establishment.getName());
//        // Remove setting type here since it's not part of the Establishment entity
//
//        establishmentService.updateEstablishment(existingEstablishment);
//        System.out.printf("update1");
//        return "redirect:/establishments";
//    }



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
    @GetMapping("/establishmentDetails/{establishmentId}")
    public String showEstablishmentImageDetails(@PathVariable Long establishmentId, Model model) {
        // Logic to retrieve establishment details using establishmentId
        Establishment establishment = establishmentService.getEstablishmentById(establishmentId);
        model.addAttribute("establishment", establishment);
        return "establishmentDetails"; // return the name of the HTML page for establishment details
    }

    @GetMapping("user/{establishmentId}")
    public ResponseEntity<byte[]> getEstablishmentImage(@PathVariable Long establishmentId) {
        try {
            byte[] imageData = establishmentService.getEstablishmentImageById(establishmentId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Set content type as JPEG image
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (IOException e) {
            // Handle the IOException
            e.printStackTrace(); // Print the stack trace for debugging purposes
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Return an appropriate response
        }
    }

}
