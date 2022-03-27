package com.toy.be.global.email;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmailDto {
    private String receiveMail;
    private String certCode;

    public EmailDto() {}

    public EmailDto(String receiveMail, String certCode) {
        this.receiveMail = receiveMail;
        this.certCode = certCode;
    }
}
