package kr.or.ddit.admin.controller;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    private static final EmailSender INSTANCE = new EmailSender();
    private final Session session;
    private final String from;

    private EmailSender() {
        //String user = System.getenv("MAILTRAP_USER");
        //String pass = System.getenv("MAILTRAP_PASS");
        //String user = System.getenv("SMTP_USER");
        //String pass = System.getenv("SMTP_PASS");
        String user = "iseonyeob41@gmail.com";
        String pass = "bgmn axxc tdyh ympe";
       
      

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
       // props.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
       // props.put("mail.smtp.port", "2525");
       
        props.put("mail.smtp.starttls.required", "true");   
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
        
        // 4) 발신자 주소는 테스트용 임의값
        //this.from = "no-reply@example.com";
        this.from = user;
        
        session.setDebug(true);  
    }

    public static EmailSender getInstance() {
        return INSTANCE;
    }
    
    /**
     * @param to      받을 주소 (실제 존재하지 않아도 Mailtrap에 잡힙니다)
     * @param subject 메일 제목
     * @param body    메일 본문
     */

    public boolean send(String to, String subject, String body) {
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO,
                              InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setText(body);
            Transport.send(msg);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
