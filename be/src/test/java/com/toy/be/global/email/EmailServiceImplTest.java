package com.toy.be.global.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class EmailServiceImplTest {

    @Autowired
    EmailService emailService;

    @Test
    public void mailTest() throws Exception{
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.naver.com");
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.naver.com");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
           String un = "doran2322";
           String pw = "alstkd1544.!";
           protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(un, pw);
           }
        });

        session.setDebug(true);

        Message mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress("doran2322@naver.com"));
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("doran2322@gmail.com"));
        mimeMessage.setSubject("test title");
        mimeMessage.setText("test content");
        Transport.send(mimeMessage);
    }
    @Test
    public void mailCertCodeTest() throws Exception{
        System.out.println("emailService = " + emailService);
        emailService.mailSend(new EmailDto("doran2322@naver.com", "12345678"));
    }

}