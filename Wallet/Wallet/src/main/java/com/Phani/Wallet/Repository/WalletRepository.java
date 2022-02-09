package com.Phani.Wallet.Repository;

import com.Phani.Wallet.Entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WalletRepository extends JpaRepository<Wallet,String> {
}
