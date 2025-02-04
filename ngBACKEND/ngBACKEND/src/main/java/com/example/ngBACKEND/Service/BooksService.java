package com.example.ngBACKEND.Service;

import com.example.ngBACKEND.DTO.BooksDTO;
import com.example.ngBACKEND.Entity.Books;
import com.example.ngBACKEND.Repostry.BooksRepository;
import com.example.ngBACKEND.Response.CRUDRespons;
import com.example.ngBACKEND.Response.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService {
    @Autowired
    private BooksRepository booksRepository;

    public CRUDRespons createBooks(BooksDTO booksDTO){
        Books books = new Books(booksDTO.getTITlE(),booksDTO.getAUTHOR(),booksDTO.getPRICE(), booksDTO.getCATEGORY(), booksDTO.getCOPIES());
        booksRepository.save(books);
        return new CRUDRespons("successfully added",true);
    }
    public Books readBook(int id){
        Books books = booksRepository.findByid(id);
        if(books == null){
            throw  new NotFoundException("cant find id");
        }
        return books;
    }
    public List<Books> readAllBooks(){
        return booksRepository.findAll();
    }
    public CRUDRespons editBook(BooksDTO booksDTO){
        Books books = booksRepository.findByid(booksDTO.getID());
        if(books != null){
            books.setAuther(booksDTO.getAUTHOR());
            books.setPrice(booksDTO.getPRICE());
            books.setCategory(booksDTO.getCATEGORY());
            books.setCopies(booksDTO.getCOPIES());
            booksRepository.save(books);
            return new CRUDRespons("successfully updated",true);
        }
        return new CRUDRespons("cant find id",false);
    }
    public CRUDRespons deleteBook(int id){
        Books books = booksRepository.findByid(id);
        if(books != null){
            booksRepository.delete(books);
            return new CRUDRespons("successfully deleted",true);
        }
        return new CRUDRespons("cant find id",false);
    }
}
