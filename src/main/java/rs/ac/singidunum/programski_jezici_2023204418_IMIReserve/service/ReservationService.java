package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.request.ReservationDTO;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.response.ReservationResponseDTO;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Instrument;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Researcher;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Reservation;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.repository.InstrumentRepository;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.repository.ResearcherRepository;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ResearcherRepository researcherRepository;
    private final InstrumentRepository instrumentRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public List<ReservationResponseDTO> getReservations() {
        List<Reservation> reservations = reservationRepository.findAllByDeletedAtIsNull();
        List<ReservationResponseDTO> dtos = new ArrayList<ReservationResponseDTO>();
        for (Reservation r : reservations) {
            dtos.add(toDTO(r));
        }
        return dtos;
    }

    public Optional<ReservationResponseDTO> getReservationById(Integer id) {
        Optional<Reservation> optionalReservation = reservationRepository.findByIdAndDeletedAtIsNull(id);
        if (optionalReservation.isPresent()) {
            return Optional.of(toDTO(optionalReservation.get()));
        } else {
            return Optional.empty();
        }
    }

    public ReservationResponseDTO createReservation(ReservationDTO dto) {
        Optional<Researcher> optionalResearcher = researcherRepository.findByIdAndDeletedAtIsNull(dto.getResearcherId());
        if (!optionalResearcher.isPresent()) {
            throw new RuntimeException("RESEARCHER_NOT_FOUND");
        }
        Researcher researcher = optionalResearcher.get();

        Optional<Instrument> optionalInstrument = instrumentRepository.findByIdAndDeletedAtIsNull(dto.getInstrumentId());
        if (!optionalInstrument.isPresent()) {
            throw new RuntimeException("INSTRUMENT_NOT_FOUND");
        }
        Instrument instrument = optionalInstrument.get();

        Reservation reservation = new Reservation();

        if (dto.getUuid() != null) {
            reservation.setUuid(dto.getUuid());
        } else {
            reservation.setUuid(UUID.randomUUID().toString());
        }

        reservation.setParameter(dto.getParameter());
        reservation.setStartTime(LocalDateTime.parse(dto.getStartTime(), formatter));
        reservation.setEndTime(LocalDateTime.parse(dto.getEndTime(), formatter));
        reservation.setResearcher(researcher);
        reservation.setInstrument(instrument);
        reservation.setCreatedAt(LocalDateTime.now());

        return toDTO(reservationRepository.save(reservation));
    }

    public ReservationResponseDTO updateReservation(Integer id, ReservationDTO dto) {
        Optional<Reservation> optionalReservation = reservationRepository.findByIdAndDeletedAtIsNull(id);
        if (!optionalReservation.isPresent()) {
            throw new RuntimeException("RESERVATION_NOT_FOUND");
        }
        Reservation reservation = optionalReservation.get();

        Optional<Researcher> optionalResearcher = researcherRepository.findByIdAndDeletedAtIsNull(dto.getResearcherId());
        if (!optionalResearcher.isPresent()) {
            throw new RuntimeException("RESEARCHER_NOT_FOUND");
        }
        Researcher researcher = optionalResearcher.get();

        Optional<Instrument> optionalInstrument = instrumentRepository.findByIdAndDeletedAtIsNull(dto.getInstrumentId());
        if (!optionalInstrument.isPresent()) {
            throw new RuntimeException("INSTRUMENT_NOT_FOUND");
        }
        Instrument instrument = optionalInstrument.get();

        reservation.setParameter(dto.getParameter());
        reservation.setStartTime(LocalDateTime.parse(dto.getStartTime(), formatter));
        reservation.setEndTime(LocalDateTime.parse(dto.getEndTime(), formatter));
        reservation.setResearcher(researcher);
        reservation.setInstrument(instrument);
        reservation.setUpdatedAt(LocalDateTime.now());

        return toDTO(reservationRepository.save(reservation));
    }

    public void deleteReservation(Integer id) {
        Optional<Reservation> optionalReservation = reservationRepository.findByIdAndDeletedAtIsNull(id);
        if (!optionalReservation.isPresent()) {
            throw new RuntimeException("RESERVATION_NOT_FOUND");
        }
        Reservation reservation = optionalReservation.get();
        reservation.setDeletedAt(LocalDateTime.now());
        reservationRepository.save(reservation);
    }

    public ReservationResponseDTO toDTO(Reservation r) {
        ReservationResponseDTO dto = new ReservationResponseDTO();
        dto.setId(r.getId());
        dto.setResearcherId(r.getResearcher().getId());
        dto.setResearcherName(r.getResearcher().getFirstName() + " " + r.getResearcher().getLastName());
        dto.setInstrumentId(r.getInstrument().getId());
        dto.setInstrumentName(r.getInstrument().getInstrumentName());
        dto.setParameter(r.getParameter());
        dto.setStartTime(r.getStartTime().toString());
        dto.setEndTime(r.getEndTime().toString());
        dto.setUuid(r.getUuid());

        if (r.getCreatedAt() != null) {
            dto.setCreatedAt(r.getCreatedAt().toString());
        } else {
            dto.setCreatedAt(null);
        }

        if (r.getUpdatedAt() != null) {
            dto.setUpdatedAt(r.getUpdatedAt().toString());
        } else {
            dto.setUpdatedAt(null);
        }

        return dto;
    }
}