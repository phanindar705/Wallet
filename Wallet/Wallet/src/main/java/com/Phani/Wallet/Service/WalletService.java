package com.Phani.Wallet.Service;


import com.Phani.Wallet.Entity.Wallet;
import com.Phani.Wallet.Repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    // Check wallet exists or not
    public  boolean CheckWallet(Wallet wallet){
        List<Wallet> AllWallets = walletRepository.findAll();
        for(Wallet ExistingWallet : AllWallets){
            if(ExistingWallet.getPhoneNumber().equals(wallet.getPhoneNumber()))
            return false;
        }
        return true;
    }


    public Wallet CreateUserWallet(Wallet wallet){
        return walletRepository.save(wallet);
    }


    public List<Wallet> GeTAllWalletDetails(){
        return walletRepository.findAll();
    }

    public Wallet GetWalletDetailsById(String phoneNumber){
        return walletRepository.findById(phoneNumber).get();
    }

    public String DeleteWalletDetailsById(String phoneNumber){
        if(walletRepository.findById(phoneNumber).isPresent()){
            walletRepository.deleteById(phoneNumber);
            return "Wallet Deleted";
        }
        else {
            return "Wallet Doesn't Exist";
        }
    }

}
