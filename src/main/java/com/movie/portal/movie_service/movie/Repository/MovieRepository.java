package com.movie.portal.movie_service.movie.Repository;

import com.movie.portal.movie_service.movie.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
