package dev.kerem.library_app.service;


import dev.kerem.library_app.dto.LoanDto;
import dev.kerem.library_app.entity.Book;
import dev.kerem.library_app.entity.Customer;
import dev.kerem.library_app.entity.Loan;
import dev.kerem.library_app.repository.BookRepository;
import dev.kerem.library_app.repository.CustomerRepository;
import dev.kerem.library_app.repository.LoanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public LoanDto borrowBook(LoanDto loanDto) {
        Book book = bookRepository.findById(loanDto.getBookId()).orElse(null);
        Customer customer = customerRepository.findById(loanDto.getCustomerId()).orElse(null);

        // İş Kuralı: Kitap mevcut mu ve stokta yeterli adet var mı?
        if (book == null || customer == null || book.getStock() <= 0) {
            return null; // İşlem başarısız
        }

        book.setStock(book.getStock() - 1);
        bookRepository.save(book);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setCustomer(customer);
        loan.setLoanDate(LocalDate.now());

        Loan savedLoan = loanRepository.save(loan);
        return new LoanDto(savedLoan.getId(), savedLoan.getBook().getId(), savedLoan.getCustomer().getId(), savedLoan.getLoanDate(), savedLoan.getReturnDate());
    }

    @Transactional
    public LoanDto returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElse(null);

        if (loan == null || loan.getReturnDate() != null) {
            return null; // İşlem başarısız
        }

        loan.setReturnDate(LocalDate.now());
        Loan returnedLoan = loanRepository.save(loan);

        // Stok güncelleme
        Book book = returnedLoan.getBook();
        book.setStock(book.getStock() + 1);
        bookRepository.save(book);

        return new LoanDto(returnedLoan.getId(), returnedLoan.getBook().getId(), returnedLoan.getCustomer().getId(), returnedLoan.getLoanDate(), returnedLoan.getReturnDate());
    }

    public List<LoanDto> getAllLoans() {
        return loanRepository.findAll().stream()
                .map(loan -> new LoanDto(loan.getId(), loan.getBook().getId(), loan.getCustomer().getId(), loan.getLoanDate(), loan.getReturnDate()))
                .collect(Collectors.toList());
    }

    public LoanDto getLoanById(Long id) {
        Loan loan = loanRepository.findById(id).orElse(null);
        if (loan != null) {
            return new LoanDto(loan.getId(), loan.getBook().getId(), loan.getCustomer().getId(), loan.getLoanDate(), loan.getReturnDate());
        }
        return null;
    }
}