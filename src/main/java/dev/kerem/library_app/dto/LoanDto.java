package dev.kerem.library_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDto {
    private Long id;
    private Long bookId;
    private Long customerId;
    private LocalDate loanDate;
    private LocalDate returnDate;
}