package com.movie.portal.movie_service.movie.Service;

import ch.qos.logback.core.util.StringUtil;
import com.movie.portal.movie_service.movie.DTO.MovieDTO;
import com.movie.portal.movie_service.movie.Entity.Movie;
import com.movie.portal.movie_service.movie.Exception.MovieServiceException;
import com.movie.portal.movie_service.movie.Repository.MovieRepository;
import com.movie.portal.movie_service.utility.MovieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Movie data.
 */
@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;
    private static final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Override
    public MovieDTO addMovie(MovieDTO movieDTO) {
        if (movieDTO == null) {
            throw new IllegalArgumentException("MovieDTO must not be null");
        }
        try {
            log.debug("Adding movie: {}", movieDTO);
            Movie savedMovie = movieRepository.save(MovieMapper.convertToMovie(movieDTO));
            log.info("Movie added successfully");
            return MovieMapper.convertToMovieDTO(savedMovie);
        } catch (DataAccessException e) {
            log.error("Database error while saving movie", e);
            throw new MovieServiceException("Database error while saving movie", e);
        } catch (Exception e) {
            log.error("Unexpected error occurred", e);
            throw new RuntimeException("Unexpected error occurred", e);
        }
    }

    @Override
    public MovieDTO updateMovie(MovieDTO movieDTO) {
        if (movieDTO == null) {
            throw new IllegalArgumentException("MovieDTO must not be null");
        }
        try {
            log.debug("Updating movie: {}", movieDTO);
            Optional<Movie> movie = movieRepository.findById(movieDTO.getId());
            if (movie.isPresent()) {
                Movie retrievedMovie = movie.get();
                updateMovieFromMovieDTO(movieDTO, retrievedMovie);
                Movie updatedMovie = movieRepository.save(retrievedMovie);
                log.info("Movie updated successfully");
                return MovieMapper.convertToMovieDTO(updatedMovie);
            } else {
                log.warn("Movie with ID {} not found", movieDTO.getId());
                throw new MovieServiceException("Movie not found with ID: " + movieDTO.getId());
            }
        } catch (DataAccessException e) {
            log.error("Database error while updating movie", e);
            throw new MovieServiceException("Database error while updating movie", e);
        } catch (MovieServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred", e);
            throw new MovieServiceException("Unexpected error occurred", e);
        }
    }

    @Override
    public void deleteMovie(long id) {
        if (id <= 0) {
            log.warn("Invalid movie ID: {}", id);
            throw new IllegalArgumentException("Movie ID must be greater than 0");
        }

        if (!movieRepository.existsById(id)) {
            log.warn("Movie with ID {} not found for deletion", id);
            throw new MovieServiceException("Movie not found with ID: " + id);
        }

        movieRepository.deleteById(id);
        log.info("Movie with ID {} deleted successfully", id);
    }

    @Override
    public MovieDTO getMovieByID(long id) {
        if (id <= 0) {
            log.warn("Invalid movie ID: {}", id);
            throw new IllegalArgumentException("Movie ID must be greater than 0");
        }
        Optional<Movie> retrievedMovie = movieRepository.findById(id);
        if (retrievedMovie.isPresent()) {
            return MovieMapper.convertToMovieDTO(retrievedMovie.get());
        } else {
            log.warn("Movie with ID {} not found", id);
            throw new MovieServiceException("Movie not found with ID: " + id);
        }
    }

    @Override
    public List<MovieDTO> getAllMovieDetails() {
        log.info("Retrieve all movie details");
        List<Movie> movieList = movieRepository.findAll();
        List<MovieDTO> retreivedMovieList = new ArrayList<>();
        if (!movieList.isEmpty()) {
            retreivedMovieList = movieList.stream().map(movie ->
                    MovieMapper.convertToMovieDTO(movie)).collect(Collectors.toList());
        }
        return retreivedMovieList;
    }

    private Movie updateMovieFromMovieDTO (MovieDTO movieDTO, Movie movie) {
        if (!StringUtil.isNullOrEmpty(movieDTO.getDescription())) {
            movie.setDescription(movieDTO.getDescription());
        }
        if (!StringUtil.isNullOrEmpty(movieDTO.getPosterUrl())) {
            movie.setPosterUrl(movieDTO.getPosterUrl());
        }
        if (!StringUtil.isNullOrEmpty(movieDTO.getGenre())) {
            movie.setGenre(movieDTO.getGenre());
        }
        if (!StringUtil.isNullOrEmpty(movieDTO.getTrailerUrl())) {
            movie.setTrailerUrl(movieDTO.getTrailerUrl());
        }
        if (!StringUtil.isNullOrEmpty(movieDTO.getTitle())) {
            movie.setTitle(movieDTO.getTitle());
        }
        if (movieDTO.getReleaseYear() > 0) {
            movie.setReleaseYear(movieDTO.getReleaseYear());
        }
        return movie;
    }
}
