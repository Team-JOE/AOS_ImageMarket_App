package com.aosproject.imagemarket.Bean;

public class UserModel {
    static String email, pwd, name, phoneNumber, bank, owner, account;

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserModel.email = email;
    }

    public static String getPwd() {
        return pwd;
    }

    public static void setPwd(String pwd) {
        UserModel.pwd = pwd;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        UserModel.name = name;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(String phoneNumber) {
        UserModel.phoneNumber = phoneNumber;
    }

    public static String getBank() {
        return bank;
    }

    public static void setBank(String bank) {
        UserModel.bank = bank;
    }

    public static String getOwner() {
        return owner;
    }

    public static void setOwner(String owner) {
        UserModel.owner = owner;
    }

    public static String getAccount() {
        return account;
    }

    public static void setAccount(String account) {
        UserModel.account = account;
    }
}
