package com.movie.portal.movie_service.utility;

import com.movie.portal.movie_service.movie.DTO.MovieDTO;
import com.movie.portal.movie_service.movie.Entity.Movie;

/**
 * Utility class for converting between {@link Movie} entities and {@link MovieDTO} data transfer objects.
 */
public class MovieMapper {

    public static MovieDTO convertToMovieDTO(Movie movie) {
        return new MovieDTO.Builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .trailerUrl(movie.getTrailerUrl())
                .posterUrl(movie.getPosterUrl())
                .genre(movie.getGenre())
                .releaseYear(movie.getReleaseYear())
                .build();
    }

    public static Movie convertToMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setPosterUrl(movieDTO.getPosterUrl());
        movie.setReleaseYear(movieDTO.getReleaseYear());
        movie.setTrailerUrl(movieDTO.getTrailerUrl());
        return movie;
    }
}
