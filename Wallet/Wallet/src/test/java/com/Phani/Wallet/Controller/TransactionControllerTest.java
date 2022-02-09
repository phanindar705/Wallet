package com.Phani.Wallet.Controller;

import com.Phani.Wallet.Entity.Transaction;
import com.Phani.Wallet.Entity.User;
import com.Phani.Wallet.Entity.Wallet;
import com.Phani.Wallet.Repository.UserRepository;
import com.Phani.Wallet.Service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void transferMoneyTest() throws Exception {


        Transaction transaction = new  Transaction(0L,"12345","54321",100L,"Successful");
        Mockito.when(transactionService.MoneyTransfer(transaction)).thenReturn(transaction);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void getAllTransactionsTest() throws Exception {
        Transaction transaction = new  Transaction(0L,"12345","54321",100L,"Successful");

        List<Transaction> transactionList=new ArrayList<>(Arrays.asList(transaction));

        Mockito.when(transactionService.AllTransactions()).thenReturn(transactionList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].status",Matchers.is("Successful")));


    }

    @Test
     public void getAllUserTransactionsTest() throws Exception {
        User user = new User(0L,"phani","12345","phani@gmail","true");
        Transaction transaction = new  Transaction(0L,"12345","54321",100L,"Successful");
        List<Transaction> transactionList=new ArrayList<>(Arrays.asList(transaction));

        Mockito.when(userRepository.findByuserName("phani")).thenReturn(user);
        Mockito.when(transactionService.GetUserTransactions(user.getPhoneNumber())).thenReturn(transactionList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transactions/" + user.getUserName()))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].transactionId", Matchers.is(0)));


    }




}