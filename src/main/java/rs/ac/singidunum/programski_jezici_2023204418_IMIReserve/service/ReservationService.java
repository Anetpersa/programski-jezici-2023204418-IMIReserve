package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Instrument;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Researcher;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Reservation;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.repository.InstrumentRepository;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.repository.ResearcherRepository;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ResearcherRepository researcherRepository;
    private final InstrumentRepository instrumentRepository;

    public List<Reservation> getReservations() {
        return reservationRepository.findAllByDeletedAtIsNull();
    }

    public Optional<Reservation> getReservationById(Integer id) {
        return reservationRepository.findByIdAndDeletedAtIsNull(id);
    }

    public void createReservation(Reservation model) {
        Reservation reservation = new Reservation();
        reservation.setUuid(UUID.randomUUID().toString());
        reservation.setParameter(model.getParameter());
        reservation.setStartTime(model.getStartTime());
        reservation.setEndTime(model.getEndTime());

        if (!researcherRepository.existsByIdAndDeletedAtIsNull(model.getResearcher().getId()))
            throw new RuntimeException("RESEARCHER_NOT_FOUND");

        Researcher researcher = new Researcher();
        researcher.setId(model.getResearcher().getId());
        reservation.setResearcher(researcher);

        if (!instrumentRepository.existsByIdAndDeletedAtIsNull(model.getInstrument().getId()))
            throw new RuntimeException("INSTRUMENT_NOT_FOUND");

        Instrument instrument = new Instrument();
        instrument.setId(model.getInstrument().getId());
        reservation.setInstrument(instrument);

        reservation.setCreatedAt(LocalDateTime.now());
        reservationRepository.save(reservation);
    }

    public void updateReservation(Integer id, Reservation model) {
        Reservation reservation = this.getReservationById(id).orElseThrow();
        reservation.setParameter(model.getParameter());
        reservation.setStartTime(model.getStartTime());
        reservation.setEndTime(model.getEndTime());

        Researcher researcher = new Researcher();
        researcher.setId(model.getResearcher().getId());
        reservation.setResearcher(researcher);

        Instrument instrument = new Instrument();
        instrument.setId(model.getInstrument().getId());
        reservation.setInstrument(instrument);

        reservation.setUpdatedAt(LocalDateTime.now());
        reservationRepository.save(reservation);
    }

    public void deleteReservation(Integer id) {
        Reservation reservation = this.getReservationById(id).orElseThrow();
        reservation.setDeletedAt(LocalDateTime.now());
        reservationRepository.save(reservation);
    }
}