package com.Phani.Wallet.Entity;


import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long UserId;
    private String userName;
    private String phoneNumber;
    private String emailId;
    private String active;


    public User() {

    }

    public User(Long userId, String userName, String phoneNumber, String emailId, String active) {
        UserId = userId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.active = active;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
