package com.practice.zooticketportal.service;

import com.practice.zooticketportal.entity.NonWorkingDays;
import com.practice.zooticketportal.repositories.NonWorkingDaysRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface NonWorkingDaysService {
    List<NonWorkingDays> getNonWorkingDatesByEstablishmentId(Long establishmentId);

    void deleteNonWorkingDay(Long nonWorkingDayId);

}
