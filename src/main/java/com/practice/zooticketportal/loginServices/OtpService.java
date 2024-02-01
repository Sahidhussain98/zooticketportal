package com.practice.zooticketportal.loginServices;


import com.practice.zooticketportal.audit.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpService {
    @Autowired
    private AuditService auditService;

    // Hardcoded OTP for demonstration purposes
    private static String generatedOtp;
    public String generateOtp() {
        // In a real-world scenario, you would generate a dynamic OTP here
        generatedOtp = "123456";
        auditService.audit("GENERATE_OTP", "OtpService", null, null);
        return generatedOtp;

    }
    public static String getGeneratedOtp() {
        return generatedOtp;
    }
}
