package com.Phani.Wallet.Controller;

import com.Phani.Wallet.Entity.Transaction;
import com.Phani.Wallet.Entity.User;
import com.Phani.Wallet.Repository.UserRepository;
import com.Phani.Wallet.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @Autowired
    private UserRepository userRepository;



   // Build API to Transfer Money from Sender to Receiver wallets
    @PostMapping
    public ResponseEntity<?> TransferMoney(@RequestBody Transaction transaction){
       return new ResponseEntity<Transaction>(transactionService.MoneyTransfer(transaction),HttpStatus.CREATED);
   }



  // Build API to Get AllTransactions
   @GetMapping
   public ResponseEntity<?> GetAllTransactions(){
       List<Transaction>TransactionsList = transactionService.AllTransactions();
       if(TransactionsList.isEmpty()){
           return new ResponseEntity<String>("No Transactions", HttpStatus.CONFLICT);
       }
       else{
           return new ResponseEntity<List<Transaction>>(TransactionsList,HttpStatus.ACCEPTED);
       }
   }



   // Getting User AllTransactions by UserName
   @GetMapping("/{userName}")
    public ResponseEntity<?> GetAllUserTransactions(@PathVariable String userName){
       User user = userRepository.findByuserName(userName);
       if(user != null){
           String UserphoneNumber = user.getPhoneNumber();
           List<Transaction>AllUsertransactions = transactionService.GetUserTransactions(UserphoneNumber);
           if(AllUsertransactions.isEmpty()){
               return new ResponseEntity<String>("No Transactions",HttpStatus.ACCEPTED);
           }
           else{
               return new ResponseEntity<List<Transaction>>(AllUsertransactions,HttpStatus.ACCEPTED);
           }
       }
       else {
           return new ResponseEntity<String>("User Not Found",HttpStatus.CONFLICT);
       }
   }


   

   // Pagination
   @GetMapping("/{pagination}/{offset}/{pagesize}/{field}")
   public Page<Transaction>GetTransactionPagination(@PathVariable int offset,@PathVariable int pagesize,@PathVariable String field){
        return transactionService.transactionPagination(offset,pagesize,field);
  }
}
