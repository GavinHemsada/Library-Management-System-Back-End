package com.example.ngBACKEND.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FineDTO {
    private Integer fineId;
    @NotNull(message = "Fine amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Fine amount must be greater than zero")
    private BigDecimal fineAmount;

    @NotNull(message = "Fine date is required")
    private LocalDate fineDate;

    @NotBlank(message = "Payment status is required")
    private String paymentStatus;

    private String reason;

    private LocalDate paymentDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Transaction ID is required")
    private Integer transactionId;

}