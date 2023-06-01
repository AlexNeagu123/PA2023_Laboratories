package com.ro.api.exceptions;

public class OptimizationException extends RuntimeException {
    public OptimizationException(String cause) {
        super("Couldn't compute the Vertex Separator because there are not enough games registered. Cause: " + cause);
    }
}
