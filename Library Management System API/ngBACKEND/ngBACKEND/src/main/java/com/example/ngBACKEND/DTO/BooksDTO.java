package com.example.ngBACKEND.DTO;

import jakarta.validation.constraints.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksDTO {
    private Integer bookId;
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be at most 200 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 100, message = "Author name must be at most 100 characters")
    private String author;

    @NotBlank(message = "ISBN is required")
    @Pattern(
            regexp = "^(97(8|9))?\\d{9}(\\d|X)$",
            message = "ISBN must be valid 10 or 13 digit format"
    )
    private String isbn;

    @NotNull(message = "Publication year is required")
    @Min(value = 1000, message = "Publication year must be valid")
    @Max(value = 2100, message = "Publication year must be valid")
    private Integer publicationYear;

    @NotBlank(message = "Publisher is required")
    @Size(max = 100, message = "Publisher must be at most 100 characters")
    private String publisher;

    @NotBlank(message = "Language is required")
    @Size(max = 50, message = "Language must be at most 50 characters")
    private String language;

    @NotNull(message = "Total copies is required")
    @Min(value = 0, message = "Total copies cannot be negative")
    private Integer totalCopies;

    @NotNull(message = "Copies available is required")
    @Min(value = 0, message = "Copies available cannot be negative")
    private Integer copiesAvailable;

    @NotBlank(message = "Category name is required")
    private String categoryName;

    @NotNull(message = "Category ID is required")
    private Integer categoryId;

    @NotNull(message = "Active status is required")
    private Boolean isActive;
}