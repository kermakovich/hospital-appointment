package solvd.laba.ermakovich.ha.domain.exception;

public class ResourceDoesNotExistException extends RuntimeException {

    public static final String MESSAGE = "%s with id: %d doesn`t exist";

    public ResourceDoesNotExistException(String resourceName, long id) {
        super(String.format(MESSAGE, resourceName, id));
    }

}
