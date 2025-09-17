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
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ResearcherRepository researcherRepository;
    private final InstrumentRepository instrumentRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public List<ReservationResponseDTO> getReservations() {
        return reservationRepository.findAllByDeletedAtIsNull()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ReservationResponseDTO> getReservationById(Integer id) {
        return reservationRepository.findByIdAndDeletedAtIsNull(id)
                .map(this::toDTO);
    }

    public ReservationResponseDTO createReservation(ReservationDTO dto) {
        Researcher researcher = researcherRepository.findByIdAndDeletedAtIsNull(dto.getResearcherId())
                .orElseThrow(() -> new RuntimeException("RESEARCHER_NOT_FOUND"));

        Instrument instrument = instrumentRepository.findByIdAndDeletedAtIsNull(dto.getInstrumentId())
                .orElseThrow(() -> new RuntimeException("INSTRUMENT_NOT_FOUND"));

        Reservation reservation = new Reservation();
        reservation.setUuid(dto.getUuid() != null ? dto.getUuid() : UUID.randomUUID().toString());
        reservation.setParameter(dto.getParameter());
        reservation.setStartTime(LocalDateTime.parse(dto.getStartTime(), formatter));
        reservation.setEndTime(LocalDateTime.parse(dto.getEndTime(), formatter));
        reservation.setResearcher(researcher);
        reservation.setInstrument(instrument);
        reservation.setCreatedAt(LocalDateTime.now());

        return toDTO(reservationRepository.save(reservation));
    }

    public ReservationResponseDTO updateReservation(Integer id, ReservationDTO dto) {
        Reservation reservation = reservationRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("RESERVATION_NOT_FOUND"));

        Researcher researcher = researcherRepository.findByIdAndDeletedAtIsNull(dto.getResearcherId())
                .orElseThrow(() -> new RuntimeException("RESEARCHER_NOT_FOUND"));

        Instrument instrument = instrumentRepository.findByIdAndDeletedAtIsNull(dto.getInstrumentId())
                .orElseThrow(() -> new RuntimeException("INSTRUMENT_NOT_FOUND"));

        reservation.setParameter(dto.getParameter());
        reservation.setStartTime(LocalDateTime.parse(dto.getStartTime(), formatter));
        reservation.setEndTime(LocalDateTime.parse(dto.getEndTime(), formatter));
        reservation.setResearcher(researcher);
        reservation.setInstrument(instrument);
        reservation.setUpdatedAt(LocalDateTime.now());

        return toDTO(reservationRepository.save(reservation));
    }

    public void deleteReservation(Integer id) {
        Reservation reservation = reservationRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("RESERVATION_NOT_FOUND"));
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
        dto.setCreatedAt(r.getCreatedAt() != null ? r.getCreatedAt().toString() : null);
        dto.setUpdatedAt(r.getUpdatedAt() != null ? r.getUpdatedAt().toString() : null);
        return dto;
    }
}