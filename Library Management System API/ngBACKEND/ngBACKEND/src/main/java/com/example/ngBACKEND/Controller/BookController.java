package com.example.ngBACKEND.Controller;

import com.example.ngBACKEND.DTO.BooksDTO;
import com.example.ngBACKEND.Respons.Respons;
import com.example.ngBACKEND.Service.BooksService;
import com.example.ngBACKEND.Util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/books")

public class BookController {

    @Autowired
    private BooksService booksService;

    @GetMapping
    public Respons<?> getAllBooks() {
        try{
            return  new Respons<>(true,"All users",booksService.getAllBooks());
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Respons<?> getBookById(@PathVariable Integer id) {
        try{
            return  new Respons<>(true,"get Book by ID",booksService.getBookById(id));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/category/{categoryId}")
    public Respons<?> getBooksByCategory(@PathVariable Integer categoryId) {
        try{
            return  new Respons<>(true,"getBooksByCategory",booksService.getBooksByCategory(categoryId));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/title")
    public Respons<?> searchBooksByTitle(@RequestParam String title) {
        try{
            return  new Respons<>(true,"searchBooksByTitle",booksService.searchBooksByTitle(title));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/author")
    public Respons<?> searchBooksByAuthor(@RequestParam String author) {
        try{
            return  new Respons<>(true,"searchBooksByAuthor",booksService.searchBooksByAuthor(author));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/isbn")
    public Respons<?> getBookByIsbn(@RequestParam String isbn) {
        try{
            return  new Respons<>(true,"getBookByIsbn",booksService.getBookByIsbn(isbn));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/available")
    public Respons<?> getAvailableBooks() {
        try{
            return  new Respons<>(true,"getAvailableBooks",booksService.getAvailableBooks());
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/out-of-stock")
    public Respons<?> getOutOfStockBooks() {
        try{
            return  new Respons<>(true,"getOutOfStockBooks",booksService.getOutOfStockBooks());
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/year")
    public Respons<?> getBooksByPublicationYear(@RequestParam Integer start,
                                                                    @RequestParam Integer end) {
        try{
            return  new Respons<>(true,"getBooksByPublicationYear",booksService.getBooksByPublicationYear(start, end));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/search")
    public Respons<?> searchBooks(@RequestParam String keyword) {
        try{
            return  new Respons<>(true,"searchBooks",booksService.searchBooks(keyword));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @PostMapping
    public Respons<?> createBook(@RequestBody BooksDTO bookDTO) {
        try{
            Map<String, String> errors = ValidationUtil.validateObject(bookDTO);
            if (!errors.isEmpty()) {
                return new Respons<>( false, "Input Validation failed", errors);
            }
            return  new Respons<>(true,"createBook",booksService.createBook(bookDTO));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Respons<?> updateBook(@PathVariable Integer id, @RequestBody BooksDTO bookDTO) {
        try{
            Map<String, String> errors = ValidationUtil.validateObject(bookDTO);
            if (!errors.isEmpty()) {
                return new Respons<>( false, "Input Validation failed", errors);
            }
            return  new Respons<>(true,"",booksService.updateBook(id, bookDTO));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Respons<?> deleteBook(@PathVariable Integer id) {
        try{
            return  new Respons<>(true,"",booksService.deleteBook(id));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @PutMapping("/{id}/deactivate")
    public Respons<?> deactivateBook(@PathVariable Integer id) {
        try{
            return  new Respons<>(true,"",booksService.deactivateBook(id));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @PutMapping("/{id}/copies")
    public Respons<?> updateBookCopies(@PathVariable Integer id,
                                                 @RequestParam Integer totalCopies,
                                                 @RequestParam Integer availableCopies) {
        try{
            return  new Respons<>(true,"",booksService.updateBookCopies(id, totalCopies, availableCopies));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }
}
