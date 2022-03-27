package com.toy.be.domain.member.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "MemberLoginPostReq", description = "멤버가 로그인시 필요한 정보")
public class MemberLoginPostReq {
    @ApiModelProperty(value = "회원 email", example = "test@test.com")
    String email;
    @ApiModelProperty(value = "회원 password", example = "1234")
    String password;

    public MemberLoginPostReq() {}
}
