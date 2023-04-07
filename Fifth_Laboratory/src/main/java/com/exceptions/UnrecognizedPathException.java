package com.exceptions;

public class UnrecognizedPathException extends Exception {
    public UnrecognizedPathException(String path) {
        super("This type of file '" + path + "' cannot be recognized..");
    }
}
