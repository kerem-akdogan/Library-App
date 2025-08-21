package dev.kerem.library_app.controller;

import dev.kerem.library_app.dto.LoanDto;
import dev.kerem.library_app.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/borrow")
    public ResponseEntity<LoanDto> borrowBook(@RequestBody LoanDto loanDto) {
        LoanDto newLoan = loanService.borrowBook(loanDto);
        if (newLoan != null) {
            return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/return/{loanId}")
    public ResponseEntity<LoanDto> returnBook(@PathVariable Long loanId) {
        LoanDto returnedLoan = loanService.returnBook(loanId);
        if (returnedLoan != null) {
            return new ResponseEntity<>(returnedLoan, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<LoanDto>> getAllLoans() {
        List<LoanDto> loans = loanService.getAllLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDto> getLoanById(@PathVariable Long id) {
        LoanDto loan = loanService.getLoanById(id);
        if (loan != null) {
            return new ResponseEntity<>(loan, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}