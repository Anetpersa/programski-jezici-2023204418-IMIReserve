package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.InstrumentDTO;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Instrument;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.service.InstrumentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/instrument")
@CrossOrigin
@RequiredArgsConstructor
public class InstrumentController {

    private final InstrumentService service;

    @GetMapping
    public List<InstrumentDTO> getInstruments() {
        return service.getInstruments().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public InstrumentDTO getInstrumentById(@PathVariable Integer id) {
        return service.getInstrumentById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("INSTRUMENT_NOT_FOUND"));
    }

    @PostMapping
    public InstrumentDTO saveInstrument(@RequestBody InstrumentDTO dto) {
        Instrument instrument = service.createInstrument(dto);
        return convertToDTO(instrument);
    }

    @PutMapping("/{id}")
    public InstrumentDTO updateInstrument(@PathVariable Integer id, @RequestBody InstrumentDTO dto) {
        Instrument instrument = service.updateInstrument(id, dto);
        return convertToDTO(instrument);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInstrument(@PathVariable Integer id) {
        service.deleteInstrument(id);
    }

    private InstrumentDTO convertToDTO(Instrument i) {
        InstrumentDTO dto = new InstrumentDTO();
        dto.setInstrumentName(i.getInstrumentName());
        dto.setLaboratory(i.getLaboratory());
        return dto;
    }
}