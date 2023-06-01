package ro.util;

import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import ro.exception.UnrecognizedParamType;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The <tt>ClassAnalysis</tt> class contains a lot of useful static methods involving printing information about a {@link Class}
 * on the screen, invoking a method from a class, checking if @Test annotation is present and generating statistics about
 * the tests that have been run.
 *
 * @author Alex Neagu
 */
@Log4j2
public class ClassAnalysis {
    /**
     * Enumerates all the methods (plus additional information) from a given class
     *
     * @param symbolicClassName  The name of the class
     * @param correspondingClass The {@link Class} object corresponding to the class
     */
    public static void printClassMethods(String symbolicClassName, Class<?> correspondingClass) {
        try {
            System.out.println(symbolicClassName + " class has the following methods");
            for (Method m : correspondingClass.getMethods()) {
                System.out.println("=====================================================");
                System.out.println("Method '" + m.getName() + "'");
                System.out.println("Modifiers: " + Modifier.toString(m.getModifiers()));
                System.out.println("Return Type: " + m.getReturnType());
                System.out.print("Declared Annotations: ");

                for (Annotation annotation : m.getDeclaredAnnotations()) {
                    System.out.print(annotation.annotationType().getName() + " ");
                }
                System.out.println();
                System.out.print("Method Parameters: ");

                for (Parameter parameter : m.getParameters()) {
                    System.out.print("(" + parameter.getType() + " " + parameter.getName() + ") ");
                }
                System.out.println();
            }
        } catch (NoClassDefFoundError cnf) {
            log.error(cnf.getMessage() + " class has not been found, thus method printing failed");
        }
    }

    /**
     * Invoking all the methods annotated with @Test,step by step
     * <p>
     * Only int and String parameter types are supported.
     * <p>
     * The methods do not have to be static
     * <p>
     * Heavily used in <b>Homework</b> implementation
     *
     * @param correspondingClass The {@link Class} object corresponding to the class
     */
    public static void invokeAllTestMethods(Class<?> correspondingClass) {
        System.out.println("=====================================================");
        System.out.println(correspondingClass.getName() + " class with @Test annotation is being analyzed..");
        System.out.println("All the @Test methods are being invoked. Those methods that have parameters are called with mock values");

        int testCount = 0, passedTests = 0;
        for (Method method : correspondingClass.getMethods()) {
            if (!methodContainsTestAnnotation(method)) {
                continue;
            }
            try {
                List<Object> methodArguments = extractMethodArguments(method);
                testCount++;
                if (checkIfGeneralTestPasses(method, methodArguments.toArray())) {
                    passedTests++;
                }
            } catch (UnrecognizedParamType ex) {
                log.error(ex.getMessage());
            }
        }

        System.out.println("Final results: " + passedTests + " passed tests from " + testCount + " number of tests");
    }

    /**
     * Invoking all the methods annotated with @Test, step by step
     * <p>
     * The methods DOES have to be static and WITHOUT parameters of any type.
     * <p>
     * Heavily used in <b>Compulsory</b> implementation
     *
     * @param correspondingClass The {@link Class} object corresponding to the class
     */
    public static void invokeStaticEmptyTestMethods(Class<?> correspondingClass) {
        System.out.println("=====================================================");
        System.out.println("Invoking the @Test static methods, without any parameters..");
        int testCount = 0, passedTests = 0;
        for (Method method : correspondingClass.getMethods()) {
            if (!methodContainsTestAnnotation(method) || !Modifier.isStatic(method.getModifiers()) || !hasNoArguments(method)) {
                continue;
            }
            testCount++;
            if (checkIfStaticEmptyTestPasses(method)) {
                passedTests++;
            }
        }
        System.out.println("Final results: " + passedTests + " passed tests from " + testCount + " number of tests");
    }

    /**
     * Invoking the injected 'printSecretMessage' method
     * <p>
     * The injected method is static by default
     * <p>
     * Heavily used in <b>Bonus</b> implementation
     *
     * @param correspondingClass The {@link Class} object corresponding to the class
     * @see MockInjector
     */
    public static void invokeInjectedMethod(Class<?> correspondingClass) {
        try {
            System.out.println("=====================================================");
            System.out.println("The injected method 'printSecretMessage' is being called..");
            Method method = correspondingClass.getMethod("printSecretMessage", null);
            method.invoke(null);
        } catch (NoSuchMethodException ex) {
            System.out.println("The 'printSecretMessage' method was not injected in class " + correspondingClass.getName());
        } catch (IllegalAccessException | InvocationTargetException ex) {
            log.error("Error when invoking the 'printSecretMessage' injected method");
        }
    }

    public static boolean classContainsTestAnnotation(Class<?> correspondingClass) {
        for (Annotation annotation : correspondingClass.getAnnotations()) {
            if (annotation.annotationType().getName().contains("Test")) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkIfStaticEmptyTestPasses(Method method) {
        try {
            method.invoke(null);
            System.out.printf("Test %s passed! %n", method.getName());
            return true;
        } catch (Throwable ex) {
            System.out.printf("Test %s failed: %s!%n", method.getName(), ex.getCause());
            return false;
        }
    }

    private static List<Object> extractMethodArguments(Method method) throws UnrecognizedParamType {
        List<Object> methodArguments = new ArrayList<>();
        Faker faker = new Faker();

        for (Parameter parameter : method.getParameters()) {
            String paramType = parameter.getType().getName();
            if (paramType.equals("int")) {
                methodArguments.add((int) (Math.random() * 1000));
            } else if (paramType.equals("String")) {
                methodArguments.add(faker.name().name());
            } else {
                throw new UnrecognizedParamType(parameter);
            }
        }
        return methodArguments;
    }

    private static boolean checkIfGeneralTestPasses(Method method, Object[] arguments) {
        try {
            System.out.println("Invoking @Test method " + method.getName() + " with arguments " + Arrays.toString(arguments));
            if (Modifier.isStatic(method.getModifiers())) {
                // static method
                method.invoke(null, arguments);
            } else {
                // non-static method, should create a TargetObject
                Class<?> clazz = method.getDeclaringClass();
                Constructor<?> ctor = clazz.getConstructor();
                Object targetObject = ctor.newInstance();
                method.invoke(targetObject, arguments);
            }
            System.out.println("Test passed!");
            return true;
        } catch (Throwable ex) {
            System.out.println("Test " + method.getName() + " failed with cause: " + ex.getCause() + " " + ex.getClass());
            return false;
        }
    }

    private static boolean hasNoArguments(Method method) {
        return method.getParameters().length == 0;
    }

    private static boolean methodContainsTestAnnotation(Method method) {
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation.annotationType().getName().contains("Test")) {
                return true;
            }
        }
        return false;
    }
}
