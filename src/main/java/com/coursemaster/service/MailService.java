package com.coursemaster.service;

import com.coursemaster.dto.mail.MailStructureDto;

public interface MailService {
    void sendMail(MailStructureDto mailStructure);
}
