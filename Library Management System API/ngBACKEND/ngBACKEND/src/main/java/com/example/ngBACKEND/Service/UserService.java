package com.example.ngBACKEND.Service;

import com.example.ngBACKEND.DTO.UserDTO;
import com.example.ngBACKEND.Entity.User;
import com.example.ngBACKEND.Repostry.FineRepository;
import com.example.ngBACKEND.Repostry.TransactionsRepository;
import com.example.ngBACKEND.Repostry.UserRepository;
import org.springframework.cache.annotation.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private FineRepository fineRepository;

    @Cacheable("allUsers")
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public Optional<UserDTO> getUserById(Integer id) {
        return userRepository.findById(id).map(this::convertToDTO);
    }

    @Cacheable(value = "userEmail",key = "#email")
    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(this::convertToDTO);
    }

    public List<UserDTO> getUsersByType(User.UserType userType) {
        return userRepository.findByUserType(userType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getActiveUsers() {
        return userRepository.findByIsActive(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> searchUsersByName(String name) {
        return userRepository.findByNameContaining(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getActiveUsersByType(User.UserType userType) {
        return userRepository.findActiveUsersByType(userType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getUsersWithOverdueBooks() {
        return userRepository.findUsersWithOverdueBooks().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @CacheEvict(value = {"allUsers", "userEmail"}, allEntries = true)
    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (!existingUser.getEmail().equals(userDTO.getEmail()) &&
                userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("User with email '" + userDTO.getEmail() + "' already exists");
        }

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setAddress(userDTO.getAddress());
        existingUser.setUserType(User.UserType.valueOf(userDTO.getUserType()));
        existingUser.setIsActive(userDTO.getIsActive());

        User savedUser = userRepository.save(existingUser);
        return convertToDTO(savedUser);
    }

    @CacheEvict(value = {"allUsers", "userEmail"}, allEntries = true)
    public String deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Check if user has pending transactions
        Long pendingTransactions = transactionsRepository.countUnreturnedBooksByUser(id);
        if (pendingTransactions > 0) {
            throw new RuntimeException("Cannot delete user with pending book transactions");
        }

        // Check if user has pending fines
        BigDecimal pendingFines = fineRepository.getTotalPendingFinesByUser(id).orElse(BigDecimal.ZERO);
        if (pendingFines.compareTo(BigDecimal.ZERO) > 0) {
            throw new RuntimeException("Cannot delete user with pending fines");
        }

        userRepository.deleteById(id);
        return "Delete successful";
    }

    @CacheEvict(value = {"allUsers", "userEmail"}, allEntries = true)
    public String deactivateUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setIsActive(false);
        userRepository.save(user);
        return "Deactivate successful";
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getAddress(),
                user.getUserType().toString(),
                user.getRegistrationDate(),
                user.getIsActive()
        );
    }
}