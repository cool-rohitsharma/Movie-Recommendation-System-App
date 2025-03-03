package com.movierecommendationapp.movierecommendation.repository;

import com.movierecommendationapp.movierecommendation.domain.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<Users, Long> {
}
