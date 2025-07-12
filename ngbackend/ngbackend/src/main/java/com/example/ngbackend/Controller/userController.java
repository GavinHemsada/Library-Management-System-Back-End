package com.example.ngbackend.Controller;

import com.example.ngbackend.DTO.UserDTO;
import com.example.ngbackend.Respons.LoginRespons;
import com.example.ngbackend.Respons.RegisterRespons;
import com.example.ngbackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/ngCRUD")
public class userController {
    @Autowired
    UserService userService;
    @PostMapping("/register")
   public ResponseEntity<?> userRegister(@RequestBody UserDTO user){
        RegisterRespons registerRespons = userService.register(user);
        return ResponseEntity.ok(registerRespons);
   }
   @GetMapping("/login")
   public ResponseEntity<?> userLogin(@RequestBody UserDTO user){
       LoginRespons loginRespons = userService.login(user);
       return ResponseEntity.ok(loginRespons);
   }
}
