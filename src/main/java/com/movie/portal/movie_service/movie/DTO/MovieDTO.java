package com.movie.portal.movie_service.movie.DTO;

/**
 * Data Transfer Object for representing movie details.
 * Encapsulates the movie attributes used in API communications.
 */
public class MovieDTO {
    private Long id;
    private String title;
    private String description;
    private String genre;
    private int releaseYear;
    private String posterUrl;
    private String trailerUrl;

    public MovieDTO() {}

    public MovieDTO(Builder builder) {
        id = builder.id;
        title = builder.title;
        description = builder.description;
        genre = builder.genre;
        releaseYear = builder.releaseYear;
        posterUrl = builder.posterUrl;
        trailerUrl = builder.trailerUrl;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private String genre;
        private int releaseYear;
        private String posterUrl;
        private String trailerUrl;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder releaseYear(int releaseYear) {
            this.releaseYear = releaseYear;
            return this;
        }

        public Builder posterUrl(String posterUrl) {
            this.posterUrl = posterUrl;
            return this;
        }

        public Builder trailerUrl(String trailerUrl) {
            this.trailerUrl = trailerUrl;
            return this;
        }

        public MovieDTO build() {
            return new MovieDTO(this);
        }
    }
}
