package com.example.ngBACKEND.Controller;

import com.example.ngBACKEND.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("api/ngCRUD")
public class EmailController {

    @Autowired
    private EmailService emailService;
    Logger logger;
    @GetMapping("/sendEmail")
    public String sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body) {
        try {
            emailService.sendEmail(to, subject, body);
            return "Email sent successfully!";
        } catch (Exception e) {
            return "An unexpected error occurred: " + e.getMessage();
        }
    }
}
