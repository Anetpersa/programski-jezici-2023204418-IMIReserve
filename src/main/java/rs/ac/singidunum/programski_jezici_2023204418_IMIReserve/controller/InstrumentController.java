package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.request.InstrumentDTO;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.response.InstrumentResponseDTO;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Instrument;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.service.InstrumentService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instrument")
@CrossOrigin
@RequiredArgsConstructor
public class InstrumentController {

    private final InstrumentService service;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @GetMapping
    public List<InstrumentResponseDTO> getInstruments() {
        List<Instrument> instruments = service.getInstruments();
        List<InstrumentResponseDTO> dtos = new ArrayList<InstrumentResponseDTO>();
        for (Instrument i : instruments) {
            InstrumentResponseDTO dto = convertToResponseDTO(i);
            dtos.add(dto);
        }
        return dtos;
    }

    @GetMapping("/{id}")
    public InstrumentResponseDTO getInstrumentById(@PathVariable Integer id) {
        Optional<Instrument> optionalInstrument = service.getInstrumentById(id);
        if (optionalInstrument.isPresent()) {
            Instrument instrument = optionalInstrument.get();
            return convertToResponseDTO(instrument);
        } else {
            throw new RuntimeException("INSTRUMENT_NOT_FOUND");
        }
    }

    @PostMapping
    public InstrumentResponseDTO saveInstrument(@RequestBody InstrumentDTO dto) {
        Instrument instrument = service.createInstrument(dto);
        return convertToResponseDTO(instrument);
    }

    @PutMapping("/{id}")
    public InstrumentResponseDTO updateInstrument(@PathVariable Integer id, @RequestBody InstrumentDTO dto) {
        Instrument instrument = service.updateInstrument(id, dto);
        return convertToResponseDTO(instrument);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInstrument(@PathVariable Integer id) {
        service.deleteInstrument(id);
    }

    private InstrumentResponseDTO convertToResponseDTO(Instrument i) {
        InstrumentResponseDTO dto = new InstrumentResponseDTO();
        dto.setId(i.getId());
        dto.setInstrumentName(i.getInstrumentName());
        dto.setLaboratory(i.getLaboratory());

        if (i.getCreatedAt() != null) {
            dto.setCreatedAt(i.getCreatedAt().format(formatter));
        } else {
            dto.setCreatedAt(null);
        }

        if (i.getUpdatedAt() != null) {
            dto.setUpdatedAt(i.getUpdatedAt().format(formatter));
        } else {
            dto.setUpdatedAt(null);
        }

        return dto;
    }
}