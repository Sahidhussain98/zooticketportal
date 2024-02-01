package com.practice.zooticketportal.audit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditService auditService;

    @Before("execution(* com.zoo.loginServices.*.*(..))")
    public void beforeServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String entityName = getEntityName(joinPoint);
        Long entityId = getEntityId(joinPoint);
        String username = getUsername(joinPoint);

        auditService.audit("CALL", entityName, entityId, username);
    }

    private String getEntityName(JoinPoint joinPoint) {
        // Assuming that the entity name is present in the class name
        String className = joinPoint.getTarget().getClass().getSimpleName();
        return className.replace("Service", ""); // Remove "Service" suffix
    }

    private Long getEntityId(JoinPoint joinPoint) {
        // Assuming that the entity ID is the first parameter of the method
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof Long) {
            return (Long) args[0];
        }
        return null;
    }

    private String getUsername(JoinPoint joinPoint) {
        // Assuming that the username is the second parameter of the method
        Object[] args = joinPoint.getArgs();
        if (args.length > 1 && args[1] instanceof String) {
            return (String) args[1];
        }
        return null;
    }
}
