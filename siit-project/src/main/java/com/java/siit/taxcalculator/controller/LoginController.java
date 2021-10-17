package com.java.siit.taxcalculator.controller;


import com.java.siit.taxcalculator.domain.entity.LoginEntity;
import com.java.siit.taxcalculator.domain.entity.business.PfaEntity;
import com.java.siit.taxcalculator.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import com.java.siit.taxcalculator.domain.model.LoginDTO;
import com.java.siit.taxcalculator.config.UserRoles;
import com.java.siit.taxcalculator.domain.model.LoginDTO;
import com.java.siit.taxcalculator.mapper.LoginEntityToLoginDTOMapper;
import com.java.siit.taxcalculator.domain.entity.LoginEntity;
import com.java.siit.taxcalculator.service.LoginService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;


import java.util.List;


@Controller
@RequestMapping
@AllArgsConstructor
public class LoginController {

    @Autowired
    private final LoginService service;



    private final LoginEntityToLoginDTOMapper loginEntityToLoginDTOMapper;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

//    protected LoginEntity createAdmin(){
//        long id = 0;
//        LoginEntity admin1 = new LoginEntity(id,"admin1@admin.com","admifirstname","adminlastname","admin","no", true, UserRoles.ADMIN );
//
//    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/index")
    public String index() {
        return "/index";
    }


//    @GetMapping("/users")
//    public String getAllUsersWithBusiness(Model model) {
//        List<LoginEntity> users = service.getAllUsersWithBusiness();
//        model.addAttribute("users", users);
//        return "users";
//    }


    @GetMapping("/user/delete/{id}")
    public RedirectView delete(@PathVariable("id") long id) {
        service.delete(id);
        return new RedirectView("http://localhost:8080/users");
    }


    @RequestMapping("/user/edit/{id}")
    public ModelAndView editUser(@PathVariable(name = "id") int id) throws NotFoundException {

        ModelAndView mav = new ModelAndView("edit_user");
        LoginDTO login = service.findById(id);
        mav.addObject("login", login);
        return mav;
    }

            @GetMapping("/register")
            public ModelAndView register () {

                ModelAndView modelAndView = new ModelAndView();
                LoginEntity loginEntity = new LoginEntity();
                modelAndView.addObject("loginEntity", loginEntity);
                modelAndView.setViewName("register");
                return modelAndView;
            }


    @PutMapping(value = "/user/update")
    public RedirectView editUser(@ModelAttribute("login") LoginDTO loginDTO) throws NotFoundException {

        service.update(loginDTO);
        return new RedirectView("http://localhost:8080/user/edit/" + Long.toString(loginDTO.getId()));

    }

    @PostMapping("/register")
    public ModelAndView create(LoginEntity loginEntity, BindingResult bindingResult, ModelMap modelMap) {

        loginEntity.setEnabled("true");
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "Please correct the errors in form!");
            modelMap.addAttribute("bindingResult", bindingResult);
        } else {

            LoginEntity loginEntity1 = service.create(loginEntity);


            modelAndView.addObject("successMessage", "User is registered successfully!");
        }
        modelAndView.addObject("loginEntity", new LoginEntity());

        modelAndView.setViewName("register");
        return modelAndView;
    }
}
