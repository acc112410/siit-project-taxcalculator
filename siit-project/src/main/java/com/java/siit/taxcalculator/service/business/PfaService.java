package com.java.siit.taxcalculator.service.business;


import com.java.siit.taxcalculator.domain.entity.business.PfaEntity;
import com.java.siit.taxcalculator.domain.model.business.PfaDTO;
import com.java.siit.taxcalculator.repository.business.PfaRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@AllArgsConstructor
public class PfaService {

    private final PfaRepository pfaRepository;


    @Autowired
    private ModelMapper modelMapper;

    public void createPfa(PfaEntity dto) {
        pfaRepository.save(PfaEntity.builder()
                .id(dto.getId())
                .loginId(dto.getLoginId())
                .income(dto.getIncome())
                .fiscalYear(dto.getFiscalYear())
                .CAS((dto.getIncome() * 25 / 100))
                .CASS((dto.getIncome() * 10 / 100))
                .incomeTaxes((dto.getIncome() * 10 / 100))
                .incomeTaxesPerMonth((dto.getIncome() * 0))
                .dividendTaxesPerMonth((dto.getIncome() * 0))
                .taxesTotal((dto.getIncome() * 25 / 100) + (dto.getIncome() * 10 / 100) + (dto.getIncome() * 10 / 100))
                .build());

    }

    public void createPfa(PfaDTO dto) {
        pfaRepository.save(PfaEntity.builder()
                .id(dto.getId())
                .loginId(dto.getLoginId())
                .income(dto.getIncome())
                .fiscalYear(dto.getFiscalYear())
                .CAS((dto.getIncome() * 25 / 100))
                .CASS((dto.getIncome() * 10 / 100))
                .incomeTaxes((dto.getIncome() * 10 / 100))
                .incomeTaxesPerMonth((dto.getIncome() * 0))
                .dividendTaxesPerMonth((dto.getIncome() * 0))
                .taxesTotal((dto.getIncome() * 25 / 100) + (dto.getIncome() * 10 / 100) + (dto.getIncome() * 10 / 100))
                .build());
    }

    public void updatePfa(PfaDTO dto) {
        pfaRepository.save(PfaEntity.builder()
                .id(dto.getId())
                .loginId(dto.getLoginId())
                .income(dto.getIncome())
                .fiscalYear(dto.getFiscalYear())
                .CAS((dto.getIncome() * 25 / 100))
                .CASS((dto.getIncome() * 10 / 100))
                .incomeTaxes((dto.getIncome() * 10 / 100))
                .incomeTaxesPerMonth((dto.getIncome() * 0))
                .dividendTaxesPerMonth((dto.getIncome() * 0))
                .taxesTotal((dto.getIncome() * 25 / 100) + (dto.getIncome() * 10 / 100) + (dto.getIncome() * 10 / 100))
                .build());
    }

    //    public long sumTaxes(PfaEntity pfaEntity) {
//        int sum=0;
//        List<PfaEntity> lista = pfaService.findAll(pfaEntity);
//        List<Long> pfaTaxesByID = new ArrayList<>();
//        for()
//        List<Long> taxesSum = Arrays.asList(pfaEntity.getTaxesTotal());
//        Integer sum = taxesSum.stream().mapToInt(Long::intValue)
//                .sum();
//        pfaEntity.setTotalTaxesById(sum);
//        System.out.println("Total : " +sum);
//        return sum;
//    }
//    public void methodThatAddAllTaxesForASpecificID(PfaEntity pfaEntity) {
//        List<PfaEntity> lista = pfaRepository.findAll();
//        List<Long> pfaTaxesByID = new ArrayList<>();
//        int sum = 0;
//        for (int i = 0; i < lista.size(); i++) {
//            pfaTaxesByID.add(lista.get(i).getTaxesTotal());
//        }
//
//        for (int i = 0; i < pfaTaxesByID.size(); i++)
//            sum += pfaTaxesByID.get(i);
//
//        pfaEntity.setTotalTaxesById(sum);
//
//    }

    public void save(PfaEntity pfaEntity) {
        pfaRepository.save(pfaEntity);
    }

    public PfaDTO toDto(PfaEntity pfaEntity) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        PfaDTO pfaDTO = new PfaDTO();
        pfaDTO = modelMapper.map(pfaEntity, PfaDTO.class);
        return pfaDTO;
    }


    public PfaEntity get(Long id) {
        return pfaRepository.findById(id).get();
    }

//    public List<PfaEntity> getAllFromFiscalYear(Long fiscalYear) {
//        return pfaRepository.findByFiscalYear();
//    }

//    public PfaEntity getById(Long loginId) {
//        return pfaRepository.findById(loginId).get();
//    }

    public void delete(Long id) {
        pfaRepository.deleteById(id);
    }

    public PfaEntity findByLoginId(Long userId) {
        return pfaRepository.findById(userId).get();
    }

    public void delete(PfaEntity pfaEntity) {
        pfaRepository.delete(pfaEntity);
    }

    //    public List<PfaEntity> findByLoginId(Long id) {
//        return pfaRepository.findAllByLoginId();
//    }
    public List<PfaEntity> findAll(PfaEntity pfaEntity) {
        return pfaRepository.findAll();
    }
}




