package com.security.Security.demo.exception;

import org.springframework.security.core.AuthenticationException;

public class NoteNotFoundException extends AuthenticationException {
    public NoteNotFoundException(String msg) {
        super(msg);
    }

    public NoteNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
