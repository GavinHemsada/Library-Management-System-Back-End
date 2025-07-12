package com.example.ngBACKEND.auth;

import com.example.ngBACKEND.Respons.Respons;
import com.example.ngBACKEND.Util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Login
    @PostMapping("/login")
    public Respons<?> login(@RequestBody LoginDTO loginDTO) {
        try{
            Map<String, String> errors = ValidationUtil.validateObject(loginDTO);
            if (!errors.isEmpty()) {
                return new Respons<>( false, "Input Validation failed", errors);
            }
            return  new Respons<>(true,"Login successful",authService.login(loginDTO.getEmail(), loginDTO.getPassword()));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    // Create new user
    @PostMapping("/register")
    public Respons<?> createUser(@RequestBody RegisterDTO registerDTO) {
        try{
            Map<String, String> errors = ValidationUtil.validateObject(registerDTO);
            if (!errors.isEmpty()) {
                return new Respons<>( false, "Input Validation failed", errors);
            }
            return  new Respons<>(true,authService.createUser(registerDTO),"");
        }catch (RuntimeException e){
            return  new Respons<>(false, e.getMessage(), null);
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "UserAllReservations", key = "#userId"),
            @CacheEvict(value = "userAllFines", key = "#userId"),
            @CacheEvict(value = "userAllTransactions", key = "#userId")
    })
    @GetMapping("/logout/{userId}")
    public Respons<?> logout(@PathVariable Integer userId) {
        return  new Respons<>(true,"Logout successful","user id:"+userId);
    }


}
