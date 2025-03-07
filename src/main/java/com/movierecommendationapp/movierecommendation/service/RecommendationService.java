package com.movierecommendationapp.movierecommendation.service;

import com.movierecommendationapp.movierecommendation.domain.model.Movie;
import com.movierecommendationapp.movierecommendation.domain.model.Rating;
import com.movierecommendationapp.movierecommendation.repository.MovieRepository;
import com.movierecommendationapp.movierecommendation.repository.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecommendationService {

    @Autowired MovieRepository movieRepository;

    @Autowired RatingRepository ratingRepository;

    public RecommendationService(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    public List<Movie> getRecommendedMovies(Long userId){
        //Get all rating by the user
        List<Rating> userRatings = ratingRepository.findByUserId(userId);
        log.info("Fetching all Ratings provided by user"+ userRatings);

        if(userRatings.isEmpty()){
            //if user rating is empty return top movies trending globally
            log.info("Fetching top rated movies");
            return getTopRatedMovies();
        }

        //Get Movie IDs rated by this user
        Set<Long> ratedMovieIds = userRatings.stream()
                                             .map(r -> r.getMovie().getMovieId())
                                             .collect(Collectors.toSet());

        //Find other users who rated the same movies
        List<Rating> allRatings = ratingRepository.findAll();
        Map<Long, List<Rating>> userRatingMap = new HashMap<>();

        for(Rating rating: allRatings){
            if(!ratedMovieIds.contains(rating.getMovie().getMovieId())) continue;
            userRatingMap.computeIfAbsent(rating.getUser().getUserId(), k -> new ArrayList<>()).add(rating);
        }

        //Find Similar users
        Map<Long, Double> similarityScores = new HashMap<>();

        for(Map.Entry<Long, List<Rating>> entry: userRatingMap.entrySet()){
            Long otherUserId = entry.getKey();
            if(Objects.equals(otherUserId, userId)) continue;

            double similarity = calculateSimilarity(userRatings, entry.getValue());
            similarityScores.put(otherUserId, similarity);
        }

        //Sort user by Similarity
        List<Long> similarUsers = similarityScores.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(5).toList();

        //Get Top Movie Recommendation
        log.info("Fetching all top Rated movies similar to user preferences"+ userRatings);
        return getMoviesFromSimilarUsers(similarUsers, ratedMovieIds);
    }

    private List<Movie> getMoviesFromSimilarUsers(List<Long> similarUsers, Set<Long> ratedMovieIds) {
        List<Movie> recommendedMovies = new ArrayList<>();

        for(Long userId : similarUsers){
            List<Rating> ratings = ratingRepository.findByUserId(userId);
            for(Rating rating: ratings){
                if(!ratedMovieIds.contains(rating.getMovie().getMovieId()) && rating.getRating() >= 4.0){
                    recommendedMovies.add(movieRepository.findById(rating.getMovie().getMovieId()).orElse(null));
                }
            }
        }
        return recommendedMovies.stream().distinct().limit(5).collect(Collectors.toList());
    }

    private double calculateSimilarity(List<Rating> userRatings, List<Rating> otherUserRatings) {
        //Pearson correlation similarity calculation
        Map<Long, Double> userRatingMap = userRatings.stream()
                .collect(Collectors.toMap(r -> r.getMovie().getMovieId(), Rating :: getRating));
        Map<Long, Double> otherUserRatingMap = otherUserRatings.stream()
                .collect(Collectors.toMap(r -> r.getMovie().getMovieId(), Rating :: getRating));

        List<Double> userValues = new ArrayList<>();
        List<Double> otherValues = new ArrayList<>();

        for(Long movieId: userRatingMap.keySet()){
            if(otherUserRatingMap.containsKey(movieId)){
                userValues.add(userRatingMap.get(movieId));
                otherValues.add(otherUserRatingMap.get(movieId));
            }
        }
        return computePearsonCorrelation(userValues, otherValues);
    }

    private double computePearsonCorrelation(List<Double> x, List<Double> y) {
        int size = x.size();
        if(size == 0) return 0;

        double sumX = x.stream().mapToDouble(Double :: doubleValue).sum();
        double sumY = y.stream().mapToDouble(Double :: doubleValue).sum();
        double sumXY = 0, sumX2 = 0, sumY2 = 0;

        for(int i = 0; i< size; i++){
            sumXY += x.get(i) * y.get(i);
            sumX2 += x.get(i) * x.get(i);
            sumY2 += y.get(i) * y.get(i);
        }
        return (size * sumXY - sumX * sumY)/
                (Math.sqrt(size * sumX2 - sumX * sumX) * Math.sqrt(size * sumY2 - sumY * sumY));
    }

    private List<Movie> getTopRatedMovies() {
        return movieRepository.findAll().stream()
                                        .sorted(Comparator.comparingDouble(this :: getAverageRating).reversed())
                                        .limit(5)
                                        .collect(Collectors.toList());
    }

    private double getAverageRating(Movie movie) {
        List<Rating> ratings = ratingRepository.findByMovieId(movie.getMovieId());
        return ratings.stream().mapToDouble(Rating::getRating).average().orElse(0);
    }
}
