package com.java.siit.taxcalculator.domain.entity.business;


import com.java.siit.taxcalculator.domain.entity.LoginEntity;

import com.java.siit.taxcalculator.domain.model.business.PfaDTO;
import lombok.*;
import org.springframework.security.core.parameters.P;

import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Setter
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "pfa")

public class PfaEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "income")
    private int income;

    @Column(name = "cui")
    private long cui;


    @Column(name = "CASS")
    private int CASS;

    @Column(name = "CAS")
    private int CAS;

    @Column(name = "income_Taxes")
    private long incomeTaxes;

    @Column(name = "income_Taxes_Per_Month")
    private long incomeTaxesPerMonth;

    @Column(name = "dividend_taxes_Per_Month")
    private long dividendTaxesPerMonth;


    @Column(name = "fiscal_Year")
    private long fiscalYear;

    @Column(name = "login_Id")
    private long loginId;


}
