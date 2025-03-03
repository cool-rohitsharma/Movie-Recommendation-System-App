package com.movierecommendationapp.movierecommendation.service;

import com.movierecommendationapp.movierecommendation.domain.model.Rating;
import com.movierecommendationapp.movierecommendation.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public List<Rating> getRatingsByMovieId(Long movieId) {
        return ratingRepository.findByMovieId(movieId);
    }

    public List<Rating> getRatingsByUserId(Long userId) {
        return ratingRepository.findByUserId(userId);
    }

    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }
}
