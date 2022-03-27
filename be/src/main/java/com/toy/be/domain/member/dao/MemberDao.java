package com.toy.be.domain.member.dao;

import com.toy.be.domain.member.dto.MemberDto;

import java.util.Map;

public interface MemberDao {
    MemberDto selectDetail(String email) throws Exception;

    String selectEmail(String email) throws Exception;

    boolean selectIsLogin(Integer id) throws Exception;

    boolean selectEmailCert(Integer id) throws Exception;

    Integer updateIsLogin(Integer id) throws Exception;

    Integer updateEmailCert(Integer id) throws Exception;

    Integer insert(MemberDto memberDto) throws  Exception;
}
