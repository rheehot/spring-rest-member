package com.toy.be.domain.member.service;

import com.toy.be.domain.member.dto.MemberDto;
import com.toy.be.domain.member.req.MemberRegisterPostReq;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {
    boolean memberIsEmailCheck(String email);

    MemberDto memberDetail(String email);

    boolean memberIsLogin(Integer id);

    boolean memberEmailCert(String certCode);

    boolean memberEmailCertCheck(Integer id);

    boolean memberIsLoginCheck(Integer id);

    boolean memberRegister(MemberRegisterPostReq memberRegisterInfo, String cert_code);
}
