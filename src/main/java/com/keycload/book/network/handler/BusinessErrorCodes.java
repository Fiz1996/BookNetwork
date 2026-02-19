package com.keycload.book.network.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@Getter
public enum BusinessErrorCodes {
    NO_CODE(0,"noCode",HttpStatus.NOT_IMPLEMENTED),
    INCORRECT_REQUEST(300,"password is incorrect",HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_DOES_NOT_MATCH(301,"password does not match",HttpStatus.BAD_REQUEST),
    ACCOUNT_DISABLED(302,"Account disabled",HttpStatus.BAD_REQUEST),
    BAD_CREDENTIALS(302,"Bad credentials",HttpStatus.BAD_REQUEST),
    ACCOUNT_LOCKED(302,"User is locked",HttpStatus.FORBIDDEN);

    private final int code;
    private final String description;
    private final HttpStatus httpStatus;
}
