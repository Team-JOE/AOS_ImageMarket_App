package com.aosproject.imagemarket.Bean;

public class JsonUserDY {

    String email, pwd, name, phoneNumber, bank, owner, account;

    public JsonUserDY(String email, String pwd, String name, String phoneNumber, String bank, String owner, String account) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.bank = bank;
        this.owner = owner;
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
