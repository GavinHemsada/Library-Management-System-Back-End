package com.example.ngbackend.Service;

import com.example.ngbackend.DTO.UserDTO;
import com.example.ngbackend.Entity.User;
import com.example.ngbackend.Repository.UserRepository;
import com.example.ngbackend.Respons.LoginRespons;
import com.example.ngbackend.Respons.RegisterRespons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private UserDTO userDTO;
    public RegisterRespons register(UserDTO userDTO){
        User user = userRepository.findByEmail(userDTO.getEMAIL());
        if(user == null){
            User user1 = new User(userDTO.getID(),userDTO.getEMAIL(),userDTO.getPASSWORD());
            userRepository.save(user1);
            return new RegisterRespons("register success",true);
        }else {
            return new RegisterRespons("Email already used",false);
        }
    }

    public LoginRespons login(UserDTO userDTO){
        User userEmail = userRepository.findByEmail(userDTO.getEMAIL());
        if(userEmail != null){
            User userPassword = userRepository.findOneByEmailAndPassword(userDTO.getEMAIL(),userDTO.getPASSWORD());
            if(userPassword != null){
                return new LoginRespons("login success",true);
            }else {
                return new LoginRespons("invalid password",false);
            }

        }else {
            return new LoginRespons("Invalid Email",false);
        }
    }
}
