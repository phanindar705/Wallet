package com.Phani.Wallet.Service;

import com.Phani.Wallet.Entity.Transaction;
import com.Phani.Wallet.Entity.User;
import com.Phani.Wallet.Entity.Wallet;
import com.Phani.Wallet.Repository.TransactionRepository;
import com.Phani.Wallet.Repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private WalletRepository walletRepository;

    @Test
    void moneyTransferTest() {
      Wallet Senderwallet = new Wallet("12345",150);
      Wallet ReceiverWallet = new Wallet("54321",100);
      Mockito.when(walletRepository.findById("12345")).thenReturn(Optional.of(Senderwallet));
      Mockito.when(walletRepository.findById("54321")).thenReturn(Optional.of(ReceiverWallet));
      Transaction transaction = new Transaction(0L,"12345","54321",100,"processing");
      Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
      Assertions.assertEquals(transaction,transactionService.MoneyTransfer(transaction));
    }

    @Test
    void allTransactionsTest() {
        Mockito.when(transactionRepository.findAll()).thenReturn(Stream.of(new Transaction(0L,"12345","54321",100L,"processing")).collect(Collectors.toList()));
        Assertions.assertEquals(1,transactionService.AllTransactions().size());
    }

    @Test
    void getUserTransactions() {
        Mockito.when(transactionRepository.findAll()).thenReturn(Stream.of(new Transaction(0L,"12345","54321",100L,"processing")).collect(Collectors.toList()));
        Assertions.assertEquals(1,transactionService.GetUserTransactions("12345").size());

    }
}