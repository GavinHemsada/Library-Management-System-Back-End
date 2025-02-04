package com.example.ngBACKEND.Service;

import com.example.ngBACKEND.DTO.ReservationDTO;
import com.example.ngBACKEND.Entity.Books;
import com.example.ngBACKEND.Entity.Reservation;
import com.example.ngBACKEND.Entity.User;
import com.example.ngBACKEND.Repostry.BooksRepository;
import com.example.ngBACKEND.Repostry.ReservationRepository;
import com.example.ngBACKEND.Repostry.UserRepository;
import com.example.ngBACKEND.Response.CRUDRespons;
import com.example.ngBACKEND.Response.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private UserRepository userRepository;
    public CRUDRespons createReservation(ReservationDTO reservationDTO){
        User user = userRepository.findById(reservationDTO.getUserId());
        Books books = booksRepository.findByid(reservationDTO.getBookId());
        if(user != null & books != null){
            Reservation reservation = new Reservation(books,user,reservationDTO.getReservedAt(),reservationDTO.getStatus());
            reservationRepository.save(reservation);
            return new CRUDRespons("Successfully created",true);
        }
        return new CRUDRespons("inputs are empty",false);
    }
    public List<Reservation> readAllReservation(){
        return reservationRepository.findAll();
    }
    public Reservation readReservation( int id){
        Reservation reservation = reservationRepository.findById(id);
        if(reservation == null){
            throw new NotFoundException("cant find reservation");
        }
        return reservation;
    }
    public CRUDRespons editReservation(ReservationDTO reservationDTO){
        Reservation reservation = reservationRepository.findById(reservationDTO.getId());
        if(reservation != null){
            reservation.setReservedAt(reservationDTO.getReservedAt());
            reservation.setStatus(reservationDTO.getStatus());
            reservationRepository.save(reservation);
            return new CRUDRespons("successfully update",true);
        }
        return new CRUDRespons("cant find id",false);
    }
    public CRUDRespons deleteReservation(int id){
        Reservation reservation = reservationRepository.findById(id);
        if(reservation!= null){
            reservationRepository.delete(reservation);
            return new CRUDRespons("successfully delete",true);
        }
        return new CRUDRespons("cant find Reservation",false);
    }
}
