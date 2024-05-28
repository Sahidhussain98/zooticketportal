package com.practice.zooticketportal.service;

public interface FeesService {
    boolean combinationExists(Long establishmentId,Long nationalityId, Long categoryId);
    void deleteFeesById(Long feesId);
}
