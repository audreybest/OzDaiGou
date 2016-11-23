package com.ozdaigou.exceptions;

public class GetProductPageFailException extends Exception{
    public GetProductPageFailException() {
    }

    public GetProductPageFailException(String message) {
        super(message);
    }

    public GetProductPageFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetProductPageFailException(Throwable cause) {
        super(cause);
    }

    public GetProductPageFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
