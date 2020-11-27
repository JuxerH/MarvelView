package com.finalwork.marvelview.api.exception;

public class MarvelException extends Exception {
    private final int mCode;

    public MarvelException(int code, String message) {
        super(message);
        mCode = code;
    }
}
