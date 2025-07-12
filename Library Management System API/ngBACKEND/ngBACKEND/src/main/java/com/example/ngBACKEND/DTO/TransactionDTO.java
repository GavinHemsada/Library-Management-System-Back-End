package com.example.ngBACKEND.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Integer transactionId;
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
    @NotNull(message = "Issue date is required")
    private LocalDate issueDate;

    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date must be today or in the future")
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status;
    private String notes;
    @DecimalMin(value = "0.0", inclusive = true, message = "Fine amount cannot be negative")
    private BigDecimal fineAmount;
    private String fineStatus;
}