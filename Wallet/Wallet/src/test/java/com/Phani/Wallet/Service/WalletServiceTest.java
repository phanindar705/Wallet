package com.Phani.Wallet.Service;

import com.Phani.Wallet.Entity.Wallet;
import com.Phani.Wallet.Repository.UserRepository;
import com.Phani.Wallet.Repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WalletServiceTest {

    @Autowired
    private WalletService walletService;

    @MockBean
    private WalletRepository walletRepository;



    @Test
    void createUserWalletTest() {
        Wallet wallet = new Wallet("12345",500);
        Mockito.when(walletRepository.save(wallet)).thenReturn(wallet);
       Assertions.assertEquals(wallet, walletService.CreateUserWallet(wallet));
    }

    @Test
    void geTAllWalletDetailsTest() {
        Mockito.when(walletRepository.findAll()).thenReturn(Stream.of(new Wallet("12345",500)).collect(Collectors.toList()));
        Assertions.assertEquals(1,walletService.GeTAllWalletDetails().size());
    }

    @Test
    void getWalletDetailsById() {
        Wallet wallet = new Wallet("12345",500);
        Mockito.when(walletRepository.findById("12345")).thenReturn(Optional.of(wallet));
        Assertions.assertEquals(wallet,walletService.GetWalletDetailsById("12345"));

    }

    @Test
    void deleteWalletDetailsById() {
        Wallet wallet = new Wallet("12345",500);
        Mockito.when(walletRepository.findById("12345")).thenReturn(Optional.of(wallet));
        walletService.DeleteWalletDetailsById("12345");
        Mockito.verify(walletRepository,Mockito.times(1)).deleteById("12345");

    }
}