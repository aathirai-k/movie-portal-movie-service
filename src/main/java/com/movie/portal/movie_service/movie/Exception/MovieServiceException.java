package com.movie.portal.movie_service.movie.Exception;

/**
 * Custom exception class for handling movie service-specific errors.
 * <p>
 * This exception is thrown when an error occurs during any movie-related
 * operation in the service layer. It extends {@link RuntimeException},
 * allowing it to be thrown without being explicitly declared.
 */
public class MovieServiceException extends RuntimeException{
    public MovieServiceException(String message) {
        super(message);
    }

    public MovieServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
