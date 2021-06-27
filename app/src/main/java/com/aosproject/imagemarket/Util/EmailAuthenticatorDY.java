package com.aosproject.imagemarket.Util;

import javax.mail.PasswordAuthentication;

public class EmailAuthenticatorDY {
    private String id;
    private String pw;

    // constructor
    public EmailAuthenticatorDY(String id, String pw){
        super();
        this.id = id;
        this.pw = pw;
    }

    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(id, pw);
    }
}