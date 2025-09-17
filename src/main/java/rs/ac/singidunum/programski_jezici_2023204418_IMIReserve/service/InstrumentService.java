package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.request.InstrumentDTO;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Instrument;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.repository.InstrumentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstrumentService {
    private final InstrumentRepository repository;

    public List<Instrument> getInstruments() {
        return repository.findAllByDeletedAtIsNull();
    }

    public Optional<Instrument> getInstrumentById(Integer id) {
        return repository.findByIdAndDeletedAtIsNull(id);
    }

    public Instrument createInstrument(InstrumentDTO dto) {
        Instrument instrument = new Instrument();
        instrument.setInstrumentName(dto.getInstrumentName());
        instrument.setLaboratory(dto.getLaboratory());
        instrument.setCreatedAt(LocalDateTime.now());
        return repository.save(instrument);
    }

    public Instrument updateInstrument(Integer id, InstrumentDTO dto) {
        Instrument instrument = repository.findByIdAndDeletedAtIsNull(id).orElseThrow();

        instrument.setInstrumentName(dto.getInstrumentName());
        instrument.setLaboratory(dto.getLaboratory());
        instrument.setUpdatedAt(LocalDateTime.now());
        return repository.save(instrument);
    }

    public void deleteInstrument(Integer id) {
        Instrument instrument = repository.findByIdAndDeletedAtIsNull(id).orElseThrow();
        instrument.setDeletedAt(LocalDateTime.now());
        repository.save(instrument);
    }
}