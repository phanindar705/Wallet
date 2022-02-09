package com.Phani.Wallet.Repository;


import com.Phani.Wallet.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
