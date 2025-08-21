package dev.kerem.library_app.service;

import dev.kerem.library_app.dto.BookDto;
import dev.kerem.library_app.entity.Book;
import dev.kerem.library_app.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public BookDto addBook(BookDto bookDto) {
        Book book = new Book();
        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());
        book.setPublicationYear(bookDto.getPublicationYear());
        book.setStock(bookDto.getStock());
        Book savedBook = bookRepository.save(book);
        return new BookDto(savedBook.getId(), savedBook.getName(), savedBook.getAuthor(), savedBook.getPublicationYear(), savedBook.getStock());
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> new BookDto(book.getId(), book.getName(), book.getAuthor(), book.getPublicationYear(), book.getStock()))
                .collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            return new BookDto(book.getId(), book.getName(), book.getAuthor(), book.getPublicationYear(), book.getStock());
        }
        return null;
    }

    public BookDto updateBook(Long id, BookDto bookDto) {
        Book existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook != null) {
            existingBook.setName(bookDto.getName());
            existingBook.setAuthor(bookDto.getAuthor());
            existingBook.setPublicationYear(bookDto.getPublicationYear());
            existingBook.setStock(bookDto.getStock());
            Book updatedBook = bookRepository.save(existingBook);
            return new BookDto(updatedBook.getId(), updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getPublicationYear(), updatedBook.getStock());
        }
        return null;
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}