package solvd.laba.ermakovich.ha.domain.exception;

public class ResourceNotFoundException extends RuntimeException {

    public static final String MESSAGE = "%s with id: %d doesn`t exist";

    public ResourceNotFoundException(String resourceName, long id) {
        super(String.format(MESSAGE, resourceName, id));
    }

}
