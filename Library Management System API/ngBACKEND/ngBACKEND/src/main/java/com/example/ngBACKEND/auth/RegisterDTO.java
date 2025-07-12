package com.example.ngBACKEND.auth;
import com.example.ngBACKEND.Entity.User;

import jakarta.validation.constraints.*;
import lombok.*;
@Data
@NoArgsConstructor
public class RegisterDTO {
       @Email(message = "Email should be valid")
       @NotBlank(message = "Email is mandatory")
       private String email;
       @NotBlank(message = "Password is mandatory")
       @Size(min = 6, message = "Password must be at least 6 characters")
       private String password;
       @NotBlank(message = "Name is mandatory")
       private String name;
       @NotBlank(message = "Phone number is mandatory")
       @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
       private String phone;
       @NotBlank(message = "Address is mandatory")
       private String address;
       @NotNull(message = "User type is mandatory or invalid")
       private User.UserType userType;
}
