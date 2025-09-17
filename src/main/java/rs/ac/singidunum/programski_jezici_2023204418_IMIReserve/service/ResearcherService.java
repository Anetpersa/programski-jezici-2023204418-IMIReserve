package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.request.ResearcherDTO;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Researcher;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.repository.ResearcherRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResearcherService {

    private final ResearcherRepository repository;

    public List<Researcher> getResearchers() {
        return repository.findAllByDeletedAtIsNull();
    }

    public Optional<Researcher> getResearcherById(Integer id) {
        return repository.findByIdAndDeletedAtIsNull(id);
    }

    public Researcher createResearcher(ResearcherDTO dto) {
        Researcher researcher = new Researcher();
        researcher.setFirstName(dto.getFirstName());
        researcher.setLastName(dto.getLastName());
        researcher.setPhone(dto.getPhone());
        researcher.setEmail(dto.getEmail());
        researcher.setCreatedAt(LocalDateTime.now());
        return repository.save(researcher);
    }

    public Researcher updateResearcher(Integer id, ResearcherDTO dto) {
        Researcher researcher = repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("RESEARCHER_NOT_FOUND"));

        researcher.setFirstName(dto.getFirstName());
        researcher.setLastName(dto.getLastName());
        researcher.setPhone(dto.getPhone());
        researcher.setEmail(dto.getEmail());
        researcher.setUpdatedAt(LocalDateTime.now());
        return repository.save(researcher);
    }

    public void deleteResearcher(Integer id) {
        Researcher researcher = repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("RESEARCHER_NOT_FOUND"));
        researcher.setDeletedAt(LocalDateTime.now());
        repository.save(researcher);
    }
}