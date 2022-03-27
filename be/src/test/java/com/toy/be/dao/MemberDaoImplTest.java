package com.toy.be.dao;

import com.toy.be.domain.member.dao.MemberDao;
import com.toy.be.domain.member.dao.MemberEmailCertDao;
import com.toy.be.domain.member.dto.MemberDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class MemberDaoImplTest {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MemberEmailCertDao memberEmailCertDao;
    @Autowired
    private DataSource dataSource;

    @Test
    public void selectDetailTest() throws Exception {
        MemberDto memberDto = memberDao.selectDetail("test@test.com");
        System.out.println("memberDto = " + memberDto);
        assertTrue(memberDto.getPassword().equals("1234"));
    }

    @Test
    public void selectEmailTest() throws Exception {
        String email = memberDao.selectEmail("test@test.com");
        System.out.println("email = " + email);
        assertTrue(email.equals("test@test.com"));
    }

    @Test
    public void updateIsLoginTest() throws Exception {
        MemberDto memberDto = memberDao.selectDetail("test@test.com");
        boolean isLogin = memberDto.isLogin();

        memberDao.updateIsLogin(memberDto.getId());

        memberDto = memberDao.selectDetail("test@test.com");
        assertTrue(isLogin == !memberDto.isLogin());
    }

    @Test
    public void insertTest() throws Exception{
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("test12@test.com");
        memberDto.setPassword("1234");
        memberDto.setUsername("transaction");
        memberDto.setPhone("01098765432");
        memberDto.setBirthday("1900-01-01");

        String cert_code = RandomStringUtils.randomAlphanumeric(20);

        // 선언만해도 트랜잭션이 생성됨 : tm.commit(status) 를 하지 않으면 커밋도 되지 않음
        PlatformTransactionManager tm = new DataSourceTransactionManager(dataSource);
        TransactionStatus status = tm.getTransaction(new DefaultTransactionDefinition());

        try {
            memberDao.insert(memberDto);

            Map map = new HashMap();
            map.put("id", memberDto.getId());
            map.put("cert_code", cert_code);
            memberEmailCertDao.insertCertCode(map);

            System.out.println("commit");
            tm.commit(status);
        }catch (Exception e){
            System.out.println("rollback");
            tm.rollback(status);
        }
    }

    @Test
    public void insertCertCodeTest() throws Exception{
        Map map = new HashMap();
        map.put("id", "167");
        map.put("cert_code", "12345678");
        memberEmailCertDao.insertCertCode(map);
    }

    @Test
    public void selectCertCodeTest() throws Exception{
        int memberId = memberEmailCertDao.selectCertCode("12345678");
        System.out.println("memberId = " + memberId);
        assertTrue(memberId == 167);
    }
}