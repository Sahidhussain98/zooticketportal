package com.practice.zooticketportal.serviceimpl;

import com.practice.zooticketportal.repositories.FeesRepo;
import com.practice.zooticketportal.service.FeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeesServiceImpl implements FeesService {
    @Autowired
    private FeesRepo feesRepo;
    public boolean combinationExists(Long nationalityId, Long categoryId) {
        return feesRepo.existsByNationalityNationalityIdAndCategoryCategoryId(nationalityId, categoryId);
    }
}
