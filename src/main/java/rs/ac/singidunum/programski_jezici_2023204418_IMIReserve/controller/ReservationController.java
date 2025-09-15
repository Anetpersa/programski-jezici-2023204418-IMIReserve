package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Reservation;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/reservation")
@CrossOrigin
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    @GetMapping
    public List<Reservation> getReservations() {
        return service.getReservations();
    }

    @GetMapping(path = "/{id}")
    public Reservation getReservationById(@PathVariable Integer id) {
        return service.getReservationById(id)
                .orElseThrow(() -> new RuntimeException("RESERVATION_NOT_FOUND"));
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation model) {
        service.createReservation(model);
        return model;
    }

    @PutMapping(path = "/{id}")
    public Reservation updateReservation(@PathVariable Integer id, @RequestBody Reservation model) {
        service.updateReservation(id, model);
        return model;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable Integer id) {
        service.deleteReservation(id);
    }
}