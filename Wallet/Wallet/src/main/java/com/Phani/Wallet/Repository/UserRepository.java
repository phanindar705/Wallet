package com.Phani.Wallet.Repository;

import com.Phani.Wallet.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;




public interface UserRepository extends JpaRepository<User,Long>{
    public  User findByPhoneNumber(String phoneNumber);
    public User findByuserName(String UserName);

}
