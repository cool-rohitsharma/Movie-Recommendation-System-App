package com.movierecommendationapp.movierecommendation.controller;

import com.movierecommendationapp.movierecommendation.domain.model.Movie;
import com.movierecommendationapp.movierecommendation.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/{userId}")
    public List<Movie> getRecommendedMovies(@PathVariable Long userId) {
        return recommendationService.getRecommendedMovies(userId);
    }
}
