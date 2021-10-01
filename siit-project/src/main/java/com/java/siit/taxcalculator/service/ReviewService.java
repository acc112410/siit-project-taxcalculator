package com.java.siit.taxcalculator.service;

import com.java.siit.taxcalculator.domain.entity.ReviewEntity;
import com.java.siit.taxcalculator.domain.model.ReviewDTO;
import com.java.siit.taxcalculator.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;
    private final ReviewRepository reviewRepository;


    public List<ReviewEntity> getAllReviews(){
        return repository.findAll();
    }

    public ReviewEntity createReview(ReviewDTO dto) {
        return reviewRepository.save(ReviewEntity.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .message(dto.getMessage())
                .subject(dto.getSubject())
                .build());
    }
}

