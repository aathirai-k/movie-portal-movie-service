package com.movie.portal.movie_service.movie.Service;

import com.movie.portal.movie_service.movie.DTO.MovieDTO;
import com.movie.portal.movie_service.movie.Exception.MovieServiceException;

import java.util.List;

/**
 * Service interface for managing Movie data.
 */
public interface MovieService {
    /**
     * Adds a new movie to the database.
     *
     * @param movieDTO the movie data to add
     * @return the added movie data
     * @throws IllegalArgumentException if movieDTO is null
     * @throws MovieServiceException if there is a database or unexpected error
     */
    MovieDTO addMovie(MovieDTO movieDTO);

    /**
     * Updates an existing movie in the database.
     *
     * @param movieDTO the movie data with updated values
     * @return the updated movie data
     * @throws IllegalArgumentException if movieDTO is null
     * @throws MovieServiceException if the movie is not found or if a database/unexpected error occurs
     */
    MovieDTO updateMovie(MovieDTO movieDTO);

    /**
     * Deletes a movie from the database by ID.
     *
     * @param id the ID of the movie to delete
     * @throws IllegalArgumentException if id is less than or equal to 0
     * @throws MovieServiceException if the movie does not exist or if a database/unexpected error occurs
     */
    void deleteMovie(long id);

    /**
     * Retrieves a movie by its ID.
     *
     * @param id the ID of the movie to retrieve
     * @return the movie data if found
     * @throws IllegalArgumentException if id is less than or equal to 0
     * @throws MovieServiceException if the movie is not found or if a database/unexpected error occurs
     */
    MovieDTO getMovieByID(long id);

    /**
     * Retrieves all movies from the database.
     *
     * @return a list of all movies
     * @throws MovieServiceException if a database or unexpected error occurs
     */
    List<MovieDTO> getAllMovieDetails();

}
