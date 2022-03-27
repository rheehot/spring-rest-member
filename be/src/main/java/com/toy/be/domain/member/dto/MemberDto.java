package com.toy.be.domain.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@ToString
@ApiModel(value = "Member", description = "회원 정보")
public class MemberDto {
    @ApiModelProperty(value = "회원 번호", example = "1")
    private Integer id;
    @ApiModelProperty(value = "회원 email", example = "test@test.com")
    private String email;
    @ApiModelProperty(value = "회원 비밀번호", example = "1234")
    private String password;
    @ApiModelProperty(value = "회원 이름", example = "박개발")
    private String username;
    @ApiModelProperty(value = "회원 휴대폰 번호", example = "01012345678")
    private String phone;
    @ApiModelProperty(value = "회원 생일", example = "1999-01-01")
    private Date birthday;
    @ApiModelProperty(value = "이메일 인증 여부", example = "1")
    private boolean email_cert;
    @ApiModelProperty(value = "회원 가입일시", example = "1")
    private Date register_datetime;
    @ApiModelProperty(value = "회원 로그인 여부", example = "1")
    private boolean isLogin;

    public MemberDto() {}

    public void setBirthday(String birthday) {
        this.birthday = toDate(birthday);
    }

    private Date toDate(String date) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
