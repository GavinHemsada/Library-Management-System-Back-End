package com.example.ngBACKEND.Controller;

import com.example.ngBACKEND.Entity.Fine;
import com.example.ngBACKEND.Respons.Respons;
import com.example.ngBACKEND.Service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("api/fine")
public class FineController {
    @Autowired
    FineService fineService;
    @GetMapping
    public Respons<?> getAllFines() {
        try{
            return  new Respons<>(true,"getAllFines",fineService.getAllFines());
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Respons<?> getFineById(@PathVariable Integer id) {
        try{
            return  new Respons<>(true,"getFineById",fineService.getFineById(id));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public Respons<?> getFinesByUser(@PathVariable Integer userId) {
        try{
            return  new Respons<>(true,"getFinesByUser",fineService.getFinesByUser(userId));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/transaction/{transactionId}")
    public Respons<?> getFinesByTransaction(@PathVariable Integer transactionId) {
        try{
            return  new Respons<>(true,"getFinesByTransaction",fineService.getFinesByTransaction(transactionId));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/pending")
    public Respons<?> getPendingFines() {
        try{
            return  new Respons<>(true,"getPendingFines",fineService.getPendingFines());
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/status")
    public Respons<?> getFinesByStatus(@RequestParam Fine.PaymentStatus status) {
        try{
            return  new Respons<>(true,"getFinesByStatus",fineService.getFinesByPaymentStatus(status));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/date-range")
    public Respons<?> getFinesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        try{
            return  new Respons<>(true,"getFinesByDateRange",fineService.getFinesByDateRange(startDate, endDate));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @PutMapping("/pay/{fineId}")
    public Respons<?> payFine(@PathVariable Integer fineId) {
        try{
            return  new Respons<>(true,"payFine",fineService.payFine(fineId));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @PutMapping("/waive/{fineId}")
    public Respons<?> waiveFine(@PathVariable Integer fineId, @RequestParam String reason) {
        try{
            return  new Respons<>(true,"waiveFine",fineService.waiveFine(fineId, reason));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @PostMapping("/create")
    public Respons<?> createFine(
            @RequestParam Integer transactionId,
            @RequestParam BigDecimal amount,
            @RequestParam String reason
    ) {
        try{
            return  new Respons<>(true,"createFine",fineService.createFine(transactionId, amount, reason));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/total/paid")
    public Respons<?> getTotalPaidFines() {
        try{
            return  new Respons<>(true,"getTotalPaidFines",fineService.getTotalPaidFines());
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/total/pending")
    public Respons<?> getTotalPendingFines() {
        try{
            return  new Respons<>(true,"getTotalPendingFines",fineService.getTotalPendingFines());
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    @GetMapping("/total/pending/user/{userId}")
    public Respons<?> getTotalPendingFinesByUser(@PathVariable Integer userId) {
        try{
            return  new Respons<>(true,"getTotalPendingFinesByUser",fineService.getTotalPendingFinesByUser(userId));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }
}
