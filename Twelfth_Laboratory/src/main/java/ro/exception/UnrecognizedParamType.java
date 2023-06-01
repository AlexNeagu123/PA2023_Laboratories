package ro.exception;

import java.lang.reflect.Parameter;

public class UnrecognizedParamType extends Exception {
    public UnrecognizedParamType(Parameter parameter) {
        super("Parameter type: " + parameter.getType().getName() + " not supported in the @Test methods");
    }
}
