package com.toy.be.global.email;

public interface EmailService {
    String mailContent(String certCode);

    boolean mailSend(EmailDto emailDto);
}
