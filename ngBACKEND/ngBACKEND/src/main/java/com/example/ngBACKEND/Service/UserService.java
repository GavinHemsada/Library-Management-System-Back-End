package com.example.ngBACKEND.Service;

import com.example.ngBACKEND.DTO.UserDTO;
import com.example.ngBACKEND.Entity.User;
import com.example.ngBACKEND.Repostry.UserRepository;
import com.example.ngBACKEND.Response.CRUDRespons;
import com.example.ngBACKEND.Response.NotFoundException;
import com.example.ngBACKEND.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    EmailService emailService;
    @Autowired
    PasswordEncoder passwordEncoder;
    User user;
    public CRUDRespons register(UserDTO userDTO){
        User user = userRepository.findByEmail(userDTO.getEMAIL());
        if(user == null){
            try {
                String encodedPassword = passwordEncoder.encode(userDTO.getPASSWORD());
                User user1 = new User(userDTO.getEMAIL(),encodedPassword);
                userRepository.save(user1);
                String token = jwtUtil.createToken(userDTO);
                emailService.sendEmail(userDTO.getEMAIL(),"Verify your email",
                        "<html>"
                                + "<body>"
                                + "<p>To confirm your account, please click the button below:</p>"
                                + "<table border='0' cellpadding='0' cellspacing='0' width='100%'>"
                                + "<tr>"
                                + "<td align='center'>"
                                + "<a href=\"http://localhost:8090/api/ngCRUD/user/tokenCheck?token=" + token + "\" "
                                + "style=\"display: inline-block; padding: 10px 20px; font-size: 16px; color: white; background-color: #28a745; text-align: center; text-decoration: none; border-radius: 5px;\">"
                                + "Verify Email</a>"
                                + "</td>"
                                + "</tr>"
                                + "</table>"
                                + "</body>"
                                + "</html>");
                return new CRUDRespons("register success", true);
            }catch (Exception e){
                return new CRUDRespons(e.getMessage(),false);
            }
        }else {
            return new CRUDRespons("Email already used",false);
        }
    }

    public CRUDRespons login(UserDTO userDTO){
        User userEmail = userRepository.findByEmail(userDTO.getEMAIL());
        if(userEmail != null){
            if(passwordEncoder.matches(userDTO.getPASSWORD(),user.getPassword())){
                return new CRUDRespons("login success",true);
            }else {
                return new CRUDRespons("invalid password",false);
            }

        }else {
            return new CRUDRespons("Invalid Email",false);
        }
    }

    public CRUDRespons edit(UserDTO userDTO){
        User userEmail = userRepository.findByEmail(userDTO.getEMAIL());
        if(userEmail != null){
            String encodedPassword = passwordEncoder.encode(userDTO.getPASSWORD());
            userEmail.setPassword(encodedPassword);
            userRepository.save(userEmail);
            return new CRUDRespons("User updated successfully", true);
        }else {
            return new CRUDRespons("Cant find email", false);
        }
    }
    public CRUDRespons delete(String userEmail){
        User user = userRepository.findByEmail(userEmail);
        if(user != null){
            userRepository.delete(user);
            return new CRUDRespons("User delete successfully", true);
        }else {
            System.err.println("Delete failed: No user found with email " + userEmail);
            return new CRUDRespons("Cant find email", false);
        }
    }
    public User read(String userEmail){
        User user = userRepository.findByEmail(userEmail);
        if(user == null){
            throw new NotFoundException("cant find user");
        }
        return user;
    }
    public List<User> readAll(){
        return userRepository.findAll();
    }
}

