package solvd.laba.ermakovich.ha.domain.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthException extends AuthenticationException {

    public AuthException(String message) {
        super(message);
    }

}
