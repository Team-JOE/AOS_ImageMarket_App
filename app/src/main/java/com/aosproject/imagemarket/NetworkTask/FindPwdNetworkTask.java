package com.aosproject.imagemarket.NetworkTask;

import android.content.Context;
import android.os.AsyncTask;

import com.aosproject.imagemarket.Util.ShareVar;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class FindPwdNetworkTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private Session session;
    private String email, subject, message;

    public FindPwdNetworkTask(Context context, String email, String subject, String message) {
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");


        session = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(ShareVar.EMAIL, ShareVar.PASSWORD);
            }
        });

        MimeMessage msg = new MimeMessage(session);
        try{

            msg.setFrom(new InternetAddress(ShareVar.EMAIL)); // 보내는 사람
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email)); // 받는 사람
            msg.setSubject(subject); // 메일 제목
            msg.setContent(message, "text/html;charset=UTF-8"); // 내용

            Transport.send(msg);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
