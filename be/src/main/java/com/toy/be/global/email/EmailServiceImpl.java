package com.toy.be.global.email;

import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {
    private String sendMail = "doran2322@naver.com";
    private String username = "doran2322";
    private String password = "alstkd1544.!";
    private String subject = "이메일 인증을 완료해주세요.";

    @Override
    public String mailContent(String certCode){
        StringBuilder contentText = new StringBuilder();

        contentText.append("<h1>아래의 링크를 클릭하여 이메일 인증을 완료하세요.</h1>");
        contentText.append("<strong><a href=\"http://localhost:80/api/member/register/\"" + certCode + "\" >이메일인증</a></strong>");

        return contentText.toString();
    }

    @Override
    public boolean mailSend(EmailDto emailDto){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.naver.com");
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.naver.com");
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        session.setDebug(true);

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(sendMail, "Toy"));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailDto.getReceiveMail()));
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(mailContent(emailDto.getCertCode()), "text/html;charset=euc-kr");
            Transport.send(mimeMessage);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
