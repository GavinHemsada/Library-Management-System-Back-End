package com.example.ngBACKEND.Controller;

import com.example.ngBACKEND.DTO.FineDTO;
import com.example.ngBACKEND.Entity.Fine;
import com.example.ngBACKEND.Response.CRUDRespons;
import com.example.ngBACKEND.Service.FineService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/ngCRUD/fine")
public class FineController {
    @Autowired
    FineService fineService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody FineDTO fineDTO){
        CRUDRespons create = fineService.createFine(fineDTO);
        return ResponseEntity.ok(create);
    }
    @GetMapping("/read")
    public Fine read(@RequestParam int id){
        return fineService.readFine(id);
    }
    @GetMapping("/readAll")
    public List<Fine> readAll(){
        return fineService.readAllFine();
    }
    @PutMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody FineDTO fineDTO){
        CRUDRespons edit = fineService.editFine(fineDTO);
        return ResponseEntity.ok(edit);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody JsonNode jsonNode){
        int id = jsonNode.get("id").asInt();
        CRUDRespons delete = fineService.deleteFine(id);
        return ResponseEntity.ok(delete);
    }
}
