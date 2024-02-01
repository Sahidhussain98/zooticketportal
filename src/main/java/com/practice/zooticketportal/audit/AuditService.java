package com.practice.zooticketportal.audit;


import com.practice.zooticketportal.entity.Audit;
import com.practice.zooticketportal.repositories.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    public void audit(String action, String entityName, Long entityId, String username) {
        Audit audit = new Audit();
        audit.setAction(action);
        audit.setEntityName(entityName);
        audit.setEntityId(entityId);
        audit.setUsername(username);
        audit.setTimestamp(LocalDateTime.now());

        auditRepository.save(audit);
    }

    public List<Audit> getAllAuditLogs() {
        return auditRepository.findAll();
    }
}
