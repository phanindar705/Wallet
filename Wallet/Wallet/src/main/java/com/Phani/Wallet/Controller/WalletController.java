package com.Phani.Wallet.Controller;


import com.Phani.Wallet.Entity.User;
import com.Phani.Wallet.Entity.Wallet;
import com.Phani.Wallet.Repository.UserRepository;
import com.Phani.Wallet.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserRepository userRepository;

    // Build API to CreateWallet
    @PostMapping
    public ResponseEntity<?> CreateWallet(@RequestBody Wallet wallet){
     User user = userRepository.findByPhoneNumber(wallet.getPhoneNumber());
      if(user != null){
          Wallet newwallet = walletService.CreateUserWallet(wallet);
          user.setActive("true");
          userRepository.save(user);
          return  new ResponseEntity<Wallet>(newwallet, HttpStatus.ACCEPTED);
      }
      else{
          return  new ResponseEntity<String>("User Not Found",HttpStatus.CONFLICT);
      }

    }

    // Build API to GetAllWallets
   @GetMapping
   public ResponseEntity<?>  GetAllWallets(){
       List<Wallet>AllWallets = walletService.GeTAllWalletDetails();
       if(AllWallets.isEmpty()){
           return new ResponseEntity<String >("No Wallets",HttpStatus.CONFLICT);
       }
       else{
           return new ResponseEntity<List<Wallet>>(AllWallets,HttpStatus.ACCEPTED);
       }
   }

   // Build API to GetWalletById
   @GetMapping("/{phoneNumber}")
   public ResponseEntity<?> GetWalletById(@PathVariable String phoneNumber){
        Wallet wallet = walletService.GetWalletDetailsById(phoneNumber);
        if(walletService.CheckWallet(wallet)){
            return new ResponseEntity<String>("No Wallet Exists with that number",HttpStatus.CONFLICT);
        }
        else {

            return new ResponseEntity<Wallet>(wallet,HttpStatus.ACCEPTED);
        }
  }

  // Build API to DeleteWalletById
  @DeleteMapping("/{phoneNumber}")
  public ResponseEntity<?> DeleteWalletById(@PathVariable String phoneNumber){
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if(user != null){
            user.setActive("false");
            userRepository.save(user);
            return new ResponseEntity<String>(walletService.DeleteWalletDetailsById(phoneNumber),HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<String >("No User Found With This Number",HttpStatus.CONFLICT);
        }
  }



}
