package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.request.ResearcherDTO;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.response.ResearcherResponseDTO;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Researcher;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.service.ResearcherService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/researcher")
@CrossOrigin
@RequiredArgsConstructor
public class ResearcherController {

    private final ResearcherService service;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @GetMapping
    public List<ResearcherResponseDTO> getResearchers() {
        List<Researcher> researchers = service.getResearchers();
        List<ResearcherResponseDTO> dtos = new ArrayList<ResearcherResponseDTO>();
        for (Researcher r : researchers) {
            ResearcherResponseDTO dto = convertToResponseDTO(r);
            dtos.add(dto);
        }
        return dtos;
    }

    @GetMapping("/{id}")
    public ResearcherResponseDTO getResearcherById(@PathVariable Integer id) {
        Optional<Researcher> optionalResearcher = service.getResearcherById(id);
        if (optionalResearcher.isPresent()) {
            Researcher researcher = optionalResearcher.get();
            return convertToResponseDTO(researcher);
        } else {
            throw new RuntimeException("RESEARCHER_NOT_FOUND");
        }
    }

    @PostMapping
    public ResearcherResponseDTO saveResearcher(@RequestBody ResearcherDTO dto) {
        Researcher researcher = service.createResearcher(dto);
        return convertToResponseDTO(researcher);
    }

    @PutMapping("/{id}")
    public ResearcherResponseDTO updateResearcher(@PathVariable Integer id, @RequestBody ResearcherDTO dto) {
        Researcher researcher = service.updateResearcher(id, dto);
        return convertToResponseDTO(researcher);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResearcher(@PathVariable Integer id) {
        service.deleteResearcher(id);
    }

    private ResearcherResponseDTO convertToResponseDTO(Researcher r) {
        ResearcherResponseDTO dto = new ResearcherResponseDTO();
        dto.setId(r.getId());
        dto.setFirstName(r.getFirstName());
        dto.setLastName(r.getLastName());
        dto.setPhone(r.getPhone());
        dto.setEmail(r.getEmail());

        if (r.getCreatedAt() != null) {
            dto.setCreatedAt(r.getCreatedAt().format(formatter));
        } else {
            dto.setCreatedAt(null);
        }

        if (r.getUpdatedAt() != null) {
            dto.setUpdatedAt(r.getUpdatedAt().format(formatter));
        } else {
            dto.setUpdatedAt(null);
        }

        return dto;
    }
}