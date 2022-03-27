package com.toy.be.domain.member.service;

import com.toy.be.domain.member.dao.MemberDao;
import com.toy.be.domain.member.dao.MemberEmailCertDao;
import com.toy.be.domain.member.dto.MemberDto;
import com.toy.be.domain.member.req.MemberRegisterPostReq;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;
    @Autowired
    MemberEmailCertDao memberEmailCertDao;
    @Autowired
    DataSource dataSource;

    @Override
    public boolean memberIsEmailCheck(String email) {
        try{
            if (email.equals(memberDao.selectEmail(email))) return true;
            else return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public MemberDto memberDetail(String email){
        try{
            return memberDao.selectDetail(email);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean memberIsLogin(Integer id){
        try{
            return memberDao.updateIsLogin(id) == 1 ? true : false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean memberEmailCert(String certCode){
        try{
            int id = memberEmailCertDao.selectCertCode(certCode);

            // 이미 체크되어있으면 실패
            if (memberDao.selectEmailCert(id)) throw new Exception();
            
            memberDao.updateEmailCert(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean memberEmailCertCheck(Integer id){
        try{
            return memberDao.selectEmailCert(id);
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean memberIsLoginCheck(Integer id){
        try{
            return memberDao.selectIsLogin(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean memberRegister(MemberRegisterPostReq memberRegisterInfo, String cert_code){
        MemberDto memberDto = new MemberDto();

        memberDto.setEmail(memberRegisterInfo.getEmail());
        memberDto.setPassword(memberRegisterInfo.getPassword());
        memberDto.setUsername(memberRegisterInfo.getUsername());
        memberDto.setPhone(memberRegisterInfo.getPhone());
        memberDto.setBirthday(memberRegisterInfo.getBirthday());

        PlatformTransactionManager tm = new DataSourceTransactionManager(dataSource);
        TransactionStatus status = tm.getTransaction(new DefaultTransactionDefinition());

        try{
            memberDao.insert(memberDto);
            Map map = new HashMap();
            map.put("id", memberDto.getId());
            map.put("cert_code", cert_code);
            memberEmailCertDao.insertCertCode(map);

            tm.commit(status);
            return true;
        } catch (Exception e){
            tm.rollback(status);
            return false;
        }
    }
}
