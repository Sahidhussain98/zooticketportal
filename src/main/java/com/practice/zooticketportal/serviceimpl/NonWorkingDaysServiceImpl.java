package com.practice.zooticketportal.serviceimpl;

import com.practice.zooticketportal.entity.NonWorkingDays;
import com.practice.zooticketportal.repositories.NonWorkingDaysRepo;
import com.practice.zooticketportal.service.NonWorkingDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NonWorkingDaysServiceImpl  implements NonWorkingDaysService {
    @Autowired
    private NonWorkingDaysRepo nonWorkingDaysRepo;

    @Override
    public List<NonWorkingDays> getNonWorkingDatesByEstablishmentId(Long establishmentId) {
        return nonWorkingDaysRepo.findByEstablishmentEstablishmentId(establishmentId);
    }

    @Override
    public void deleteNonWorkingDay(Long nonWorkingDayId) {
        nonWorkingDaysRepo.deleteById(nonWorkingDayId);
    }
}
