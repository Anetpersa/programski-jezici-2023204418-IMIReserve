package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.ReservationDTO;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.ReservationResponseDTO;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    // GET sve
    @GetMapping
    public List<ReservationResponseDTO> getReservations() {
        return service.getReservations();
    }

    // GET po ID
    @GetMapping("/{id}")
    public ReservationResponseDTO getReservationById(@PathVariable Integer id) {
        return service.getReservationById(id)
                .orElseThrow(() -> new RuntimeException("RESERVATION_NOT_FOUND"));
    }

    // POST
    @PostMapping
    public ReservationResponseDTO createReservation(@RequestBody ReservationDTO dto) {
        return service.createReservation(dto);
    }

    // PUT
    @PutMapping("/{id}")
    public ReservationResponseDTO updateReservation(@PathVariable Integer id, @RequestBody ReservationDTO dto) {
        return service.updateReservation(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable Integer id) {
        service.deleteReservation(id);
    }
}