# Movie Recommendation System

## Overview
The Movie Recommendation System is a Spring Boot application that allows users to rate movies and receive recommendations based on their preferences. It leverages machine learning using Tribuo for personalized movie suggestions.

## Tech Stack
- **Backend**: Java 17, Spring Boot 3.4.3
- **Database**: PostgreSQL
- **Machine Learning**: Tribuo (for recommendation system)
- **Build Tool**: Maven

## Features
- Users can add ratings for movies.
- Fetch movies and their ratings.
- Fetch user ratings history.
- Recommend movies based on user preferences.

## Installation & Setup
### Prerequisites
- Java 17 installed
- PostgreSQL installed and running
- Maven installed

### Steps to Run
1. Clone the repository:
   ```sh
   git clone https://github.com/cool-rohitsharma/Movie-Recommendation-System-App.git
   cd movie-recommendation
   ```
2. Configure PostgreSQL in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/moviedb
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
3. Build the project:
   ```sh
   mvn clean install
   ```
4. Run the application:
   ```sh
   mvn spring-boot:run
   ```

## API Endpoints
### Movies API
- `GET /movies` - Fetch all movies
- `GET /movies/{id}` - Fetch movie by ID
- `POST /movies` - Add a new movie

### Ratings API
- `GET /ratings` - Fetch all ratings
- `GET /ratings/movie/{movieId}` - Fetch ratings for a movie
- `GET /ratings/user/{userId}` - Fetch ratings given by a user
- `POST /ratings` - Add a new rating

### Users API
- `GET /users` - Fetch all users
- `GET /users/{id}` - Fetch user by ID
- `POST /users` - Add a new user

## Recommendation Logic
- The system collects user ratings and applies collaborative filtering using Tribuo.
- The model is trained to predict movie preferences based on user ratings.
- Recommendations are generated dynamically based on similar user preferences.

## Future Enhancements
- Improve the recommendation algorithm using deep learning.
- Integrate DTO, validation and mapper functions
- Integrate exception handling mechanism
- Add unit and functional tests cases 
- Integrate a frontend for better user experience.
- Implement authentication and user roles.

## Contributing
1. Fork the repository.
2. Create a feature branch (`feature-xyz`).
3. Commit your changes.
4. Push to your branch and create a pull request.
"# Movie-Recommendation-System-App" 
