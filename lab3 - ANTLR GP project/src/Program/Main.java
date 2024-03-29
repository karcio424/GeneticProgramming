package Program;

import Interpreter.GPprojectLexer;
import Interpreter.GPprojectParser;
import Interpreter.Variables.AntlrProgram;
import Interpreter.Variables.ContextTable;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        int minLength = 4;
        int maxLength = 6;
        int programLength = generateRandomNumber(minLength, maxLength);

        List<String> variableList = new ArrayList<>(generateRandomVariableArray(minLength, maxLength));
        System.out.println("GENERATED VARS:" + variableList);

        String randomProgram = generateRandomProgram(programLength, variableList);
        System.out.println("Random Program: " + programLength + '\n' + randomProgram);
        Object result = executeProgram(randomProgram);

        System.out.println("Program result: " + result);
    }

    public static Set<String> generateRandomVariableArray(int min, int max) {
        Set<String> variableSet = new HashSet<>();
        int arraySize = generateRandomNumber(min, max);
        for (int i = 0; i < arraySize; i++) {
            String variable = generateRandomVariableName();
            variableSet.add(variable);
        }
        return variableSet;
    }

    private static Object executeProgram(String program) {
        GPprojectLexer lexer = new GPprojectLexer(CharStreams.fromString(program));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GPprojectParser parser = new GPprojectParser(tokens);

        try {
            ParseTree tree = parser.program();
            try (FileWriter fileWriter = new FileWriter("target/input.txt")) {
                fileWriter.write(program);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to save program to file.");
            }
            AntlrProgram programVisitor = new AntlrProgram(100);
            programVisitor.visit(tree);
            System.out.println("PROGRAM FAILED?:" + AntlrProgram.didProgramFail);
            System.out.println(ContextTable.variables);
            return AntlrProgram.programOutput;
        } catch (RuntimeException e) {
            return null; // Program zawiera błąd składniowy
        }
    }

    public static String generateRandomProgram(int length, List<String> list) {

        return generateRandomStatement(length, list);
    }

    private static String generateRandomStatement(int length, List<String> variableList) {
        if (length <= 0) {
            return "";
        }

        String statement = "";
        int randomRule = generateRandomNumber(1, 4);
        int randomVar = generateRandomNumber(0, variableList.size() - 1);

        switch (randomRule) {
            case 1 -> statement = generateRandomLoopStatement(variableList.get(randomVar));
            case 2 -> statement = generateRandomConditionalStatement(variableList.get(randomVar));
            case 3 -> statement = generateRandomBlockStatement(variableList.get(randomVar));
            case 4 -> statement = generateRandomAssignmentStatement(variableList.get(randomVar));
        }

        return statement + generateRandomStatement(length - 1, variableList);
    }

    private static String generateRandomLoopStatement(String var) {
        return "loop(" + var + ") {" + var + "=" + generateRandomNumber(1, 100) + ";}\n";
    }

    private static String generateRandomConditionalStatement(String var) {
        return "if (" + var + ") " +
                "{" + var + "=" + generateRandomNumber(1, 100) + ";} " +
                "else " +
                "{" + var + "=" + generateRandomNumber(1, 100) + ";}\n";
    }

    private static String generateRandomBlockStatement(String var) {
        return "{" + var + "=" + generateRandomNumber(1, 100) + ";}\n";
    }

    private static String generateRandomAssignmentStatement(String var) {
        return var + "=" + generateRandomNumber(1, 100) + ";\n";
    }

    private static int generateRandomNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private static String generateRandomVariableName() {
        return "var" + generateRandomNumber(1, 100);
    }
}
