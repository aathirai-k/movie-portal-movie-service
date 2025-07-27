package com.movie.portal.movie_service.movie.controller;

import com.movie.portal.movie_service.movie.DTO.MovieDTO;
import com.movie.portal.movie_service.movie.Exception.MovieServiceException;
import com.movie.portal.movie_service.movie.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    /**
     * Adds a new movie to the system.
     *
     * @param movieDTO the movie data transfer object containing movie details
     * @return the added movie details wrapped in a {@link ResponseEntity}
     * @throws AccessDeniedException if the user does not have the 'ROLE_ADMIN' authority
     */
    @PostMapping("/addMovie")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieDTO> addMovie(@RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok(movieService.addMovie(movieDTO));
    }

    /**
     * Retrieves a movie by its ID.
     *
     * @param id the ID of the movie to retrieve
     * @return the movie details wrapped in a {@link ResponseEntity}
     * @throws MovieServiceException if the movie with the specified ID does not exist
     */
    @GetMapping("/getByMovieId/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieByID(id));
    }

    /**
     * Retrieves all movie details from the system.
     *
     * @return a list of all movies wrapped in a {@link ResponseEntity}
     */
    @PostMapping("/allMovie")
    public ResponseEntity<List<MovieDTO>> getAllMovieDetails() {
        return ResponseEntity.ok(movieService.getAllMovieDetails());
    }

    /**
     * Updates an existing movie in the system.
     *
     * @param movieDTO the movie data transfer object containing updated movie details
     * @return the updated movie details wrapped in a {@link ResponseEntity}
     * @throws AccessDeniedException if the user does not have the 'ROLE_ADMIN' authority
     */
    @PutMapping("/updateMovie")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieDTO> updateMovie(@RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok(movieService.updateMovie(movieDTO));
    }

    /**
     * Deletes a movie by its ID.
     *
     * @param id the ID of the movie to delete
     * @return a success message wrapped in a {@link ResponseEntity}
     * @throws AccessDeniedException if the user does not have the 'ROLE_ADMIN' authority
     * @throws MovieServiceException if the movie with the specified ID does not exist
     */
    @DeleteMapping("/deleteByMovieId/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie deleted successfully!");
    }

}
