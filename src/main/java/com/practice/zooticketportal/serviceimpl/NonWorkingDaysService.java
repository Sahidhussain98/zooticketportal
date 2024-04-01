package com.practice.zooticketportal.serviceimpl;

import com.practice.zooticketportal.entity.NonWorkingDays;
import com.practice.zooticketportal.repositories.NonWorkingDaysRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NonWorkingDaysService {
    @Autowired
    private NonWorkingDaysRepo nonWorkingDaysRepo;

    public List<NonWorkingDays> getNonWorkingDatesByEstablishmentId(Long establishmentId) {
        return nonWorkingDaysRepo.findByEstablishmentEstablishmentId(establishmentId);
    }
}
