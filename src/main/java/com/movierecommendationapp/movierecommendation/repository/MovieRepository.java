package com.movierecommendationapp.movierecommendation.repository;

import com.movierecommendationapp.movierecommendation.domain.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository  extends JpaRepository<Movie, Long> {
}
