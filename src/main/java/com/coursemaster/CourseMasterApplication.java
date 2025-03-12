package com.coursemaster;

import com.coursemaster.dto.mail.MailStructureDto;
import com.coursemaster.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.coursemaster.config.properties")
public class CourseMasterApplication implements CommandLineRunner {

    @Autowired
    private MailService mailService;

    public static void main(String[] args) {
        SpringApplication.run(CourseMasterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        mailService.sendMail(new MailStructureDto("namdaribrahim04@gmail.com", "Привкет", "namdaribrahim04@gmail.com"));
    }
}
