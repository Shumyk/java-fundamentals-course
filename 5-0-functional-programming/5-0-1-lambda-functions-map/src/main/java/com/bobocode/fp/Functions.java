package com.bobocode.fp;

/**
 * An util class that provides a factory method for creating an instance of a {@link FunctionMap} filled with a list
 * of functions.
 * <p>
 *
 * @author Taras Boychuk
 */
public class Functions {
    private Functions() {
    }

    /**
     * A static factory method that creates an integer function map with basic functions:
     * - abs (absolute value)
     * - sgn (signum function)
     * - increment
     * - decrement
     * - square
     *
     * @return an instance of {@link FunctionMap} that contains all listed functions
     */
    public static FunctionMap<Integer, Integer> intFunctionMap() {
        FunctionMap<Integer, Integer> intFunctionMap = new FunctionMap<>();

        intFunctionMap.addFunction("abs", a -> a < 0 ? -a : a);
        intFunctionMap.addFunction("sgn", a -> a == 0 ? 0 : a < 0 ? -1 : 1);
        intFunctionMap.addFunction("increment", a -> a + 1);
        intFunctionMap.addFunction("decrement", a -> a - 1);
        intFunctionMap.addFunction("square", a -> a * a);

        return intFunctionMap;
    }
}
