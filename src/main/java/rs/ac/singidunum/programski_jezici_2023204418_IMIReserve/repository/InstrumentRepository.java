package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Instrument;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Integer> {

    List<Instrument> findAllByDeletedAtIsNull();

    Optional<Instrument> findByIdAndDeletedAtIsNull(Integer id);
}
