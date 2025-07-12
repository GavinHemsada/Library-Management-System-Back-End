package com.example.ngBACKEND.auth;

import com.example.ngBACKEND.DTO.UserDTO;
import com.example.ngBACKEND.Entity.User;
import com.example.ngBACKEND.Repostry.UserRepository;
import com.example.ngBACKEND.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public String login(String email,String password){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            User founduser = user.get();
            if(passwordEncoder.matches(password, founduser.getPassword())){
                UserDTO userDTO = new UserDTO();
                userDTO.setEmail(founduser.getEmail());
                userDTO.setUserType(founduser.getUserType().toString());
                return jwtUtil.createToken(userDTO);
            }
        }
        throw new RuntimeException("Invalid email or password");
    }

    public String createUser(RegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new RuntimeException("User with email '" + registerDTO.getEmail() + "' already exists");
        }

        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setName(registerDTO.getName());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setPhone(registerDTO.getPhone());
        user.setAddress(registerDTO.getAddress());
        user.setRegistrationDate(LocalDate.now());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUserType(registerDTO.getUserType());

        userRepository.save(user);
        return "register Successfully!";
    }
}
