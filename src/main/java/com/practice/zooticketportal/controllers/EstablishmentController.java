package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.*;
import com.practice.zooticketportal.repositories.*;
import com.practice.zooticketportal.service.EstablishmentService;
import com.practice.zooticketportal.service.OtherFeesService;
import com.practice.zooticketportal.service.StorageService;
import com.practice.zooticketportal.serviceimpl.FeesServiceImpl;
import com.practice.zooticketportal.serviceimpl.OtherFeesServiceImpl;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

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
    private NonWorkingDaysRepo nonWorkingDaysRepo;
    @Autowired
    private EstablishmentRepo establishmentRepo;
    @Autowired
    private OtherFeesRepo otherFeesRepo;
    @Autowired
    private OtherFeesServiceImpl otherFeesService;
    @Autowired
    private FeesServiceImpl feesServiceImpl;

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
        Optional<State> state = stateRepo.findById(16L);
        String stateName = state.isPresent() ? state.get().getStateName() : "Unknown";
        model.addAttribute("establishmentTypes", establishmentTypes);
        model.addAttribute("nationalities", nationalities);
        model.addAttribute("categories", categories);
        model.addAttribute("stateName", stateName);
        Establishment establishment = new Establishment();
        model.addAttribute("establishment", establishment);
        return "create-establishments";
    }

    @PostMapping
    public String saveEstablishment(@RequestParam("name") String name,
                                    @RequestParam("typeId") Long typeId,
                                    @RequestParam("villageId") Long villageId,
                                    @RequestParam("image") MultipartFile imageFile,
                                    Model model) {
        try {
            Establishment establishment = new Establishment();
            establishment.setName(name);

            MasterEstablishment masterEstablishment = masterEstablishmentRepo.findById(typeId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid establishment type id"));
            establishment.setMasterEstablishment(masterEstablishment);

            establishment.setVillage(villageRepo.findById(villageId).orElse(null));
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            String enteredBy = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")) ? "admin" : currentUserName;

            establishment.setEnteredBy(enteredBy);

            byte[] imageData = imageFile.getBytes();
            establishment.setProfileImage(imageData);

            establishmentService.saveEstablishment(establishment);

            model.addAttribute("message", "Establishment created successfully!");
            return "redirect:/establishments/show?id=" + establishment.getEstablishmentId();

        } catch (IOException e) {
            model.addAttribute("error", "Failed to upload image. Please try again.");
            return "/errorpage/error";
        }
    }

    @GetMapping("/nonworkingdates")
    public String showAddNonWorkingDatesPage(@RequestParam("id") Long establishmentId, Model model) {
        Establishment establishment = establishmentService.getEstablishmentById(establishmentId);
        model.addAttribute("establishment", establishment);
        // Fetch data from your repository/service using the establishmentId
        List<NonWorkingDays> nonWorkingDates = nonWorkingDaysRepo.findByEstablishmentEstablishmentId(establishmentId);
        model.addAttribute("nonWorkingDates", nonWorkingDates);
        return "nonWorkingDates";
    }

    @PostMapping("/nonworkingdates/save")
    public String saveNonWorkingDates(@RequestBody Map<String, Object> requestData, RedirectAttributes redirectAttributes) {
        Long establishmentId = Long.parseLong(requestData.get("establishmentId").toString());
        List<Map<String, String>> nonWorkingDates = (List<Map<String, String>>) requestData.get("nonWorkingDates");

        List<String> dates = new ArrayList<>();
        List<String> reasons = new ArrayList<>();

        for (Map<String, String> dateMap : nonWorkingDates) {
            dates.add(dateMap.get("nonWorkingDate"));
            reasons.add(dateMap.get("reason"));
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        boolean hasErrors = false;

        for (int i = 0; i < dates.size(); i++) {
            NonWorkingDays nonWorkingDay = new NonWorkingDays();
            LocalDate parsedDate = LocalDate.parse(dates.get(i), dateFormatter);
            nonWorkingDay.setNonWorkingDate(parsedDate);
            nonWorkingDay.setReason(reasons.get(i));
            nonWorkingDay.setEnteredOn(LocalDateTime.now());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            String enteredBy = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")) ? "admin" : currentUserName;
            nonWorkingDay.setEnteredBy(enteredBy);
            Establishment establishment = establishmentRepo.findById(establishmentId).orElse(null);
            if (establishment != null) {
                nonWorkingDay.setEstablishment(establishment);
                nonWorkingDaysRepo.save(nonWorkingDay);
            } else {
                hasErrors = true;
                redirectAttributes.addFlashAttribute("errorMessage", "Error: Establishment not found with ID " + establishmentId);
                break;
            }
        }

        if (hasErrors) {
            return "/errorpage/error"; // Redirect to an error page if needed
        }

        redirectAttributes.addFlashAttribute("successMessage", "Non-working dates saved successfully.");
        return "redirect:/establishments/nonworkingdates/" + establishmentId; // Adjust the redirect URL as needed
    }

    @GetMapping("/show")
    public String showEstablishmentDetails(@RequestParam("id") Long establishmentId, Model model) {
        Establishment establishment = establishmentRepo.findById(establishmentId).orElse(null);

        if (establishment != null) {
            model.addAttribute("establishment", establishment);
            List<Category> categories = categoryRepo.findAll();
            List<Nationality> nationalities1 = nationalityRepo.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("nationalities", nationalities1);
            return "createestablishment2";
        } else {
            return "/errorpage/error";
        }
    }

    @PostMapping("/save2")
    public String saveEstablishment2(@RequestParam("establishmentId") Long establishmentId,
                                     @RequestParam("address") String address,
                                     @RequestParam("openingTime") String openingTimeStr,
                                     @RequestParam("closingTime") String closingTimeStr,
                                     @RequestParam("imageFiles") MultipartFile[] imageFiles,
                                     @RequestParam("nationalityId") List<Long> nationalityIds,
                                     @RequestParam("categoryId") List<Long> categoryIds,
                                     @RequestParam("entryFee") List<Double> entryFees,
                                     @RequestParam("newFeesType") List<String> feesType,
                                     @RequestParam("newFees") List<Double> feesList,
                                     Model model) {
        try {
            Establishment establishment = establishmentService.getEstablishmentById(establishmentId);

            SimpleDateFormat hM = new SimpleDateFormat("HH:mm");
            establishment.setAddress(address);
            LocalTime openingTime = LocalTime.parse(openingTimeStr, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime closingTime = LocalTime.parse(closingTimeStr, DateTimeFormatter.ofPattern("HH:mm"));
            establishment.setOpeningTime(openingTime);
            establishment.setClosingTime(closingTime);
            establishment.updateStatus();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            String enteredBy = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")) ? "admin" : currentUserName;
            establishment.setEnteredBy(enteredBy);
            establishmentService.saveEstablishment(establishment);

            for (MultipartFile imageFile : imageFiles) {
                storageService.uploadImage(imageFile, establishmentId);
            }

            for (int i = 0; i < nationalityIds.size(); i++) {
                Long nationalityId = nationalityIds.get(i);
                Long categoryId = categoryIds.get(i);
                Double entryFee = entryFees.get(i);
                Fees fees = new Fees();
                fees.setEntryFee(entryFee);
                fees.setNationality(nationalityRepo.findById(nationalityId).orElse(null));
                fees.setCategory(categoryRepo.findById(categoryId).orElse(null));
                fees.setEstablishment(establishment);
                fees.setEnteredOn(LocalDateTime.now());
                fees.setEnteredBy(currentUserName);

                feesRepo.save(fees);
            }

            for (int i = 0; i < feesType.size(); i++) {
                String feeType = feesType.get(i);
                Double fee = feesList.get(i);
                OtherFees otherFeesEntity = new OtherFees();
                otherFeesEntity.setFeesType(feeType);
                otherFeesEntity.setFees(fee);
                otherFeesEntity.setEstablishment(establishment);
                otherFeesEntity.setEnteredOn(LocalDateTime.now());
                otherFeesEntity.setEnteredBy(enteredBy);
                otherFeesRepo.save(otherFeesEntity);
            }

            model.addAttribute("message", "Establishment updated successfully!");
        } catch (IOException e) {
            model.addAttribute("error", "Failed to upload image. Please try again.");
            return "/errorpage/error";
        }
        return "redirect:/establishments"; // Replace with your actual success page
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
        List<Block> blocks = Optional.ofNullable(blockRepo.findByDistrictDistrictId(districtId))
                .orElseThrow(() -> new Exception("District not Found"));
        return ResponseEntity.ok(blocks);
    }

    @GetMapping("/villages/{blockId}")
    public ResponseEntity<?> getVillageByBlockId(@PathVariable Long blockId) throws Exception {
        List<Village> villages = Optional.ofNullable(villageRepo.findByBlockBlockId(blockId))
                .orElseThrow(() -> new Exception("Block id not found"));
        return ResponseEntity.ok(villages);
    }

    @GetMapping("/viewEstablishment/{id}")
    public String editEstablishmentForm(@PathVariable Long id, Model model) {
        Establishment existingEstablishment = establishmentService.getEstablishmentById(id);
        List<OtherFees> otherFees = otherFeesService.getOtherFeesByEstablishmentEstablishmentId(id);
        model.addAttribute("establishment", existingEstablishment);
        model.addAttribute("otherFees", otherFees);
        List<Nationality> nationalities = nationalityRepo.findAll();
        List<Category> categories = categoryRepo.findAll();
        model.addAttribute("nationalities", nationalities);
        model.addAttribute("categories", categories);
        return "edit-establishments";
    }

    @PostMapping("/update/{id}")
    public String updateEstablishment(@PathVariable Long id,
                                      Establishment establishment,
                                      Model model) {
        Establishment existingEstablishment = establishmentService.getEstablishmentById(id);
        existingEstablishment.setName(establishment.getName());
        existingEstablishment.setAddress(establishment.getAddress());
        existingEstablishment.setOpeningTime(establishment.getOpeningTime());
        existingEstablishment.setClosingTime(establishment.getClosingTime());
        model.addAttribute("establishment", existingEstablishment);

        establishmentService.updateEstablishment(existingEstablishment);
        return "edit-establishments";
    }

    @PostMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam("status") String status) {
        Establishment existingEstablishment = establishmentService.getEstablishmentById(id);
        existingEstablishment.setStatus(status.equals("Active"));
        establishmentService.updateEstablishment(existingEstablishment);
        return "redirect:/establishments";
    }


    @PostMapping("/updateOtherFees")
    public String updateOtherFees(
            @RequestParam("otherFeesId") Long otherFeesId,
            @RequestParam("establishmentId") Long establishmentId,
            @RequestParam("feesType") String feesType,
            @RequestParam("fees") Double fees,
            Model model) {

        OtherFees otherFees = otherFeesRepo.findById(otherFeesId).orElse(null);
        if (otherFees == null) {
            model.addAttribute("error", "Fee not found");
            return "/errorpage/error"; // Return an error page or handle the error accordingly
        }

        Establishment existingEstablishment = establishmentService.getEstablishmentById(establishmentId);
        if (!otherFees.getEstablishment().equals(existingEstablishment)) {
            model.addAttribute("error", "Invalid establishment");
            return "/errorpage/error"; // Return an error page or handle the error accordingly
        }

        otherFees.setFeesType(feesType);
        otherFees.setFees(fees);
        otherFeesRepo.save(otherFees);

        // Fetch necessary data for the view
        List<OtherFees> otherFeesList = otherFeesService.getOtherFeesByEstablishmentEstablishmentId(establishmentId);
        List<Nationality> nationalities = nationalityRepo.findAll();
        List<Category> categories = categoryRepo.findAll();

        // Add attributes to the model
        model.addAttribute("establishment", existingEstablishment);
        model.addAttribute("otherFees", otherFeesList);
        model.addAttribute("nationalities", nationalities);
        model.addAttribute("categories", categories);
        model.addAttribute("message", "Update Successful");

        // Return the view name
        return "edit-establishments";
    }

    @PostMapping("/updateEntryFees")
    public String updateEntryFees(
            @RequestParam("establishmentId") Long establishmentId,
            @RequestParam("feesId") Long feesId,
            @RequestParam("entryFee") Double entryFee,
            Model model) {

        try {
            Fees fees = feesRepo.findById(feesId).orElse(null);
            if (fees == null) {
                model.addAttribute("error", "Fees record not found");
                return "/errorpage/error"; // Return an error page or handle the error accordingly
            }

            Establishment existingEstablishment = establishmentService.getEstablishmentById(establishmentId);
            if (!fees.getEstablishment().equals(existingEstablishment)) {
                model.addAttribute("error", "The fee does not belong to the specified establishment.");
                return "/errorpage/error"; // Return an error page or handle the error accordingly
            }

            fees.setEntryFee(entryFee);
            feesRepo.save(fees);

            // Fetch necessary data for the view
            List<OtherFees> otherFeesList = otherFeesService.getOtherFeesByEstablishmentEstablishmentId(establishmentId);
            List<Nationality> nationalities = nationalityRepo.findAll();
            List<Category> categories = categoryRepo.findAll();

            // Add attributes to the model
            model.addAttribute("establishment", existingEstablishment);
            model.addAttribute("otherFees", otherFeesList);
            model.addAttribute("nationalities", nationalities);
            model.addAttribute("categories", categories);
            model.addAttribute("message", "Entry fee updated successfully");

            // Return the view name
            return "edit-establishments";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while updating the entry fee: " + e.getMessage());
            return "/errorpage/error"; // Return an error page or handle the error accordingly
        }
    }



    @GetMapping("/delete/{id}")
    public String deleteEstablishment(@PathVariable Long id) {
        establishmentService.deleteEstablishmentById(id);
        return "redirect:/establishments";
    }

    @GetMapping("/deleteOtherFee/{otherFeesId}")
    public ResponseEntity<?> deleteOtherFee(@PathVariable("otherFeesId") Long otherFeesId) {
        otherFeesService.deleteOtherFee(otherFeesId);
        return ResponseEntity.ok("Deleted");
    }

//    @GetMapping("/deleteFees/{feesId}")
//    public ResponseEntity<?> deleteFees(@PathVariable("feesId") Long feesId) {
//        feesServiceImpl.deleteFeesById(feesId);
//        return ResponseEntity.ok("Deleted");
//    }
@GetMapping("/deleteFees/{feesId}")
public String deleteEntryFee(@PathVariable Long feesId, RedirectAttributes redirectAttributes) {
    try {
        Fees fee = feesRepo.findById(feesId).orElseThrow(() -> new IllegalArgumentException("Invalid fees ID"));
        Long establishmentId = fee.getEstablishment().getEstablishmentId();
        feesRepo.deleteById(feesId);

        // Check if there are no entry fees left for the establishment
        if (feesRepo.countByEstablishmentId(establishmentId) == 0) {
            Establishment establishment = establishmentService.getEstablishmentById(establishmentId);
            establishment.setStatus(false);
            establishmentService.updateEstablishment(establishment);
        }

        redirectAttributes.addFlashAttribute("successMessage", "Entry fee deleted successfully.");
        redirectAttributes.addAttribute("id", establishmentId);
        return "redirect:/establishments/viewEstablishment/{id}";
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Unable to delete entry fee at this time.");
        return "errorPage/error";
    }
}

    @GetMapping("/establishmentDetails/{establishmentId}")
    public String showEstablishmentImageDetails(@PathVariable Long establishmentId, Model model) {
        Establishment establishment = establishmentService.getEstablishmentById(establishmentId);
        model.addAttribute("establishment", establishment);
        return "establishmentDetails";
    }

    @GetMapping("user/{establishmentId}")
    public ResponseEntity<byte[]> getEstablishmentImage(@PathVariable Long establishmentId) {
        try {
            byte[] imageData = establishmentService.getEstablishmentImageById(establishmentId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/addEntryFees")
    public String addEntryFee(@RequestParam("establishmentId") Long establishmentId,
                              @RequestParam("nationality") Long nationalityId,
                              @RequestParam("category") Long categoryId,
                              @RequestParam("entryFee") Double entryFee,
                              RedirectAttributes redirectAttributes) {
        try {
            Establishment establishment = establishmentService.getEstablishmentById(establishmentId);
            if (establishment == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Establishment not found.");
                return "redirect:/errorPage/error";
            }

            // Check if the combination of nationality and category already exists
            boolean exists = feesRepo.existsByEstablishmentEstablishmentIdAndNationalityNationalityIdAndCategoryCategoryId(establishmentId, nationalityId, categoryId);
            if (exists) {
                redirectAttributes.addFlashAttribute("errorMessage", "This fee configuration already exists. Please choose another.");
            }

            Fees fees = new Fees();
            fees.setEstablishment(establishment);
            fees.setNationality(nationalityRepo.findById(nationalityId).orElseThrow(() -> new IllegalArgumentException("Invalid nationality ID")));
            fees.setCategory(categoryRepo.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Invalid category ID")));
            fees.setEntryFee(entryFee);
            fees.setEnteredOn(LocalDateTime.now());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            fees.setEnteredBy(authentication.getName());

            feesRepo.save(fees);

            // Activate the establishment if not already active
            if (!establishment.getStatus()) {
                establishment.setStatus(true);
                establishmentService.updateEstablishment(establishment);
            }

            redirectAttributes.addFlashAttribute("successMessage", "Entry fee added successfully and establishment activated.");
            redirectAttributes.addAttribute("id", establishmentId);
            return "redirect:/establishments/viewEstablishment/{id}";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Unable to process your request at this time.");
            return "/errorPage/error";
        }
    }


    @GetMapping("/checkCombinationExists")
    @ResponseBody
    public Map<String, Boolean> checkCombinationExists(@RequestParam("establishmentId") Long establishmentId,
                                                       @RequestParam Long nationalityId,
                                                       @RequestParam Long categoryId) {
        boolean exists = feesServiceImpl.combinationExists(establishmentId,nationalityId, categoryId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return response;
    }

    @GetMapping("/fetchCounts")
    public ResponseEntity<Map<String, Long>> fetchCounts() {
        Long establishmentsCount = establishmentRepo.countEstablishments();
        Long visitorsCount = 0L; // Replace this with the actual logic to fetch visitor counts if needed
        Long activeEstablishmentsCount = establishmentRepo.countActiveEstablishments();
        Long inactiveEstablishmentsCount = establishmentRepo.countInactiveEstablishments();

        Map<String, Long> counts = new HashMap<>();
        counts.put("establishments", establishmentsCount);
        counts.put("visitors", visitorsCount);
        counts.put("activeEstablishments", activeEstablishmentsCount);
        counts.put("inactiveEstablishments", inactiveEstablishmentsCount);

        return ResponseEntity.ok(counts);
    }




}
