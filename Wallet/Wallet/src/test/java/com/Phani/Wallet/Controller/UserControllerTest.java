package com.Phani.Wallet.Controller;

import com.Phani.Wallet.Entity.User;
import com.Phani.Wallet.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.LifecycleState;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;






@WebMvcTest(UserController.class)
public class UserControllerTest {

   @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;



    @Test
    public void createUserTest() throws Exception {
        User user = new User(0L,"phan","1122","phan@gmail","false");

       when(userService.CheckUser(user)).thenReturn(true);
        when(userService.AddUserDetails(user)).thenReturn(user);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
                       // .andExpect(status().isConflict()).andExpect(content().string("user already exists"));
         //  System.out.println(this.objectMapper.writeValueAsString(newuser));


    }



    @Test
    void getAllUsersTest() throws Exception {
        User user = new User(0L,"phani","11223","phani@gmail","false");

        List<User> allUsers= Stream.of(user).collect(Collectors.toList());

        when(userService.GetAllUserDetails()).thenReturn(allUsers);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$",Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].userName",Matchers.is(user.getUserName())));
    }

    @Test
    public void getUserByIdTest() throws Exception {
        User user = new User(0L,"phani","11223","phani@gmail","false");
        when(userService.GetUserDetailsById(user.getUserId())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/"+user.getUserId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$",Matchers.notNullValue()))
                .andExpect(jsonPath("$.userName",Matchers.is(user.getUserName())));


    }

    @Test
    public void deleteUserByIdTest() throws Exception{
        User user = new User(0L,"phani","11223","phani@gmail","false");

        when(userService.DeleteUserDetailsById(user.getUserId())).thenReturn(String.valueOf("User Deleted"));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/"+user.getUserId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().string("User Deleted"));


    }
}