package com.Phani.Wallet.Service;

import com.Phani.Wallet.Entity.User;
import com.Phani.Wallet.Repository.UserRepository;
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
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void addUserDetailsTest() {
        User user = new User(0L,"phani","11223","phani@gmail","false");
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Assertions.assertEquals(user, userService.AddUserDetails(user));
    }

    @Test
    void getAllUserDetailsTest() {

        Mockito.when(userRepository.findAll()).thenReturn(Stream.of(new User(0L,"phani","11223","phani@gmail","false")).collect(Collectors.toList()));
        Assertions.assertEquals(1,userService.GetAllUserDetails().size());
    }

    @Test
    void getUserDetailsByIdTest() {
        User user = new User(0L,"phani","11223","phani@gmail","false");
        Mockito.when(userRepository.getById(0L)).thenReturn(user);
        Assertions.assertEquals(user,userService.GetUserDetailsById(0L));
    }

    @Test
    void deleteUserDetailsByIdTest() {
        User user = new User(0L,"phani","11223","phani@gmail","false");
        Mockito.when(userRepository.findById(0L)).thenReturn(Optional.of(user));
        userService.DeleteUserDetailsById(0L);
        Mockito.verify(userRepository,Mockito.times(1)).deleteById(0L);
    }
}