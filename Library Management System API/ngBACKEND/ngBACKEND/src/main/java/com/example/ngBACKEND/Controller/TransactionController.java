package com.example.ngBACKEND.Controller;

import com.example.ngBACKEND.Entity.Transactions;
import com.example.ngBACKEND.Respons.Respons;
import com.example.ngBACKEND.Service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    TransactionsService transactionsService;
    @GetMapping
    public Respons<?> getAllTransactions() {
        try{
            return  new Respons<>(true,"getAllTransactions",transactionsService.getAllTransactions());
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Respons<?> getTransactionById(@PathVariable Integer id) {
        try{
            return  new Respons<>(true,"getTransactionById",transactionsService.getTransactionById(id));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public Respons<?> getTransactionsByUser(@PathVariable Integer userId) {
        try{
            return  new Respons<>(true,"getTransactionsByUser",transactionsService.getTransactionsByUser(userId));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/book/{bookId}")
    public Respons<?> getTransactionsByBook(@PathVariable Integer bookId) {
        try{
            return  new Respons<>(true,"getTransactionsByBook",transactionsService.getTransactionsByBook(bookId));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/status")
    public Respons<?> getTransactionsByStatus(@RequestParam Transactions.TransactionStatus status) {
        try{
            return  new Respons<>(true,"getTransactionsByStatus",transactionsService.getTransactionsByStatus(status));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/overdue")
    public Respons<?> getOverdueTransactions() {
        try{
            return  new Respons<>(true,"getOverdueTransactions",transactionsService.getOverdueTransactions());
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/active/user/{userId}")
    public Respons<?> getActiveTransactionsByUser(@PathVariable Integer userId) {
        try{
            return  new Respons<>(true,"getActiveTransactionsByUser",transactionsService.getActiveTransactionsByUser(userId));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/date-range")
    public Respons<?> getTransactionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        try{
            return  new Respons<>(true,"getTransactionsByDateRange",transactionsService.getTransactionsByDateRange(startDate, endDate));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @PostMapping("/issue")
    public Respons<?> issueBook(
            @RequestBody Integer userId,
            @RequestBody Integer bookId,
            @RequestBody Integer daysToReturn,
            @RequestBody String note
    ) {
        try{
            return  new Respons<>(true,"issueBook",transactionsService.issueBook(userId, bookId, daysToReturn, note));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @PutMapping("/return/{transactionId}")
    public Respons<?> returnBook(@PathVariable Integer transactionId) {
        try{
            return  new Respons<>(true,"returnBook",transactionsService.returnBook(transactionId));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @PutMapping("/renew/{transactionId}")
    public Respons<?> renewBook(
            @PathVariable Integer transactionId,
            @RequestParam Integer additionalDays
    ) {
        try{
            return  new Respons<>(true,"renewBook",transactionsService.renewBook(transactionId, additionalDays));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }
}
