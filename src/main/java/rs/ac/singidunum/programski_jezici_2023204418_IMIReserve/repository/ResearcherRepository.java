package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResearcherRepository {

    List<Researcher> findAllByDeletedAtIsNull();

    Optional<Researcher> findByIdAndDeletedAtIsNull(Integer id);
}
