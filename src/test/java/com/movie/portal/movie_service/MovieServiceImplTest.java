package com.movie.portal.movie_service;

import com.movie.portal.movie_service.movie.DTO.MovieDTO;
import com.movie.portal.movie_service.movie.Entity.Movie;
import com.movie.portal.movie_service.movie.Exception.MovieServiceException;
import com.movie.portal.movie_service.movie.Repository.MovieRepository;
import com.movie.portal.movie_service.movie.Service.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    private Movie movie;
    private MovieDTO movieDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movie = new Movie(1L, "Title", "Trailer", "Poster", 2025, "Genre", "Description");
        movieDTO = new MovieDTO(1L, "Title", "Description", "Genre", 2025, "Poster", "Trailer");
    }

    @Test
    void addMovie_success() {
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        MovieDTO result = movieService.addMovie(movieDTO);
        assertNotNull(result);
        assertEquals(movieDTO.getTitle(), result.getTitle());
        verify(movieRepository).save(any(Movie.class));
    }

    @Test
    void updateMovie_success() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        MovieDTO updated = movieService.updateMovie(movieDTO);

        assertNotNull(updated);
        assertEquals(movieDTO.getTitle(), updated.getTitle());
    }

    @Test
    void updateMovie_notFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        MovieServiceException ex = assertThrows(MovieServiceException.class, () -> movieService.updateMovie(movieDTO));
        assertEquals("Movie not found with ID: 1", ex.getMessage());
    }

    @Test
    void deleteMovie_success() {
        when(movieRepository.existsById(1L)).thenReturn(true);
        doNothing().when(movieRepository).deleteById(1L);

        assertDoesNotThrow(() -> movieService.deleteMovie(1L));
        verify(movieRepository).deleteById(1L);
    }

    @Test
    void deleteMovie_notFound() {
        when(movieRepository.existsById(1L)).thenReturn(false);
        assertThrows(MovieServiceException.class, () -> movieService.deleteMovie(1L));
    }

    @Test
    void getMovieById_success() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        MovieDTO result = movieService.getMovieByID(1L);
        assertNotNull(result);
        assertEquals(movie.getTitle(), result.getTitle());
    }

    @Test
    void getMovieById_notFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(MovieServiceException.class, () -> movieService.getMovieByID(1L));
    }

    @Test
    void getAllMovieDetails_success() {
        when(movieRepository.findAll()).thenReturn(Arrays.asList(movie));
        List<MovieDTO> movies = movieService.getAllMovieDetails();
        assertFalse(movies.isEmpty());
        assertEquals(1, movies.size());
    }

    @Test
    void addMovie_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> movieService.addMovie(null));
    }

    @Test
    void updateMovie_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> movieService.updateMovie(null));
    }

    @Test
    void deleteMovie_invalidId() {
        assertThrows(IllegalArgumentException.class, () -> movieService.deleteMovie(0));
    }

    @Test
    void getMovieById_invalidId() {
        assertThrows(IllegalArgumentException.class, () -> movieService.getMovieByID(0));
    }
}
