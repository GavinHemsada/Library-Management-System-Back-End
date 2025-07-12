package com.example.ngBACKEND.Controller;

import com.example.ngBACKEND.DTO.UserDTO;
import com.example.ngBACKEND.Entity.User;
import com.example.ngBACKEND.Respons.Respons;
import com.example.ngBACKEND.Service.UserService;
import com.example.ngBACKEND.Util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class userController {
    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping
    public Respons<?> getAllUsers() {
        try{
            return  new Respons<>(true,"all users",userService.getAllUsers());
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    // Get user by ID
    @GetMapping("/{id}")
    public Respons<?> getUserById(@PathVariable Integer id) {
        try{
            return  new Respons<>(true,"User",userService.getUserById(id));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    // Get user by email
    @GetMapping("/email/{email}")
    public Respons<?> getUserByEmail(@PathVariable String email) {
        try{
            return  new Respons<>(true,"Email owner",userService.getUserByEmail(email));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    // Get users by type
    @GetMapping("/type/{userType}")
    public Respons<?> getUsersByType(@PathVariable User.UserType userType) {
        try{
            return  new Respons<>(true,"User Type",userService.getUsersByType(userType));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    // Get active users
    @GetMapping("/active")
    public Respons<?> getActiveUsers() {
        try{
            return  new Respons<>(true,"Active users",userService.getActiveUsers());
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    // Search users by name
    @GetMapping("/search")
    public Respons<?> searchUsersByName(@RequestParam String name) {
        try{
            return  new Respons<>(true,"Search user",userService.searchUsersByName(name));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    // Get active users by type
    @GetMapping("/active/type/{userType}")
    public Respons<?> getActiveUsersByType(@PathVariable User.UserType userType) {
        try{
            return  new Respons<>(true,"Active user by Type",userService.getActiveUsersByType(userType));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    // Get users with overdue books
    @GetMapping("/overdue")
    public Respons<?> getUsersWithOverdueBooks() {
        try{
            return  new Respons<>(true,"All users Overdue Users",userService.getUsersWithOverdueBooks());
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    // Update user
    @PutMapping("/{id}")
    public Respons<?> updateUser(@PathVariable Integer id,@RequestBody UserDTO userDTO) {
        try{
            Map<String, String> errors = ValidationUtil.validateObject(userDTO);
            if (!errors.isEmpty()) {
                return new Respons<>( false, "Input Validation failed", errors);
            }
            return  new Respons<>(true,"Update successful",userService.updateUser(id, userDTO));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    // Delete user
    @DeleteMapping("/{id}")
    public Respons<?> deleteUser(@PathVariable Integer id) {
        try{
            return  new Respons<>(true,"User deleted successfully",userService.deleteUser(id));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    // Deactivate user
    @PutMapping("/{id}/deactivate")
    public Respons<?> deactivateUser(@PathVariable Integer id) {
        try{
            return  new Respons<>(true,"User deactivated successfully",userService.deactivateUser(id));
        }catch (RuntimeException e) {
            return new Respons<>(false, "runtime error", e.getMessage());
        }
    }
}
