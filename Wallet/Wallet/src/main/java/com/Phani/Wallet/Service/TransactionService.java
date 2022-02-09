package com.Phani.Wallet.Service;

import com.Phani.Wallet.Entity.Transaction;
import com.Phani.Wallet.Entity.Wallet;
import com.Phani.Wallet.Repository.TransactionRepository;
import com.Phani.Wallet.Repository.WalletRepository;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    public Transaction MoneyTransfer(Transaction transaction){
        Wallet senderWallet = walletRepository.findById(transaction.getSenderPhoneNumber()).get();
        Wallet ReceiverWallet = walletRepository.findById(transaction.getReceiverPhoneNumber()).get();
        if(senderWallet.getBalance() >= transaction.getAmount()){

            senderWallet.setBalance(senderWallet.getBalance()-transaction.getAmount());
            walletRepository.save(senderWallet);

            ReceiverWallet.setBalance(ReceiverWallet.getBalance()+transaction.getAmount());
            walletRepository.save(ReceiverWallet);

            transaction.setStatus("Transaction Successful");
            return transactionRepository.save(transaction);
        }
        else {
            transaction.setStatus("Transaction Failed");
            return transactionRepository.save(transaction);
        }
    }


    public List<Transaction>AllTransactions(){
      return   transactionRepository.findAll();
    }



    public List<Transaction>GetUserTransactions(String UserphoneNumber){
        List<Transaction>userTransactions = new ArrayList<Transaction>();
        List<Transaction>transactionsList = AllTransactions();
        for(Transaction transaction : transactionsList){
            if(transaction.getSenderPhoneNumber().equals(UserphoneNumber) || transaction.getReceiverPhoneNumber().equals(UserphoneNumber)){
                userTransactions.add(transaction);
            }
        }
        return userTransactions;
    }


    public Page<Transaction>transactionPagination(int offset,int pagesize,String field){
        return transactionRepository.findAll(PageRequest.of(offset,pagesize).withSort(Sort.by(field)));
    }
}
