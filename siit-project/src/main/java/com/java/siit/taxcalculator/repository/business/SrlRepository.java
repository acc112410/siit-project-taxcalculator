package com.java.siit.taxcalculator.repository.business;

import com.java.siit.taxcalculator.domain.entity.business.SrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SrlRepository extends JpaRepository<SrlEntity, Long> {
}
