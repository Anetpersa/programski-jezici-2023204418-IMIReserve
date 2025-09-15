package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Researcher;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResearcherRepository extends JpaRepository<Researcher, Integer> {

    List<Researcher> findAllByDeletedAtIsNull();

    Optional<Researcher> findByIdAndDeletedAtIsNull(Integer id);

    Boolean existsByIdAndDeletedAtIsNull(Integer id);
}
