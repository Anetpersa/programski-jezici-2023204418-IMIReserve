package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Researcher;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.repository.ResearcherRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResearcherService {

    private final ResearcherRepository repository;

    public List<Researcher> getResearcher() {
        return repository.findAllByDeletedAtIsNull();
    }

    public Optional<Researcher> getResearcherById(Integer id) {
        return repository.findByIdAndDeletedAtIsNull(id);
    }

    public Researcher createResearcher(Researcher model) {
        Researcher researcher = new Researcher();
        researcher.setFirstName(model.getFirstName());
        researcher.setLastName(model.getLastName());
        researcher.setPhone(model.getPhone());
        researcher.setEmail(model.getEmail());
        researcher.setCreatedAt(LocalDateTime.now());
        return repository.save(researcher);
    }

    public Researcher updateResearcher(Integer id, Researcher model) {
        Researcher researcher = repository.findByIdAndDeletedAtIsNull(id).orElseThrow();

        researcher.setFirstName(model.getFirstName());
        researcher.setLastName(model.getLastName());
        researcher.setPhone(model.getPhone());
        researcher.setEmail(model.getEmail());
        researcher.setUpdatedAt(LocalDateTime.now());
        return repository.save(researcher);
    }

    public void deleteResearcher(Integer id) {
        Researcher researcher = repository.findByIdAndDeletedAtIsNull(id).orElseThrow();
        researcher.setDeletedAt(LocalDateTime.now());
        repository.save(researcher);
    }
}
