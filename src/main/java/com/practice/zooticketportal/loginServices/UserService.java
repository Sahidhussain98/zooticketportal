package com.practice.zooticketportal.loginServices;

import com.practice.zooticketportal.audit.AuditService;
import com.practice.zooticketportal.entity.AllUser;
import com.practice.zooticketportal.entity.Roles;
import com.practice.zooticketportal.repositories.AllUserRepo;
import com.practice.zooticketportal.repositories.RolesRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private AllUserRepo AllUserRepo;

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private AuditService auditService;
    @Transactional
    void registerUserWithCitizenRole(String phoneNumber) {
        System.out.println("start registerUserWithCitizenRole");
        // Check if the user already exists
        AllUser existingUser = AllUserRepo.findByPhoneNumber(phoneNumber);

        if (existingUser != null) {
            System.out.println("user exist");
            // User already exists, handle accordingly (update, notify, etc.)
        } else {
            // Create a new user
            System.out.println("entered create new user");
            AllUser newUser = new AllUser();

            newUser.setPhoneNumber(phoneNumber);

            // Get the "CITIZEN" role from the database
//            Roles citizenRole = rolesRepo.findByRoleName("CITIZEN");
            Roles citizenRole = rolesRepo.findByRoleName("CITIZEN");
            if (citizenRole != null) {
                newUser.setUsername(newUser.getUsername());
                newUser.setPassword(newUser.getPassword());
                newUser.setCitizenRole(citizenRole);
                System.out.println("user saved");
                // Save the user to the database
                AllUserRepo.save(newUser);
            } else {
                // Handle the case where "CITIZEN" role is not found
                System.out.println("Entering registerUserWithoutCitizenRole");

            }
        }
        auditService.audit("REGISTER_USER_WITH_CITIZEN_ROLE", "UserService", null, null);
    }


}
