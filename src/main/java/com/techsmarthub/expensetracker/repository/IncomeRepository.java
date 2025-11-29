package com.techsmarthub.expensetracker.repository;

import com.techsmarthub.expensetracker.entity.IncomeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {

    List<IncomeEntity> findByProfileIdOrderByDateDesc(long profile_id);

    List<IncomeEntity> findTop5ByProfileIdOrderByDateDesc(long profileId);

    //Total Income by Profile Id
    @Query("SELECT SUM(i.amount) FROM IncomeEntity i where i.profile.id = :profileId")
    BigDecimal findTotalIncomeByProfileId(@Param("profileId") long profileId);

    List<IncomeEntity> findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(long profileId, LocalDate startDate, LocalDate endDate, String name, Sort sort);

    List<IncomeEntity> findByProfileIdAndDateBetween(long profileId, LocalDate startDate, LocalDate endDate);
}
