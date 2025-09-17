package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.entity.Reservation;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByDeletedAtIsNull();

    Optional<Reservation> findByIdAndDeletedAtIsNull(Integer id);
}
