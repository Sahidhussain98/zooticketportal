package com.practice.zooticketportal.serviceimpl;

import com.practice.zooticketportal.entity.OtherFees;
import com.practice.zooticketportal.repositories.OtherFeesRepo;
import com.practice.zooticketportal.service.OtherFeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtherFeesServiceImpl implements OtherFeesService {
    @Autowired
    private OtherFeesRepo otherFeesRepo;
    @Override
    public List<OtherFees> getOtherFeesByEstablishmentEstablishmentId(Long establishmentId) {
        return otherFeesRepo.findByEstablishmentEstablishmentId(establishmentId);
    }

    @Override
    public void deleteOtherFee(Long otherFeesId) {
        otherFeesRepo.deleteById(otherFeesId);
    }

    @Override
    public OtherFees findById(Long id) {
        return otherFeesRepo.findById(id).orElse(null);
    }

    @Override
    public OtherFees save(OtherFees otherFees) {
        return otherFeesRepo.save(otherFees);
    }


}
