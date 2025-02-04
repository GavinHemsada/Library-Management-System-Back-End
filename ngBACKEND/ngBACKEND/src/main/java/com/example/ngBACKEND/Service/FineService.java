package com.example.ngBACKEND.Service;

import com.example.ngBACKEND.DTO.FineDTO;
import com.example.ngBACKEND.Entity.Fine;
import com.example.ngBACKEND.Entity.Transactions;
import com.example.ngBACKEND.Entity.User;
import com.example.ngBACKEND.Repostry.FineRepository;
import com.example.ngBACKEND.Repostry.TransactionsRepository;
import com.example.ngBACKEND.Repostry.UserRepository;
import com.example.ngBACKEND.Response.CRUDRespons;
import com.example.ngBACKEND.Response.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FineService {
    @Autowired
    private FineRepository fineRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionsRepository transactionsRepository;

    public CRUDRespons createFine(FineDTO fineDTO){
        User user = userRepository.findById(fineDTO.getUserId());
        Transactions transactions = transactionsRepository.findById(fineDTO.getTransactionId());
        Fine fine = new Fine(user,transactions,fineDTO.getAmount(),fineDTO.getStatus());
        if(user != null & transactions != null) {
            fineRepository.save(fine);
            return new CRUDRespons("successfully added",true);
        }
        return  new CRUDRespons("Details are Empty",false);
    }
    public Fine readFine(int id){
        Fine fine = fineRepository.findById(id);
        if(fine==null){
           throw  new NotFoundException("Cant find Fine");
        }
        return fine;
    }

    public List<Fine> readAllFine(){
        return fineRepository.findAll();
    }
     public CRUDRespons editFine(FineDTO fineDTO){
        Fine fine = fineRepository.findById(fineDTO.getId());
        if(fine != null){
            fine.setAmount(fineDTO.getAmount());
            fine.setStatus(fineDTO.getStatus());
            fineRepository.save(fine);
            return  new CRUDRespons("Successfully update",true);
        }
        return new CRUDRespons("Cant find ID",false);
     }
     public  CRUDRespons deleteFine(int id){
        Fine fine = fineRepository.findById(id);
        if(fine != null){
            fineRepository.delete(fine);
            return new CRUDRespons("successfully Delete",true);
        }
        return new CRUDRespons("Cant find id",false);
     }
}
