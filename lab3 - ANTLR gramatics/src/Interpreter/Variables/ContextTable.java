package Interpreter.Variables;

import java.util.HashMap;
import java.util.Map;

public class ContextTable {
    public static Map<String, Object> variables = new HashMap<>();

    public static void addVariable(String varName, int value) {
        variables.put(varName, value);
    }

    public static void addVariable(String varName, boolean value) {
        variables.put(varName, value);
    }

    public static Object getVariableValue(String varName) {
        if (variables.get(varName) == null)
            return null;
        else return variables.get(varName);
    }

    public static void reset() {
        variables.clear();
    }
}
