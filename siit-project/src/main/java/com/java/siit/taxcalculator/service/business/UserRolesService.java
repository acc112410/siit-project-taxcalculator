package com.java.siit.taxcalculator.service.business;

import com.java.siit.taxcalculator.config.UserRoles;
import com.java.siit.taxcalculator.domain.entity.LoginEntity;
import com.java.siit.taxcalculator.repository.business.UserRolesRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRolesService {
    @Autowired
    private final UserRolesRepository userRolesRepository;

    public UserRoles create(LoginEntity loginEntity){
        UserRoles userRole = new UserRoles(loginEntity.getEmail(), "ROLE_USER");
        return userRolesRepository.save(userRole);
    }
}
