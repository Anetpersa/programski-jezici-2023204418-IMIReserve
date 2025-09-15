package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Researcher;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.service.ResearcherService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/researcher")
@CrossOrigin
@RequiredArgsConstructor
public class ResearcherController {

    private final ResearcherService service;

    @GetMapping
    public List<Researcher> getResearchers() {
        return service.getResearchers();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Researcher> getResearcherById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getResearcherById(id));
    }

    @PostMapping
    public Researcher saveResearcher(@RequestBody Researcher model) {

        return service.createResearcher(model);
    }

    @PutMapping(path = "/{id}")
    public Researcher updateResearcher(@PathVariable Integer id, @RequestBody Researcher model) {

        return service.updateResearcher(id, model);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResearcher(@PathVariable Integer id) {
        service.deleteResearcher(id);
    }
}
