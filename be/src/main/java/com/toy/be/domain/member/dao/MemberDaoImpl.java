package com.toy.be.domain.member.dao;

import com.toy.be.domain.member.dto.MemberDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemberDaoImpl implements MemberDao {

    @Autowired
    private SqlSession session;
    private static String namespace = "com.toy.be.dao.MemberMapper.";

    @Override
    public MemberDto selectDetail(String email) throws Exception {
        return session.selectOne(namespace + "selectDetail", email);
    }
    @Override
    public String selectEmail(String email) throws Exception {
        return session.selectOne(namespace + "selectEmail", email);
    }
    @Override
    public boolean selectIsLogin(Integer id) throws Exception {
        return session.selectOne(namespace + "selectIsLogin", id);
    }
    @Override
    public boolean selectEmailCert(Integer id) throws Exception {
        return session.selectOne(namespace + "selectEmailCert", id);
    }
    @Override
    public Integer updateIsLogin(Integer id) throws Exception {
        return session.update(namespace + "updateIsLogin", id);
    }
    @Override
    public Integer updateEmailCert(Integer id) throws Exception {
        return session.update(namespace + "updateEmailCert", id);
    }
    @Override
    public Integer insert(MemberDto memberDto) throws  Exception {
        return session.insert(namespace + "insert", memberDto);
    }
}