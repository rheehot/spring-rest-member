package com.toy.be.domain.member.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "MemberRegisterPostReq", description = "멤버가 회원가입시 필요한 정보")
public class MemberRegisterPostReq {
    @ApiModelProperty(value = "회원 email", example = "test@test.com")
    private String email;
    @ApiModelProperty(value = "회원 비밀번호", example = "1234")
    private String password;
    @ApiModelProperty(value = "회원 이름", example = "박개발")
    private String username;
    @ApiModelProperty(value = "회원 휴대폰 번호", example = "01012345678")
    private String phone;
    @ApiModelProperty(value = "회원 생일", example = "1999-01-01")
    private String birthday;

    public MemberRegisterPostReq() {}
}
