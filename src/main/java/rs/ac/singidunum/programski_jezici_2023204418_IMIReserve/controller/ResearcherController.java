package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.ResearcherDTO;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Researcher;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.service.ResearcherService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/researcher")
@CrossOrigin
@RequiredArgsConstructor
public class ResearcherController {

    private final ResearcherService service;

    @GetMapping
    public List<ResearcherDTO> getResearchers() {
        return service.getResearchers().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResearcherDTO getResearcherById(@PathVariable Integer id) {
        return service.getResearcherById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("RESEARCHER_NOT_FOUND"));
    }

    @PostMapping
    public ResearcherDTO saveResearcher(@RequestBody ResearcherDTO dto) {
        Researcher researcher = service.createResearcher(dto);
        return convertToDTO(researcher);
    }

    @PutMapping("/{id}")
    public ResearcherDTO updateResearcher(@PathVariable Integer id, @RequestBody ResearcherDTO dto) {
        Researcher researcher = service.updateResearcher(id, dto);
        return convertToDTO(researcher);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResearcher(@PathVariable Integer id) {
        service.deleteResearcher(id);
    }

    private ResearcherDTO convertToDTO(Researcher r) {
        ResearcherDTO dto = new ResearcherDTO();
        dto.setFirstName(r.getFirstName());
        dto.setLastName(r.getLastName());
        dto.setPhone(r.getPhone());
        dto.setEmail(r.getEmail());
        return dto;
    }
}