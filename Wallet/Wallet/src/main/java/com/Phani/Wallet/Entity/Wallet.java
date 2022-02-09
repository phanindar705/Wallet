package com.Phani.Wallet.Entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserWallets")
public class Wallet {

    @Id
    private String phoneNumber;

    private double Balance;

    public Wallet() {

    }

    public Wallet(String phoneNumber, double balance) {
        this.phoneNumber = phoneNumber;
        Balance = balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }
}
