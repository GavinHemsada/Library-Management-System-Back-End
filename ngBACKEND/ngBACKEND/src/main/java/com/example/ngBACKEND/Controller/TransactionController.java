package com.example.ngBACKEND.Controller;

import com.example.ngBACKEND.DTO.TransactionDTO;
import com.example.ngBACKEND.Entity.Transactions;
import com.example.ngBACKEND.Response.CRUDRespons;
import com.example.ngBACKEND.Service.TransactionsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/ngCRUD/transaction")
public class TransactionController {
    @Autowired
    TransactionsService transactionsService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TransactionDTO transactionDTO){
        CRUDRespons createRespons = transactionsService.createTransactions(transactionDTO);
        return ResponseEntity.ok(createRespons);
    }
    @GetMapping("/read")
    public Transactions read(@RequestParam int id){
        return transactionsService.readTransactions(id);
    }
    @GetMapping("/readAll")
    public List<Transactions> readAll(){
        return transactionsService.readAllTransactions();
    }
    @PutMapping ("/edit")
    public ResponseEntity<?> edit(@RequestBody TransactionDTO transactionDTO){
        CRUDRespons edit = transactionsService.editTransactions(transactionDTO);
        return ResponseEntity.ok(edit);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody JsonNode jsonNode){
        int id = jsonNode.get("id").asInt();
        CRUDRespons delete = transactionsService.deleteTransactions(id);
        return ResponseEntity.ok(delete);
    }
}
