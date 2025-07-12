package com.example.ngBACKEND.Controller;

import com.example.ngBACKEND.Entity.Reservation;
import com.example.ngBACKEND.Respons.Respons;
import com.example.ngBACKEND.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/reservation")
public class ReservationController {
    @Autowired
    ReservationService reservationService;
    @GetMapping
    public Respons<?> getAllReservations() {
        try{
            return  new Respons<>(true,"getAllReservations",reservationService.getAllReservations() );
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Respons<?> getReservationById(@PathVariable Integer id) {
        try{
            return  new Respons<>(true,"getReservationById", reservationService.getReservationById(id));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public Respons<?> getReservationsByUser(@PathVariable Integer userId) {
        try{
            return  new Respons<>(true,"getReservationsByUser", reservationService.getReservationsByUser(userId));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/book/{bookId}")
    public Respons<?> getReservationsByBook(@PathVariable Integer bookId) {
        try{
            return  new Respons<>(true,"getReservationsByBook", reservationService.getReservationsByBook(bookId));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/status")
    public Respons<?> getReservationsByStatus(@RequestParam Reservation.ReservationStatus status) {
        try{
            return  new Respons<>(true,"",reservationService.getReservationsByStatus(status) );
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/active/user/{userId}")
    public Respons<?> getActiveReservationsByUser(@PathVariable Integer userId) {
        try{
            return  new Respons<>(true,"",reservationService.getActiveReservationsByUser(userId));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }

    }

    @GetMapping("/expired")
    public Respons<?> getExpiredReservations() {
        try{
            return  new Respons<>(true,"",reservationService.getExpiredReservations());
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @PostMapping("/create")
    public Respons<?> createReservation(@RequestParam Integer userId, @RequestParam Integer bookId) {
        try{
            return  new Respons<>(true,"",reservationService.createReservation(userId, bookId));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @PutMapping("/cancel/{reservationId}")
    public Respons<?> cancelReservation(@PathVariable Integer reservationId) {
        try{
            return  new Respons<>(true,"",reservationService.cancelReservation(reservationId));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @PutMapping("/expire/{reservationId}")
    public Respons<?> expireReservation(@PathVariable Integer reservationId) {
        try{
            return  new Respons<>(true,"",reservationService.expireReservation(reservationId));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @PutMapping("/process-expired")
    public Respons<?> processExpiredReservations() {
        try{
            return  new Respons<>(true,"",reservationService.processExpiredReservations());
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }
}
