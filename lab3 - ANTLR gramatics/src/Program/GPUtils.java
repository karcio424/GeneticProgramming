package Program;

import Interpreter.GPprojectLexer;
import Interpreter.GPprojectParser;
import Interpreter.Variables.AntlrProgram;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GPUtils {
    public static int max = 10;
    public static int min = 1;
    public static int expressionNumOfOperators = 0;
    public static int numberOfStatements = 10;

    public static String generateRandomProgram(int length, List < String > list, int min_r, int max_r) {
        min = min_r;
        max = max_r;
        numberOfStatements = length;
        return generateRandomStatement(length, list, 1, 5);
    }

    private static String generateRandomStatement(int length, List < String > variableList, int from, int to) {
        if (numberOfStatements <= 0 || length <= 0) {
            return "";
        }

        String statement = "";
        int randomRule = generateRandomNumber(from, to);
        int randomVar = generateRandomNumber(0, variableList.size() - 1);

        switch (randomRule) {
            case 1 -> statement = generateRandomLoopStatement(length - 1, variableList);
            case 2 -> statement = generateRandomOutputStatement(variableList);
            //            case 3 -> statement = generateRandomBlockStatement(length - 1, variableList);
            case 3, 4 -> statement = generateRandomAssignmentStatement(length - 1, variableList.get(randomVar), variableList);
            case 5 -> statement = generateRandomConditionalStatement(length - 1, variableList);
        }

        numberOfStatements--;
        return statement + generateRandomStatement(length - 1, variableList, 1, 5);
    }

    private static String generateRandomOutputStatement(List < String > variableList) {
        int randomVar = generateRandomNumber(0, variableList.size() - 1);
        //        String programString = "output(" + variableList.get(randomVar) + ");\n";
        //        String crossoverString = "1";
        //        for (int i=1; i<programString.length(); i++){
        //            crossoverString += '0';
        //        }
        //        String mutationString = "1";
        //        for (int i=1; i<programString.length(); i++){
        //            mutationString += '0';
        //        }
        //        List stringList = new ArrayList(3);
        //        stringList.add(programString);
        //        stringList.add(crossoverString);
        //        stringList.add(mutationString);
        //
        //        return stringList;
        return "output(" + variableList.get(randomVar) + ");\n";
    }

    private static String generateRandomConditionalStatement(int length, List < String > variableList) {
        return "if (" + generateRandomExpression(variableList) + ") " +
                generateRandomBlockStatement(length, variableList, 5) +
                " else" + generateRandomBlockStatement(length, variableList, 5);
    }

    private static String generateRandomLoopStatement(int length, List < String > variableList) {
        return "loop(" + generateRandomExpression(variableList) + ")" +
                generateRandomBlockStatement(length, variableList, 1);
    }

    private static String generateRandomBlockStatement(int length, List < String > variableList, int instruction) {
        //        if (length <= 0 || numberOfStatements <= 0) {
        //            return "";
        //        }
        //        int numberStatementsInside = generateRandomNumber(1,1);
        //        numberOfStatements--; // Decrement the number of remaining statements
        //        return "{\n" + generateRandomStatement(numberStatementsInside, variableList, 2, 4) + "}\n" +
        //                generateRandomBlockStatement(length - 1, variableList, instruction);

        int numberStatementsInside = generateRandomNumber(1, 1);
        if (numberOfStatements <= 0) {
            numberOfStatements -= numberOfStatements - 1;
            return "{\n" + generateRandomStatement(numberStatementsInside, variableList, 2, 4) + "}\n";
            //            System.out.println("NIE MAAAAAA");
            //            return generateRandomAssignmentStatement(length,variableList.get(randomVar), variableList);
        }
        //        if (numberStatementsInside>numberOfStatements){
        //            numberOfStatements+=1;
        //        }
        //        numberOfStatements-=numberStatementsInside;
        if (instruction == 1) {
            return "{\n" + generateRandomStatement(numberStatementsInside, variableList, 2, 4) + "}\n";
        }

        return "{\n" + generateRandomStatement(numberStatementsInside, variableList, 2, 4) + "}\n";
    }

    private static String generateRandomAssignmentStatement(int length, String
            var, List < String > variableList) {
        return var +"=" + generateRandomExpression(variableList) + ";\n";
    }

    private static String generateRandomExpression(List < String > variableList) {
        int randomRule = generateRandomNumber(1, 10);
        switch (randomRule) {
            case 1 -> {
                // ID
                int randomVar = generateRandomNumber(0, variableList.size() - 1);
                return variableList.get(randomVar);
            }
            case 2, 8, 9 -> {
                // INT
                return String.valueOf(generateRandomNumber(0, max));
            }
            case 3 -> {
                // BOOL
                return generateRandomNumber(0, 1) == 0 ? "true" : "false";
            }
            case 4, 10 -> {
                //input
                return "input";
            }
            case 5, 6, 7 -> {
                // expression
                expressionNumOfOperators = generateRandomNumber(1, 1);
                //                System.out.println("ILE: " + expressionNumOfOperators);
                return generateRandomLogicTerm(variableList);
                //     + generateRandomExpressionTail(variableList);
                //NA RAZIE NIE JEST TO POTRZEBNE
            }
        }

        return "";
    }

    private static String generateRandomExpressionTail(List < String > variableList) {
        int randomRule = generateRandomNumber(0, 1);
        switch (randomRule) {
            case 0 -> {
                // Nothing
                return "";
            }
            case 1 -> {
                // ('&&' | '||') logicTerm
                if (expressionNumOfOperators > 0) {
                    return " " + generateRandomLogicalOperator() + " " +
                            generateRandomLogicTerm(variableList) +
                            generateRandomExpressionTail(variableList);
                } else {
                    return "";
                }
            }
        }

        return "";
    }

    private static String generateRandomLogicalOperator() {
        int randomOperator = generateRandomNumber(0, 1);
        expressionNumOfOperators--;
        return randomOperator == 0 ? "&&" : "||";
    }

    private static String generateRandomLogicTerm(List < String > variableList) {
        return generateRandomArithmeticExpression(variableList) + generateRandomLogicTermTail(variableList);
    }

    private static String generateRandomLogicTermTail(List < String > variableList) {
        int randomRule = generateRandomNumber(0, 1);
        switch (randomRule) {
            case 0 -> {
                // Nothing
                return "";
            }
            case 1 -> {
                if (expressionNumOfOperators > 0) {
                    // ('<' | '>' | '==' | '!=' | '<=' | '>=') arithmeticExpression
                    return " " + generateRandomComparisonOperator() + " " +
                            generateRandomArithmeticExpression(variableList) +
                            generateRandomLogicTermTail(variableList);
                } else {
                    return "";
                }
            }
        }

        return "";
    }

    private static String generateRandomComparisonOperator() {
        int randomOperator = generateRandomNumber(0, 5);
        expressionNumOfOperators--;
        switch (randomOperator) {
            case 0 -> {
                return "<";
            }
            case 1 -> {
                return ">";
            }
            case 2 -> {
                return "==";
            }
            case 3 -> {
                return "!=";
            }
            case 4 -> {
                return "<=";
            }
            case 5 -> {
                return ">=";
            }
        }

        return "";
    }

    private static String generateRandomArithmeticExpression(List < String > variableList) {
        return generateRandomTerm(variableList) + generateRandomArithmeticExpressionTail(variableList);
    }

    private static String generateRandomArithmeticExpressionTail(List < String > variableList) {
        int randomRule = generateRandomNumber(0, 1);
        switch (randomRule) {
            case 0 -> {
                // Nothing
                return "";
            }
            case 1 -> {
                // ('+' | '-') term
                if (expressionNumOfOperators > 0) {
                    return " " + generateRandomArithmeticOperator() + " " +
                            generateRandomTerm(variableList) +
                            generateRandomArithmeticExpressionTail(variableList);
                } else {
                    return "";
                }
            }
        }

        return "";
    }

    private static String generateRandomArithmeticOperator() {
        int randomOperator = generateRandomNumber(0, 1);
        expressionNumOfOperators--;
        return randomOperator == 0 ? "+" : "-";
    }

    private static String generateRandomTerm(List < String > variableList) {
        return generateRandomFactor(variableList) + generateRandomTermTail(variableList);
    }

    private static String generateRandomTermTail(List < String > variableList) {
        int randomRule = generateRandomNumber(0, 1);
        switch (randomRule) {
            case 0 -> {
                // Nothing
                return "";
            }
            case 1 -> {
                if (expressionNumOfOperators > 0) {
                    // ('*' | '/' | '%') factor
                    return " " + generateRandomMultiplicativeOperator() + " " +
                            generateRandomFactor(variableList) +
                            generateRandomTermTail(variableList);
                } else {
                    return "";
                }
            }
        }

        return "";
    }

    private static String generateRandomMultiplicativeOperator() {
        int randomOperator = generateRandomNumber(0, 2);
        expressionNumOfOperators--;
        switch (randomOperator) {
            case 0, 2 -> {
                return "*";
            }

            case 1 -> {
                return "/";
            }

            //            case 2 -> {
            //                return "%";
            //            }
        }

        return "";
    }

    private static String generateRandomFactor(List < String > variableList) {
        int randomRule = generateRandomNumber(0, 2); //NA RAZIE ZMIENIONE DLA UPROSZCZENIA
        switch (randomRule) {
            case 0 -> {
                // ID
                int randomVar = generateRandomNumber(0, variableList.size() - 1);
                return variableList.get(randomVar);
            }

            case 1, 2 -> {
                // INT
                return String.valueOf(generateRandomNumber(0, max));
            }

            case 3 -> {
                // '(' expression ')'
                return "(" + generateRandomExpression(variableList) + ")";
            }

            case 4 -> {
                // '-' factor
                expressionNumOfOperators--;
                return "-" + generateRandomFactor(variableList);
            }
        }

        return "";
    }

    private static ParseTree parseText(String text) {
        GPprojectLexer lexer = new GPprojectLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GPprojectParser parser = new GPprojectParser(tokens);
        System.out.println(text);

        return parser.program();
    }

    private static int generateRandomNumber() {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    private static int generateRandomNumber(int min_r, int max_r) {
        return min_r + (int)(Math.random() * ((max_r - min_r) + 1));
    }

    private static String generateRandomVariableName() {
        return "var" + generateRandomNumber();
    }

    private static double mutationProbability = 0.3;
    private static double crossoverProbability = 1 - mutationProbability;

    public static List<String> crossover(String parent1, String parent2) {
        String parent1Text = parent1;
        String parent2Text = parent2;

        int crossoverPoint1 = findCrossoverPoint(parent1Text);
        int crossoverPoint2 = findCrossoverPoint(parent2Text);

        String crossedText1 = parent1Text.substring(0, crossoverPoint1) + parent2Text.substring(crossoverPoint2);
        String crossedText2 = parent2Text.substring(0, crossoverPoint2) + parent1Text.substring(crossoverPoint1);

        List<String> crossedList = new ArrayList<>(2);
        crossedList.add(crossedText1);
        crossedList.add(crossedText2);

        return crossedList;
    }

    private static int findCrossoverPoint(String programText) {
        return new GramaticsAnalyzer(programText).analyze().getRandomCrossoverPoint();
    }

    public static String mutate(String program, List < String > variableList) {
        String programText = program;

        int mutationPoint = findMutationPoint(programText);

        String mutatedText = generateRandomProgram(1, variableList, 1, max);

        String mutatedProgramText = programText.substring(0, mutationPoint) +
                mutatedText +
                programText.substring(mutationPoint);

        return mutatedProgramText;
    }

    private static int findMutationPoint(String programText) {
        return new GramaticsAnalyzer(programText).analyze().getRandomMutationPoint();
    }

    public static List < String > generateNextGeneration(List < String > currentGeneration, List < String > variableList) {
        List < String > newGeneration = new ArrayList < > ();

        for (int i = 0; i < currentGeneration.size(); i++) {
            int whichOperation = generateRandomNumber(1, 2);
            if (whichOperation == 1) { // Mutacja
                String program1 = currentGeneration.get(i);

                String mutatedProgram = mutate(program1, variableList);
                newGeneration.add(mutatedProgram);
            } else { // Krzyżowanie
                if (i + 1 < currentGeneration.size() - 1) {
                    String program1 = currentGeneration.get(i);
                    String program2 = currentGeneration.get(i + 1);

                    List<String> crossedProgram = crossover(program1, program2);
                    String crossedProgram1 = crossedProgram.get(0);
                    crossedProgram.remove(0);
                    String crossedProgram2 = crossedProgram.get(0);
                    crossedProgram.remove(0);
                    newGeneration.add(crossedProgram1);
                    newGeneration.add(crossedProgram2);
                    i++;
                } else {
                    newGeneration.add(currentGeneration.get(i));
                }
            }
        }

        return newGeneration;
    }

    public static void main(String[] args) {
        Integer[] inputData = {
                1,
                2,
                3
        };
        Integer[] outputData = {
                2,
                4,
                6
        };

        List < Integer > inputList = Arrays.asList(inputData);
        List < Integer > outputList = Arrays.asList(outputData);
        List < String > first = Arrays.asList("var1", "var2", "var3");
        List < String > second = Arrays.asList("var4", "var5", "var6");

        ParseTree randomProgram = parseText(generateRandomProgram(10, first, 1, 100));
        ParseTree anotherRandomProgram = parseText(generateRandomProgram(10, second, 1, 100));
    }
}