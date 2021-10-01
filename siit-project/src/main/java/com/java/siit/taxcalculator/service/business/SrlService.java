package com.java.siit.taxcalculator.service.business;



import com.java.siit.taxcalculator.domain.entity.business.PfaEntity;

import com.java.siit.taxcalculator.domain.entity.business.SrlEntity;
import com.java.siit.taxcalculator.repository.business.SrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SrlService {

    private final SrlRepository srlRepository;

    public void createSRL(SrlEntity dto) {
        srlRepository.save(SrlEntity.builder()
                .income(dto.getIncome())
                .CAS((dto.getIncome() * 0))
                .CASS((dto.getIncome() * 10 / 100))
                .incomeTaxes((dto.getIncome() * 0))

                .incomeTaxesPerMonth((dto.getIncome() * 3 / 100))
                .dividendTaxesPerMonth((dto.getIncome() * 5 / 100))

                .incomeTaxesPerMonth((dto.getIncome() * 3/100))
                .dividendTaxesPerMonth((dto.getIncome() * 5/100))

                .build());
    }

    public List<SrlEntity> findAll(SrlEntity srlEntity) {
        return srlRepository.findAll();
    }


    public void save(SrlEntity srlEntity) {
        srlRepository.save(srlEntity);
    }

    public SrlEntity get(Long id) {
        return srlRepository.findById(id).get();
    }

    public void delete(Long id) {
        srlRepository.deleteById(id);
    }

}
