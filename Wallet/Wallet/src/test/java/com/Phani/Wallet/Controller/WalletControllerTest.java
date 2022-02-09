package com.Phani.Wallet.Controller;

import com.Phani.Wallet.Entity.User;
import com.Phani.Wallet.Entity.Wallet;
import com.Phani.Wallet.Repository.UserRepository;
import com.Phani.Wallet.Service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(WalletController.class)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WalletService walletService;

    @MockBean
    private UserRepository userRepository;

    @Test
     void createWalletTest() throws Exception{

        User user = new User(0L,"phani","12345","phani@gmail","false");

        Wallet wallet = new Wallet("12345",500.0);
        when(walletService.CheckWallet(wallet)).thenReturn(true);
        Mockito.when(walletService.CreateUserWallet(wallet)).thenReturn(wallet);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                       .content(this.objectMapper.writeValueAsString(wallet)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    public void getAllWalletsTest() throws  Exception {
        Wallet wallet = new Wallet("12345",500);

        List<Wallet> walletList = new ArrayList<>(Arrays.asList(wallet));

        Mockito.when(walletService.GeTAllWalletDetails()).thenReturn(walletList);

        mockMvc.perform(MockMvcRequestBuilders.get("/wallets"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].phoneNumber", Matchers.is(wallet.getPhoneNumber())));

    }

    @Test
    public  void getWalletByIdTest() throws Exception {
        Wallet wallet = new Wallet("12345",500);
        Mockito.when(walletService.GetWalletDetailsById(wallet.getPhoneNumber())).thenReturn(wallet);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/wallets/" + wallet.getPhoneNumber())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.balance", Matchers.is(500.0)));

    }

    @Test
    public void deleteWalletByIdTest() throws Exception{
        User user = new User(0L,"phani","12345","phani@gmail","false");
        Wallet wallet = new Wallet("12345",500);
        Mockito.when( userRepository.findByPhoneNumber("12345")).thenReturn(user) ;
        Mockito.when(walletService.DeleteWalletDetailsById(wallet.getPhoneNumber())).thenReturn(String.valueOf("Wallet deleted"));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/wallets/" + wallet.getPhoneNumber().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().string("Wallet deleted"));


    }

}
