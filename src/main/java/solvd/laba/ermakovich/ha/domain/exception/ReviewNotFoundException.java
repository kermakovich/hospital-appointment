package solvd.laba.ermakovich.ha.domain.exception;

public class ReviewNotFoundException extends RuntimeException {

    public static final String MESSAGE = "Review with id: %d doesn`t exist";

    public ReviewNotFoundException(long reviewId) {
        super(String.format(MESSAGE, reviewId));
    }
}
