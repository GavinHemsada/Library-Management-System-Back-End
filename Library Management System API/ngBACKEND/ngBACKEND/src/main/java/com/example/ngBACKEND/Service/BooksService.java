package com.example.ngBACKEND.Service;

import com.example.ngBACKEND.DTO.BooksDTO;
import com.example.ngBACKEND.Entity.Books;
import com.example.ngBACKEND.Entity.Category;
import com.example.ngBACKEND.Repostry.BooksRepository;
import com.example.ngBACKEND.Repostry.CategoryRepository;
import com.example.ngBACKEND.Repostry.TransactionsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BooksService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Cacheable("allBooks")
    public List<BooksDTO> getAllBooks() {
        return booksRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<BooksDTO> getBookById(Integer id) {
        return booksRepository.findById(id).map(this::convertToDTO);
    }
    @Cacheable(value = "categoryBooks" , key = "#categoryId")
    public List<BooksDTO> getBooksByCategory(Integer categoryId) {
        return booksRepository.findByCategoryCategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BooksDTO> searchBooksByTitle(String title) {
        return booksRepository.findByTitleContaining(title).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BooksDTO> searchBooksByAuthor(String author) {
        return booksRepository.findByAuthorContaining(author).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<BooksDTO> getBookByIsbn(String isbn) {
        return booksRepository.findByIsbn(isbn).map(this::convertToDTO);
    }
    @Cacheable(value = "availableBooks")
    public List<BooksDTO> getAvailableBooks() {
        return booksRepository.findAvailableBooks().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BooksDTO> getOutOfStockBooks() {
        return booksRepository.findOutOfStockBooks().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BooksDTO> getBooksByPublicationYear(Integer startYear, Integer endYear) {
        return booksRepository.findByPublicationYearBetween(startYear, endYear).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BooksDTO> searchBooks(String keyword) {
        return booksRepository.searchBooks(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BooksDTO createBook(BooksDTO bookDTO) {
        if (booksRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new RuntimeException("Book with ISBN '" + bookDTO.getIsbn() + "' already exists");
        }

        Category category = categoryRepository.findById(bookDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + bookDTO.getCategoryId()));

        Books book = convertToEntity(bookDTO);
        book.setCategory(category);
        book.setIsActive(true);
        Books savedBook = booksRepository.save(book);
        return convertToDTO(savedBook);
    }

    @CacheEvict(value = {"allBooks", "categoryBooks" ,"availableBooks"}, allEntries = true)
    public BooksDTO updateBook(Integer id, BooksDTO bookDTO) {
        Books existingBook = booksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        if (!existingBook.getIsbn().equals(bookDTO.getIsbn()) &&
                booksRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new RuntimeException("Book with ISBN '" + bookDTO.getIsbn() + "' already exists");
        }

        Category category = categoryRepository.findById(bookDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + bookDTO.getCategoryId()));

        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setPublicationYear(bookDTO.getPublicationYear());
        existingBook.setPublisher(bookDTO.getPublisher());
        existingBook.setLanguage(bookDTO.getLanguage());
        existingBook.setTotalCopies(bookDTO.getTotalCopies());
        existingBook.setCopiesAvailable(bookDTO.getCopiesAvailable());
        existingBook.setCategory(category);
        existingBook.setIsActive(bookDTO.getIsActive());

        Books savedBook = booksRepository.save(existingBook);
        return convertToDTO(savedBook);
    }
    @CacheEvict(value = {"allBooks", "categoryBooks" ,"availableBooks"}, allEntries = true)
    public String deleteBook(Integer id) {
        Books book = booksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        // Check if book has active transactions
        Long activeTransactions = transactionsRepository.countActiveTransactionsByBook(id);
        if (activeTransactions > 0) {
            throw new RuntimeException("Cannot delete book with active transactions");
        }

        booksRepository.deleteById(id);
        return "Successful Delete";
    }
    @CacheEvict(value = {"allBooks", "categoryBooks" ,"availableBooks"}, allEntries = true)
    public String deactivateBook(Integer id) {
        Books book = booksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        book.setIsActive(false);
        booksRepository.save(book);
        return "Successful Deactivate";
    }
    @CacheEvict(value = {"allBooks", "categoryBooks" ,"availableBooks"}, allEntries = true)
    public String updateBookCopies(Integer id, Integer totalCopies, Integer availableCopies) {
        Books book = booksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        book.setTotalCopies(totalCopies);
        book.setCopiesAvailable(availableCopies);
        booksRepository.save(book);
        return "Successful Update Book copies";
    }

    private BooksDTO convertToDTO(Books book) {
        return new BooksDTO(
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublicationYear(),
                book.getPublisher(),
                book.getLanguage(),
                book.getTotalCopies(),
                book.getCopiesAvailable(),
                book.getCategory().getCategoryName(),
                book.getCategory().getCategoryId(),
                book.getIsActive()
        );
    }

    private Books convertToEntity(BooksDTO bookDTO) {
        Books book = new Books();
        book.setBookId(bookDTO.getBookId());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublicationYear(bookDTO.getPublicationYear());
        book.setPublisher(bookDTO.getPublisher());
        book.setLanguage(bookDTO.getLanguage());
        book.setTotalCopies(bookDTO.getTotalCopies());
        book.setCopiesAvailable(bookDTO.getCopiesAvailable());
        book.setIsActive(bookDTO.getIsActive());
        return book;
    }
}