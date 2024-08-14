package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorCode {
    UNCCATEGORIZEd_EXCCEPTION(999,"UNCCATEGORIZED EXCCEPTION", HttpStatus.SERVICE_UNAVAILABLE),
    UNCCATEGORIZEd(1007,"UNCCATEGORIZED",HttpStatus.BAD_REQUEST),
    USER_NOT_EXITS(1006,"Users not exits",HttpStatus.NOT_FOUND),
    INVAILD_KEY(1005,"invaild key",HttpStatus.BAD_REQUEST),
    PASSWORD_INVAILD2(1004,"password null",HttpStatus.BAD_REQUEST),
    PASSWORD_INVAILD1(1003,"password must be at least 3 characters",HttpStatus.BAD_REQUEST),
    USERNAME_INVAILD(1002,"user null",HttpStatus.BAD_REQUEST),
    USERID_EXITS(1006,"Users id exits",HttpStatus.BAD_REQUEST),
    USER_EXITS(1001,"Users exits",HttpStatus.UNAUTHORIZED);

    private int code;
    private String message;
    private HttpStatus httpStatus;
}
