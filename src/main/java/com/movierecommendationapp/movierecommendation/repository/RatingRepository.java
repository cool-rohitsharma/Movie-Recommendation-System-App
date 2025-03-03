package com.movierecommendationapp.movierecommendation.repository;

import com.movierecommendationapp.movierecommendation.domain.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository  extends JpaRepository<Rating, Long> {
    List<Rating> findByUserId(Long userId);
    List<Rating> findByMovieId(Long movieId);
}
