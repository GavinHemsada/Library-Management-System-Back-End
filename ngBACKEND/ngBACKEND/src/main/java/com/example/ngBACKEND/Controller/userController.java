package com.example.ngBACKEND.Controller;

import com.example.ngBACKEND.DTO.UserDTO;
import com.example.ngBACKEND.Entity.User;
import com.example.ngBACKEND.Response.CRUDRespons;
import com.example.ngBACKEND.Security.JwtUtil;
import com.example.ngBACKEND.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/ngCRUD/user")
public class userController {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtil jwtUtil;
    ObjectMapper objectMapper = new ObjectMapper();
    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@RequestBody UserDTO userDTO){
        CRUDRespons registerRespons = userService.register(userDTO);
        return ResponseEntity.ok(registerRespons);
    }
    @GetMapping("/tokenCheck")
    public ResponseEntity<?> tokenChecker(@RequestParam String token , HttpServletResponse response) throws IOException {
        boolean valid = jwtUtil.validateToken(token);
        if (valid) {
            response.sendRedirect("http://localhost:4200/home"); // change this
            return ResponseEntity.ok("valid token");
        }else {
            boolean tokenExpired = jwtUtil.isTokenExpired(token);
            if(tokenExpired){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token Expired");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
        }
    }
    @GetMapping("/login")
    public ResponseEntity<?> userLogin(@RequestParam String email , @RequestParam String password){
        UserDTO user = new UserDTO();
        user.setEMAIL(email);
        user.setPASSWORD(password);
        CRUDRespons loginRespons = userService.login(user);
        return ResponseEntity.ok(loginRespons);
    }
    @PutMapping("/edit")
    public ResponseEntity<?> userEdit(@RequestBody UserDTO userDTO){
        CRUDRespons loginRespons = userService.edit(userDTO);
        return ResponseEntity.ok(loginRespons);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> userDelete(@RequestBody String email) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(email);
        String userEmail = jsonNode.get("email").asText();
        CRUDRespons response = userService.delete(userEmail);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/read")
    public User userRead(@RequestBody String email) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(email);
        String userEmail = jsonNode.get("email").asText();
        return userService.read(userEmail);
    }
    @PostMapping("/allread")
    public List<User> readALL(){
        return userService.readAll();
    }

}
