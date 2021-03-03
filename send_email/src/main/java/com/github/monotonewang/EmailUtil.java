package com.github.monotonewang;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//https://zhuanlan.zhihu.com/p/133120035
public class EmailUtil {

    public void send163(String sendEmail, String sendPassword, String sendUsername, String sendAuthCode, String receiveEmail) {
        new Thread() {
            @Override
            public void run() {
                super.run();

                Properties props = new Properties();
                props.put("mail.smtp.auth", true);
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.host", "smtp.163.com");
                // 设置SSL加密(未采用SSL时，端口一般为25，可以不用设置；采用SSL时，端口为465，需要显示设置)
//                props.setProperty("mail.smtp.port", "465");
                props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.setProperty("mail.smtp.socketFactory.fallback", "false");
                props.setProperty("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.username", sendUsername);
                props.put("mail.smtp.password", sendPassword);
                Authenticator authenticator = new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sendUsername, sendAuthCode);
                    }
                };
                Session session = Session.getInstance(props, authenticator);
                session.setDebug(true);
                try {
                    MimeMessage msg = new MimeMessage(session);
                    msg.setFrom(new InternetAddress(sendEmail, sendUsername, "UTF-8"));
                    msg.setRecipients(Message.RecipientType.TO, receiveEmail);
                    msg.setSubject("标题");
                    msg.setSentDate(new Date());
                    msg.setText("你好!\n这里是来自ubt的信息");
                    Transport.send(msg);
                } catch (MessagingException |
                        UnsupportedEncodingException e) {

                }
            }
        }.start();
    }
}
