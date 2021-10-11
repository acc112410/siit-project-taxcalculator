package com.java.siit.taxcalculator.controller;


import com.java.siit.taxcalculator.domain.entity.LoginEntity;
import com.java.siit.taxcalculator.domain.entity.ReviewEntity;
import com.java.siit.taxcalculator.domain.model.ReviewDTO;
import com.java.siit.taxcalculator.mapper.LoginEntityToLoginDTOMapper;
import com.java.siit.taxcalculator.repository.LoginRepository;
import com.java.siit.taxcalculator.service.ReviewService;
import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.Authenticator;
import java.util.List;

@Controller
@RequestMapping
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewService service;
    private final LoginRepository loginRepository;
    private final LoginEntityToLoginDTOMapper loginEntityToLoginDTOMapper;

    @GetMapping("/review")
    public ModelAndView getAllReviews(){
        ModelAndView modelAndView = new ModelAndView();
        ReviewEntity reviewEntity = new ReviewEntity();
        modelAndView.addObject("reviewEntity", reviewEntity);
        modelAndView.setViewName("review");
        return modelAndView;
    }

    @PostMapping("/review/create")
    public ReviewEntity createReview(ReviewDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginEntity loginEntity = loginRepository.getByEmail(auth.getName());
        dto.setLoginDTO(loginEntityToLoginDTOMapper.convert(loginEntity));
        dto.setEmail(auth.getName());
        dto.setName(loginEntity.getLastName() + " " + loginEntity.getFirstName());
        return reviewService.createReview(dto);
    }

}
