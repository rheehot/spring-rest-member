package com.toy.be.domain.member.controller;

import com.toy.be.domain.member.dto.MemberDto;
import com.toy.be.domain.member.req.MemberLoginPostReq;
import com.toy.be.domain.member.req.MemberRegisterPostReq;
import com.toy.be.domain.member.service.MemberService;
import com.toy.be.global.email.EmailDto;
import com.toy.be.global.email.EmailService;
import com.toy.be.global.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ApiModel(value = "회원 관리 API")
@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    EmailService emailService;

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "Email과 Password입력을 통해 로그인 한다.")
    public ResponseEntity<BaseResponseBody> memberLogin(@RequestBody @ApiParam(value = "로그인 정보", required = true) MemberLoginPostReq memberLoginInfo){
        log.info("memberLogin - Call");

        if (memberService.memberIsEmailCheck(memberLoginInfo.getEmail())){
            MemberDto memberDto = memberService.memberDetail(memberLoginInfo.getEmail());

            // Email + Password 를 통해 계정확인
            if (memberLoginInfo.getPassword().equals(memberDto.getPassword())){
                // 이메일인증확인
                if (memberService.memberEmailCertCheck(memberDto.getId())) {
                    // 다른곳에서 로그인 상태인지 확인
                    if (!memberService.memberIsLoginCheck(memberDto.getId())) {
                        // 로그인상태를 True로 변경
                        if (memberService.memberIsLogin(memberDto.getId())) {
                            return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
                        } else {
                            return ResponseEntity.status(401).body(BaseResponseBody.of(401, "update isLogin Fail"));
                        }
                    } else {
                        return ResponseEntity.status(402).body(BaseResponseBody.of(402, "Already Login"));
                    }
                } else{
                    return ResponseEntity.status(410).body(BaseResponseBody.of(410, "Not Email Cert"));
                }
            } else {
                return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Invalid Password"));
            }
        } else {
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Not Matched Email"));
        }
    }

    @GetMapping("/logout/{memberId}")
    @ApiOperation(value = "로그아웃", notes = "로그인여부를 확인후 로그아웃으로 처리한다.")
    public ResponseEntity<BaseResponseBody> memberLogout(@PathVariable @ApiParam(value = "회원 고유 id", required = true, example = "0") int memberId){
        log.info("memberLogout - Call");

        if (memberService.memberIsLoginCheck(memberId)){
            if (memberService.memberIsLogin(memberId)){
                return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
            } else {
                return ResponseEntity.status(401).body(BaseResponseBody.of(401, "update isLogin Fail"));
            }
        } else {
            return ResponseEntity.status(406).body(BaseResponseBody.of(406, "Not Login"));
        }
    }

    @PostMapping("/register")
    @ApiOperation(value = "회원가입", notes = "회원가입 정보를 입력받아 회원을 등록한다.")
    public ResponseEntity<BaseResponseBody> memberRegister(@RequestBody @ApiParam(value = "회원가입 정보", required = true)MemberRegisterPostReq memberRegisterInfo){
        log.info("memberRegister - Call");

        String cert_code = memberRegisterInfo.getEmail().replace("@", "").replace(".", "") + RandomStringUtils.randomAlphanumeric(10);
        if (memberService.memberRegister(memberRegisterInfo, cert_code)){
            if (emailService.mailSend(new EmailDto(memberRegisterInfo.getEmail(), cert_code))){
                return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
            } else{
                return ResponseEntity.status(411).body(BaseResponseBody.of(411, "Mail Send Fail"));
            }

        } else {
            // Status 407 로하면 "TypeError:Failed to fetch" 발생
            return ResponseEntity.status(408).body(BaseResponseBody.of(408, "Register Fail"));
        }
    }

    @GetMapping("/register/{certCode}")
    @ApiOperation(value = "회원가입 이메일인증", notes = "회원가입시 등록된 이메일을 인증한다.")
    public ResponseEntity<BaseResponseBody> memberRegisterCert(@PathVariable @ApiParam(value = "이메일 인증코드", required = true, example = "0") String certCode){
        log.info("memberRegisterCert - Call");

        if (memberService.memberEmailCert(certCode)){
            return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
        } else {
            return ResponseEntity.status(409).body(BaseResponseBody.of(409, "Already verified cert code or invalid cert code"));
        }
    }
}
