package com.practice.zooticketportal.service;

import com.practice.zooticketportal.entity.OtherFees;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OtherFeesService {
    List<OtherFees> getOtherFeesByEstablishmentEstablishmentId(Long establishmentId);


}
