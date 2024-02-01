package com.practice.zooticketportal.audit;


import com.practice.zooticketportal.entity.Audit;
import com.practice.zooticketportal.repositories.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AuditController {

    @Autowired
    private AuditRepository auditRepository;

    @GetMapping("/audit")
    public String showAuditData(Model model) {
        // Retrieve audit data from the database
        List<Audit> auditList = auditRepository.findAll();

        // Add the auditList to the model for Thymeleaf to use in the template
        model.addAttribute("auditList", auditList);

        // Return the Thymeleaf template name
        return "audit";
    }
}
