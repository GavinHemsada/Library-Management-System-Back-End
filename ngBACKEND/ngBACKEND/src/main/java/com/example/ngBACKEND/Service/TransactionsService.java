package com.example.ngBACKEND.Service;

import com.example.ngBACKEND.DTO.TransactionDTO;
import com.example.ngBACKEND.Entity.Transactions;
import com.example.ngBACKEND.Entity.User;
import com.example.ngBACKEND.Entity.Books;
import com.example.ngBACKEND.Repostry.BooksRepository;
import com.example.ngBACKEND.Repostry.TransactionsRepository;
import com.example.ngBACKEND.Repostry.UserRepository;
import com.example.ngBACKEND.Response.CRUDRespons;
import com.example.ngBACKEND.Response.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TransactionsService {
    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private BooksRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    public CRUDRespons createTransactions(TransactionDTO transactionDTO){
        Books books = bookRepository.findByid(transactionDTO.getBookId());
        User user = userRepository.findById(transactionDTO.getUserId());
        if(books != null & user != null){
            Transactions transactions = new Transactions(books,user,transactionDTO.getIssueDate(),transactionDTO.getDueDate(),transactionDTO.getReturnDate(),transactionDTO.getFine(),transactionDTO.getStatus());
            transactionsRepository.save(transactions);
            return  new CRUDRespons("Successfully added",true);
        }
        return new CRUDRespons("Empty inputs",false);
    }
    public Transactions readTransactions(int id){
        Transactions transactions = transactionsRepository.findById(id);
        if(transactions==null){
            throw  new NotFoundException("Cant find Transactions");
        }
        return transactions;
    }

    public List<Transactions> readAllTransactions(){
        return transactionsRepository.findAll();
    }
    public CRUDRespons editTransactions(TransactionDTO transactionDTO){
        Transactions transactions = transactionsRepository.findById(transactionDTO.getId());
        if(transactions != null){
            transactions.setIssueDate(transactionDTO.getIssueDate());
            transactions.setDueDate(transactionDTO.getDueDate());
            transactions.setReturnDate(transactionDTO.getReturnDate());
            transactions.setFine(transactionDTO.getFine());
            transactions.setStatus(transactionDTO.getStatus());
            transactionsRepository.save(transactions);
            return  new CRUDRespons("Successfully update",true);
        }
        return new CRUDRespons("Cant find ID",false);
    }
    public  CRUDRespons deleteTransactions(int id){
        Transactions transactions = transactionsRepository.findById(id);
        if(transactions != null){
            transactionsRepository.delete(transactions);
            return new CRUDRespons("successfully Delete",true);
        }
        return new CRUDRespons("Cant find id",false);
    }
}
