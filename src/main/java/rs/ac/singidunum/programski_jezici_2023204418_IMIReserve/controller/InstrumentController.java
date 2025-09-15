package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Instrument;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.service.InstrumentService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/instrument")
@CrossOrigin
@RequiredArgsConstructor
public class InstrumentController {

    private final InstrumentService service;

    @GetMapping
    public List<Instrument> getInstruments() {
        return service.getInstruments();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Instrument> getInstrumentById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getInstrumentById(id));
    }

    @PostMapping
    public Instrument saveInstrument(@RequestBody Instrument model) {

        return service.createInstrument(model);
    }

    @PutMapping(path = "/{id}")
    public Instrument updateInstrument(@PathVariable Integer id, @RequestBody Instrument model) {

        return service.updateInstrument(id, model);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInstrument(@PathVariable Integer id) {
        service.deleteInstrument(id);
    }
}
