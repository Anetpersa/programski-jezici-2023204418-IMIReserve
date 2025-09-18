package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.request.ReservationDTO;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.response.ReservationResponseDTO;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.service.ReservationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    // GET all reservations
    @GetMapping
    public List<ReservationResponseDTO> getReservations() {
        return service.getReservations();
    }

    // GET reservation by ID
    @GetMapping("/{id}")
    public ReservationResponseDTO getReservationById(@PathVariable Integer id) {
        Optional<ReservationResponseDTO> optionalReservation = service.getReservationById(id);
        if (optionalReservation.isPresent()) {
            return optionalReservation.get();
        } else {
            throw new RuntimeException("RESERVATION_NOT_FOUND");
        }
    }

    // POST create reservation
    @PostMapping
    public ReservationResponseDTO createReservation(@RequestBody ReservationDTO dto) {
        return service.createReservation(dto);
    }

    // PUT update reservation
    @PutMapping("/{id}")
    public ReservationResponseDTO updateReservation(@PathVariable Integer id, @RequestBody ReservationDTO dto) {
        return service.updateReservation(id, dto);
    }

    // DELETE reservation
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable Integer id) {
        service.deleteReservation(id);
    }
}