package com.java.siit.taxcalculator.domain.entity;


import com.java.siit.taxcalculator.config.UserRoles;
import com.java.siit.taxcalculator.domain.entity.business.PfaEntity;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;

import java.util.List;



@Entity
@Table(name = "login")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email
    @Column(name = "user_email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_password")
    private String password;

    @Column(name = "type_of_business")
    private String typeOfBusiness;

    @Column(name = "enabled")
    private String enabled;

    @Column(name = "user_role")
    private UserRoles userRoles;

    @OneToMany(mappedBy = "loginEntity")
    private List<ReviewEntity> reviewEntities;
    

}
