package com.example.ngBACKEND.DTO;


import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private Integer reservationId;

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotBlank(message = "User name is required")
    private String userName;

    @NotNull(message = "Book ID is required")
    private Integer bookId;

    @NotBlank(message = "Book title is required")
    private String bookTitle;

    @NotBlank(message = "Book author is required")
    private String bookAuthor;

    @NotNull(message = "Reservation date is required")
    private LocalDate reservationDate;

    @NotNull(message = "Expiry date is required")
    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;

    @NotBlank(message = "Status is required")
    private String status;
}