package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.request.InstrumentDTO;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.response.InstrumentResponseDTO;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Instrument;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.service.InstrumentService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/instrument")
@CrossOrigin
@RequiredArgsConstructor
public class InstrumentController {

    private final InstrumentService service;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @GetMapping
    public List<InstrumentResponseDTO> getInstruments() {
        return service.getInstruments().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public InstrumentResponseDTO getInstrumentById(@PathVariable Integer id) {
        return service.getInstrumentById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new RuntimeException("INSTRUMENT_NOT_FOUND"));
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
        dto.setCreatedAt(i.getCreatedAt() != null ? i.getCreatedAt().format(formatter) : null);
        dto.setUpdatedAt(i.getUpdatedAt() != null ? i.getUpdatedAt().format(formatter) : null);
        return dto;
    }
}