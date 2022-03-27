package com.toy.be.domain.member.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class MemberEmailCertDaoImpl implements MemberEmailCertDao {
    @Autowired
    private SqlSession session;
    private static String namespace = "com.toy.be.dao.MemberEmailCertMapper.";

    @Override
    public int selectCertCode(String cert_code) throws Exception {
        return session.selectOne(namespace + "selectCertCode", cert_code);
    }

    @Override
    public int insertCertCode(Map map) throws Exception {
        return session.insert(namespace + "insertCertCode", map);
    }
}
