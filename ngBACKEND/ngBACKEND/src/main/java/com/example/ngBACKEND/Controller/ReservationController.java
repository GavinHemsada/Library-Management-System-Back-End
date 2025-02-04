package com.example.ngBACKEND.Controller;

import com.example.ngBACKEND.DTO.ReservationDTO;
import com.example.ngBACKEND.Entity.Reservation;
import com.example.ngBACKEND.Response.CRUDRespons;
import com.example.ngBACKEND.Service.ReservationService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/ngCRUD/reservation")
public class ReservationController {
    @Autowired
    ReservationService reservationService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ReservationDTO reservationDTO){
        CRUDRespons create = reservationService.createReservation(reservationDTO);
        return ResponseEntity.ok(create);
    }
    @GetMapping("/read")
    public Reservation read(@RequestParam int id){
        return reservationService.readReservation(id);
    }
    @GetMapping("/readAll")
    public List<Reservation> readAll(){
        return reservationService.readAllReservation();
    }
    @PutMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody ReservationDTO reservationDTO){
        CRUDRespons edit = reservationService.editReservation(reservationDTO);
        return ResponseEntity.ok(edit);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody JsonNode jsonNode){
        int id = jsonNode.get("id").asInt();
        CRUDRespons delete = reservationService.deleteReservation(id);
        return ResponseEntity.ok(delete);

    }
}
