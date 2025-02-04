package com.example.ngBACKEND.Controller;

import com.example.ngBACKEND.DTO.BooksDTO;
import com.example.ngBACKEND.Entity.Books;
import com.example.ngBACKEND.Response.CRUDRespons;
import com.example.ngBACKEND.Service.BooksService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/ngCRUD/books")
public class BookController {
    @Autowired
    BooksService booksService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody BooksDTO booksDTO){
        CRUDRespons create = booksService.createBooks(booksDTO);
        return ResponseEntity.ok(create);
    }
    @GetMapping("/read")
    public Books read(@RequestParam int id){
        return booksService.readBook(id);
    }
    @GetMapping("/readAll")
    public List<Books> readAll(){
        return booksService.readAllBooks();
    }
    @PutMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody BooksDTO booksDTO){
        CRUDRespons edit = booksService.editBook(booksDTO);
        return ResponseEntity.ok(edit);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody JsonNode jsonNode){
        int id = jsonNode.get("id").asInt();
        CRUDRespons delete = booksService.deleteBook(id);
        return ResponseEntity.ok(delete);
    }
}
