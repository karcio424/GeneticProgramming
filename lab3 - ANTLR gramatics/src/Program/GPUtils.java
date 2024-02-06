package Program;

import Interpreter.GPprojectLexer;
import Interpreter.GPprojectParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import Interpreter.Variables.AntlrProgram;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class GPUtils {
    public static int max = 10;
    public static int min = 1;
    public static int expressionNumOfOperators = 0;
    public static int numberOfStatements = 10;

    public static String generateRandomProgram(int length, List < String > list, int min_r, int max_r) {
        //        GPprojectLexer lexer = new GPprojectLexer(CharStreams.fromString(generateRandomStatement(length, list)));
        //        GPprojectParser parser = new GPprojectParser(new CommonTokenStream(lexer));
        max = max_r;
        min = min_r;
        numberOfStatements = length;
        return generateRandomStatement(length, list, 1, 5);
        //        return generateRandomStatement(length, list);
    }

    private static String generateRandomStatement(int length, List < String > variableList, int from, int to) {
        if (numberOfStatements<=0 || length<=0) {
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
        //TODO: zastosowanie koncepcji full i grow (albo którejś z nich)
    }

    private static String generateRandomOutputStatement(List < String > variableList) {
        int randomVar = generateRandomNumber(0, variableList.size() - 1);
        return "output(" + variableList.get(randomVar) + ");\n";
    }

    private static String generateRandomConditionalStatement(int length, List < String > variableList) {
        return "if (" + generateRandomExpression(variableList) + ") " +
                generateRandomBlockStatement(length,variableList, 5) +
                " else" + generateRandomBlockStatement(length, variableList, 5);
        //TODO: tutaj jak się odwołuję do generateRandomBlockStatement to powinno być length czy length-1????????????????????
    }

    private static String generateRandomLoopStatement(int length, List < String > variableList) {
        return "loop(" + generateRandomExpression(variableList) + ")" +
                generateRandomBlockStatement(length,variableList, 1);
        //TODO: dodatkowo!!!: jesli jest samo ID to wybor sposrod zmiennych ktore maja wartosci przypisane
    }

    private static String generateRandomBlockStatement(int length, List < String > variableList, int instruction) {
//        if (length <= 0 || numberOfStatements <= 0) {
//            return "";
//        }
//        int numberStatementsInside = generateRandomNumber(1,1);
//        numberOfStatements--; // Decrement the number of remaining statements
//        return "{\n" + generateRandomStatement(numberStatementsInside, variableList, 2, 4) + "}\n" +
//                generateRandomBlockStatement(length - 1, variableList, instruction);

        int numberStatementsInside = generateRandomNumber(1,1);
        if(numberOfStatements<=0){
            numberOfStatements-=numberOfStatements-1;
            return "{\n" + generateRandomStatement(numberStatementsInside, variableList, 2, 4) + "}\n";
//            System.out.println("NIE MAAAAAA");
//            return generateRandomAssignmentStatement(length,variableList.get(randomVar), variableList);
        }
//        if (numberStatementsInside>numberOfStatements){
//            numberOfStatements+=1;
//        }
//        numberOfStatements-=numberStatementsInside;
        if(instruction==1){
            return "{\n" + generateRandomStatement(numberStatementsInside, variableList, 2, 4) + "}\n";
        }
        return "{\n" + generateRandomStatement(numberStatementsInside, variableList, 2, 4) + "}\n";
        //TODO: generateRandomStatement (i tutaj w szczególności pamiętać o tym,
        // żeby nie mogło generować w nieskończoność zagęszczonych instrukcji
    }

    private static String generateRandomAssignmentStatement(int length, String var, List < String > variableList) {
        //        return var + "="+ generateRandomNumber() + ";\n";
        return var +"=" + generateRandomExpression(variableList) + ";\n";
    }

    private static String generateRandomExpression(List<String> variableList) {
        int randomRule = generateRandomNumber(1, 5);
        switch (randomRule) {
            case 1 -> {
                // ID
                int randomVar = generateRandomNumber(0, variableList.size() - 1);
                return variableList.get(randomVar);
            }
            case 2 -> {
                // INT
                return String.valueOf(generateRandomNumber(0, 100));
            }
            case 3 -> {
                // BOOL
                return generateRandomNumber(0, 1) == 0 ? "true" : "false";
            }
            case 4 -> {
                //input
                return "input";
            }
            case 5 -> {
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

    private static String generateRandomExpressionTail(List<String> variableList) {
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
                } else
                    return "";
            }
        }
        return "";
    }

    private static String generateRandomLogicalOperator() {
        int randomOperator = generateRandomNumber(0, 1);
        expressionNumOfOperators--;
        return randomOperator == 0 ? "&&" : "||";
    }

    private static String generateRandomLogicTerm(List<String> variableList) {
        return generateRandomArithmeticExpression(variableList) + generateRandomLogicTermTail(variableList);
    }

    private static String generateRandomLogicTermTail(List<String> variableList) {
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
                } else
                    return "";
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

    private static String generateRandomArithmeticExpression(List<String> variableList) {
        return generateRandomTerm(variableList) + generateRandomArithmeticExpressionTail(variableList);
    }

    private static String generateRandomArithmeticExpressionTail(List<String> variableList) {
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
                } else
                    return "";
            }
        }
        return "";
    }

    private static String generateRandomArithmeticOperator() {
        int randomOperator = generateRandomNumber(0, 1);
        expressionNumOfOperators--;
        return randomOperator == 0 ? "+" : "-";
    }

    private static String generateRandomTerm(List<String> variableList) {
        return generateRandomFactor(variableList) + generateRandomTermTail(variableList);
    }

    private static String generateRandomTermTail(List<String> variableList) {
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
                } else
                    return "";
            }
        }
        return "";
    }

    private static String generateRandomMultiplicativeOperator() {
        int randomOperator = generateRandomNumber(0, 2);
        expressionNumOfOperators--;
        switch (randomOperator) {
            case 0 -> {
                return "*";
            }
            case 1 -> {
                return "/";
            }
            case 2 -> {
                return "%";
            }
        }
        return "";
    }

    private static String generateRandomFactor(List<String> variableList) {
        int randomRule = generateRandomNumber(0, 2); //NA RAZIE ZMIENIONE DLA UPROSZCZENIA
        switch (randomRule) {
            case 0 -> {
                // ID
                int randomVar = generateRandomNumber(0, variableList.size() - 1);
                return variableList.get(randomVar);
            }
            case 1, 2 -> {
                // INT
                return String.valueOf(generateRandomNumber(0, 100));
            }
//            case 2 -> {
//                // BOOL -> na razie nie uzywamy
//                return generateRandomNumber(0, 1) == 0 ? "true" : "false";
//            }
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
        //        GPprojectLexer lexer = new GPprojectLexer(CharStreams.fromString(text));
        //        CommonTokenStream tokens = new CommonTokenStream(lexer);
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

    private static double mutationProbability = 0.1; // Prawdopodobieństwo mutacji

    public static ParseTree crossover(ParseTree parent1, ParseTree parent2) {
        String parent1Text = serialize(parent1);
        String parent2Text = serialize(parent2);

        // Wybierz losowy punkt krzyżowania
        int crossoverPoint1 = findCrossoverPoint(parent1Text);
        int crossoverPoint2 = findCrossoverPoint(parent2Text);

        // Wymień fragmenty programów
        String crossedText = parent1Text.substring(0, crossoverPoint1) +
                parent2Text.substring(crossoverPoint2);

        // Zwróć potomka
        return parseText(crossedText);
    }

    private static int findCrossoverPoint(String programText) {
        int lastSemicolonIndex = -1;
        for (int i = 0; i < programText.length(); i++) {
            if (programText.charAt(i) == ';') {
                lastSemicolonIndex = i;
            }
        }
        // Jeśli nie znaleziono średnika, zwróć losowy punkt
        if (lastSemicolonIndex == -1) {
            return generateRandomNumber(0, programText.length() - 1);
        }
        // W przeciwnym razie, zwróć punkt po ostatnim średniku
        return lastSemicolonIndex + 1;
    }


    public static ParseTree mutate(ParseTree program) {
        String programText = serialize(program);

        // Wybierz losowy punkt mutacji
        int mutationPoint = findMutationPoint(programText);

        // Wygeneruj nowy losowy fragment kodu
        String mutatedText = generateRandomProgram(10, Arrays.asList("var1", "var2", "var3"), 1, 100);

        // Zastąp fragment w programie mutowanym
        String mutatedProgramText = programText.substring(0, mutationPoint) +
                mutatedText +
                programText.substring(mutationPoint);

        // Zwróć zmutowany program
        return parseText(mutatedProgramText);
    }

    private static int findMutationPoint(String programText) {
        // Znajdź ostatni średnik przed losowym punktem mutacji
        int lastSemicolonIndex = -1;
        int mutationPoint = generateRandomNumber(0, programText.length() - 1);
        for (int i = mutationPoint; i >= 0; i--) {
            if (programText.charAt(i) == ';') {
                lastSemicolonIndex = i;
                break;
            }
        }
        // Jeśli nie znaleziono średnika, zwróć losowy punkt
        if (lastSemicolonIndex == -1) {
            return mutationPoint;
        }
        // W przeciwnym razie, zwróć punkt po ostatnim średniku
        return lastSemicolonIndex + 1;
    }




    public static void testProgram(ParseTree program, List < Integer > input, List < Integer > output) {
        AntlrProgram programVisitor = new AntlrProgram(1000);
        programVisitor.visit(program);
        System.out.println("Testing program: " + program.getText());
        System.out.println("Input program: " + input);
        System.out.println("Output program: " + output);
        //TODO: TA FUNKCJA BEDZIE W KLASIE INTERPRETER INTERFACE, więc nie ma co tutaj robić
    }

    public static ParseTree tournamentSelection(List < ParseTree > programs, int tournamentSize) {
        int winnerIndex = -1;
        double bestFitness = Double.MAX_VALUE;

        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = generateRandomNumber(0, programs.size() - 1);
            ParseTree candidate = programs.get(randomIndex);
            double fitness = calculateFitness(candidate);

            if (fitness < bestFitness) {
                bestFitness = fitness;
                winnerIndex = randomIndex;
            }
        }

        return programs.get(winnerIndex);
    }

    private static double calculateFitness(ParseTree program) {
        AntlrProgram programVisitor = new AntlrProgram(1000);
        programVisitor.visit(program);
        // TODO: TO BEDZIE GDZIE INDZIEJ (plik INTERPRETER_INTERFACE)
        return 0.0;
    }

    public static String serialize(ParseTree program) {
        return program.getText();
    }

    public static ParseTree deserialize(String serializedData) {
        return parseText(serializedData);
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

        if (randomProgram != null && anotherRandomProgram != null) {
            System.out.println("_______________________________________-");
            System.out.println("Random Program 1: " + randomProgram.getText());
            testProgram(randomProgram, inputList, outputList);

            System.out.println("_______________________________________-");
            System.out.println("Random Program 2: " + anotherRandomProgram.getText());
            testProgram(anotherRandomProgram, inputList, outputList);

            ParseTree crossedProgram = crossover(randomProgram, anotherRandomProgram);
            System.out.println("_______________________________________-");
            System.out.println("Crossed Program: " + crossedProgram.getText());
            testProgram(crossedProgram, inputList, outputList);

            ParseTree mutatedProgram = mutate(randomProgram);
            System.out.println("_______________________________________-");
            System.out.println("Mutated Program: " + mutatedProgram.getText());
            testProgram(mutatedProgram, inputList, outputList);

            System.out.println("_______________________________________-");
            List < ParseTree > programList = Arrays.asList(randomProgram, anotherRandomProgram, crossedProgram, mutatedProgram);

//            System.out.println("_______________________________________-");
//            // List < ParseTree > nextGeneration = generateNextGeneration(programList);
//
//           // ParseTree selectedProgram = tournamentSelection(nextGeneration, 2);
//            System.out.println("_______________________________________-");
//            System.out.println("Selected Program: " + selectedProgram.getText());
//            testProgram(selectedProgram, inputList, outputList);
        } else {
            System.out.println("Failed to generate initial random programs.");
        }
    }
}