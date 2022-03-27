package com.toy.be.domain.member.dao;

import java.util.Map;

public interface MemberEmailCertDao {
    int selectCertCode(String cert_code) throws Exception;

    int insertCertCode(Map map) throws Exception;
}
