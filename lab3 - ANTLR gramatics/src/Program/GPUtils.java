package Program;

import Interpreter.GPprojectLexer;
import Interpreter.GPprojectParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import Interpreter.Variables.AntlrProgram;

import java.util.List;
import java.util.Arrays;

public class GPUtils {
    public static int max = 10;
    public static int min = 1;

    public static String generateRandomProgram(int length, List<String> list, int min_r, int max_r) {
        //        GPprojectLexer lexer = new GPprojectLexer(CharStreams.fromString(generateRandomStatement(length, list)));
        //        GPprojectParser parser = new GPprojectParser(new CommonTokenStream(lexer));
        max = max_r;
        min = min_r;
        return generateRandomStatement(length, list);
        //        return generateRandomStatement(length, list);
    }

    private static String generateRandomStatement(int length, List<String> variableList) {
        if (length <= 0) {
            return "";
        }

        String statement = "";
        int randomRule = generateRandomNumber(1, 5);
        int randomVar = generateRandomNumber(0, variableList.size() - 1);

        switch (randomRule) {
            case 1 -> statement = generateRandomLoopStatement(length - 1, variableList.get(randomVar), variableList);
            case 2 -> statement = generateRandomConditionalStatement(length - 1, variableList.get(randomVar), variableList);
            case 3 -> statement = generateRandomBlockStatement(length - 1, variableList.get(randomVar));
            case 4 -> statement = generateRandomAssignmentStatement(length - 1, variableList.get(randomVar), variableList);
            case 5 -> statement = generateRandomOutputStatement(variableList);
        }

        return statement + generateRandomStatement(length - 1, variableList);
        //TODO: zastosowanie koncepcji full i grow (albo którejś z nich)
    }

    private static String generateRandomOutputStatement(List<String> variableList) {
        int randomVar = generateRandomNumber(0, variableList.size() - 1);
        return "output(" + variableList.get(randomVar) + ");\n";
    }

    private static String generateRandomConditionalStatement(int length, String
            var, List<String> variableList) {
        String condition =
                var;
        String thenStatement = "{" +
                var + "=" + generateRandomNumber() + ";}";
        String elseStatement = "{" +
                var + "=" + generateRandomNumber() + ";}";

        //        return "if (" + condition + ") " + thenStatement + "else" + elseStatement + "\n";
        return "if (" + generateRandomExpression(variableList) + ") " + generateRandomBlockStatement(length,
                var) + "else" + generateRandomBlockStatement(length,
                var) + "\n";
        //TODO: tutaj jak się odwołuję do generateRandomBlockStatement to powinno być length czy length-1????????????????????
    }

    private static String generateRandomLoopStatement(int length, String
            var, List<String> variableList) {
        //        return "loop(" + var + ") {" + var + "=" + generateRandomNumber() + ";}\n";
        return "loop(" + generateRandomExpression(variableList) + ")" + generateRandomBlockStatement(length,
                var);
        //TODO: dodatkowo!!!: jesli jest samo ID to wybor sposrod zmiennych ktore maja wartosci przypisane
        // .
        // MOŻNA PRZEMYSLEC CZY NA POCZATKU PROGRAMU OD RAZU KAZDEJ ZMIENNEJ NIE PRZYPISAC wartosci (np. 0)
    }

    private static String generateRandomBlockStatement(int length, String
            var) {
        return "{" +
                var + "=" + generateRandomNumber() + ";}\n";
        //TODO: generateRandomStatement (i tutaj w szczególności pamiętać o tym,
        // żeby nie mogło generować w nieskończoność zagęszczonych instrukcji
        // .
        // nie rozumiem obu części tego punktu!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    private static String generateRandomAssignmentStatement(int length, String
            var, List<String> variableList) {
        //        return var + "="+ generateRandomNumber() + ";\n";
        return var + "=" + generateRandomExpression(variableList) + ";\n";
        //TODO: var = generateRandomExpression
    }

    private static String generateRandomExpression(List<String> variableList) {
        int randomRule = generateRandomNumber(1, 4);
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
                // expression
                return generateRandomLogicTerm(variableList) + generateRandomExpressionTail(variableList, 4);
                //TODO: do poprawy ten limit
            }
        }
        return "";
    }

    private static String generateRandomExpressionTail(List<String> variableList, int limit) {
        int randomRule = generateRandomNumber(0, 1);
        switch (randomRule) {
            case 0 -> {
                // Nothing
                return "";
            }
            case 1 -> {
                // ('&&' | '||') logicTerm
                if (limit > 0) {
                    return " " + generateRandomLogicalOperator() + " " + generateRandomLogicTerm(variableList) + generateRandomExpressionTail(variableList, limit--);
                } else
                    return "";
            }
        }
        return "";
    }

    private static String generateRandomLogicalOperator() {
        int randomOperator = generateRandomNumber(0, 1);
        return randomOperator == 0 ? "&&" : "||";
    }

    private static String generateRandomLogicTerm(List<String> variableList) {
        return generateRandomArithmeticExpression(variableList) + generateRandomLogicTermTail(variableList, 1);
    }

    private static String generateRandomLogicTermTail(List<String> variableList, int limit) {
        int randomRule = generateRandomNumber(0, 1);
        switch (randomRule) {
            case 0 -> {
                // Nothing
                return "";
            }
            case 1 -> {
                if (limit > 0) {
                    // ('<' | '>' | '==' | '!=' | '<=' | '>=') arithmeticExpression
                    return " " + generateRandomComparisonOperator() + " " + generateRandomArithmeticExpression(variableList) + generateRandomLogicTermTail(variableList, limit--);
                } else
                    return "";
            }
        }
        return "";
    }

    private static String generateRandomComparisonOperator() {
        int randomOperator = generateRandomNumber(0, 5);
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
        return generateRandomTerm(variableList) + generateRandomArithmeticExpressionTail(variableList, 1);
    }

    private static String generateRandomArithmeticExpressionTail(List<String> variableList, int limit) {
        int randomRule = generateRandomNumber(0, 1);
        switch (randomRule) {
            case 0 -> {
                // Nothing
                return "";
            }
            case 1 -> {
                // ('+' | '-') term
                if (limit > 0) {
                    return " " + generateRandomArithmeticOperator() + " " + generateRandomTerm(variableList) + generateRandomArithmeticExpressionTail(variableList, limit--);
                } else
                    return "";
            }
        }
        return "";
    }

    private static String generateRandomArithmeticOperator() {
        int randomOperator = generateRandomNumber(0, 1);
        return randomOperator == 0 ? "+" : "-";
    }

    private static String generateRandomTerm(List<String> variableList) {
        return generateRandomFactor(variableList) + generateRandomTermTail(variableList, 1);
    }

    private static String generateRandomTermTail(List<String> variableList, int limit) {
        int randomRule = generateRandomNumber(0, 1);
        switch (randomRule) {
            case 0 -> {
                // Nothing
                return "";
            }
            case 1 -> {
                if (limit > 0) {
                    // ('*' | '/' | '%') factor
                    return " " + generateRandomMultiplicativeOperator() + " " + generateRandomFactor(variableList) + generateRandomTermTail(variableList, limit--);
                } else
                    return "";
            }
        }
        return "";
    }

    private static String generateRandomMultiplicativeOperator() {
        int randomOperator = generateRandomNumber(0, 2);
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
        int randomRule = generateRandomNumber(0, 4);
        switch (randomRule) {
            case 0 -> {
                // ID
                int randomVar = generateRandomNumber(0, variableList.size() - 1);
                return variableList.get(randomVar);
            }
            case 1 -> {
                // INT
                return String.valueOf(generateRandomNumber(0, 100));
            }
            case 2 -> {
                // BOOL
                return generateRandomNumber(0, 1) == 0 ? "true" : "false";
            }
            case 3 -> {
                // '(' expression ')'
                return "(" + generateRandomExpression(variableList) + ")";
            }
            case 4 -> {
                // '-' factor
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
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private static int generateRandomNumber(int min_r, int max_r) {
        return min_r + (int) (Math.random() * ((max_r - min_r) + 1));
    }

    private static String generateRandomVariableName() {
        return "var" + generateRandomNumber();
    }

    public static ParseTree crossover(ParseTree program1, ParseTree program2) {
        String programText1 = program1.getText();
        String programText2 = program2.getText();

        int splitIndex1 = findSplitIndex(programText1);
        int splitIndex2 = findSplitIndex(programText2);

        String newProgramText = programText1.substring(0, splitIndex1) + programText2.substring(splitIndex2);

        return parseText(newProgramText);
    }

    private static int findSplitIndex(String programText) {
        int index = generateRandomNumber(1, programText.length() - 1);

        while (index < programText.length() - 1) {
            if (isStatementBoundary(programText, index)) {
                break;
            }
            index++;
        }

        return index;
    }

    private static boolean isStatementBoundary(String programText, int index) {
        return (programText.charAt(index) == ';' && programText.charAt(index + 1) != '}') || (programText.charAt(index) == '}' && programText.charAt(index + 1) != 'e');
    }

    public static ParseTree mutate(ParseTree program) {
        String programText = program.getText();

        int mutationIndex = findMutationIndex(programText);

        //TODO: TUTAJ TAK NIE POWINNO BYĆ
        // generujesz dodatkowo ileś statementów (od 1 do 10) do tego co już jest.......
        String mutatedProgramText = programText.substring(0, mutationIndex);
        int statementLength = generateRandomNumber(1, 10);
        mutatedProgramText += generateRandomStatement(statementLength, Arrays.asList("var1", "var2", "var3"));
        mutatedProgramText += programText.substring(mutationIndex);

        return parseText(mutatedProgramText);
    }

    private static int findMutationIndex(String programText) {
        int index = generateRandomNumber(1, programText.length() - 1);

        while (index < programText.length() - 1) {
            if (isStatementBoundary(programText, index)) {
                break;
            }
            index++;
        }

        return index;
    }

    public static void testProgram(ParseTree program, List<Integer> input, List<Integer> output) {
        AntlrProgram programVisitor = new AntlrProgram(1000);
        programVisitor.visit(program);
        System.out.println("Testing program: " + program.getText());
        System.out.println("Input program: " + input);
        System.out.println("Output program: " + output);
        //TODO: TA FUNKCJA BEDZIE W KLASIE INTERPRETER INTERFACE, więc nie ma co tutaj robić
    }

    public static ParseTree tournamentSelection(List<ParseTree> programs, int tournamentSize) {
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

        List<Integer> inputList = Arrays.asList(inputData);
        List<Integer> outputList = Arrays.asList(outputData);
        List<String> first = Arrays.asList("var1", "var2", "var3");
        List<String> second = Arrays.asList("var4", "var5", "var6");

        //        System.out.println("Random Program: ");
        ParseTree randomProgram = parseText(generateRandomProgram(10, first, 1, 100));
        //        System.out.println("Random Program: ");

        if (randomProgram != null) {
            //            System.out.println("Random Program: " + randomProgram.getText());
            testProgram(randomProgram, inputList, outputList);
        } else {
            System.out.println("Failed to generate a random program.");
        }

        ParseTree anotherRandomProgram = parseText(generateRandomProgram(10, second, 1, 100));

        if (anotherRandomProgram != null) {
            //            System.out.println("Random Program: " + anotherRandomProgram.getText());
            testProgram(anotherRandomProgram, inputList, outputList);
        } else {
            System.out.println("Failed to generate a random program.");
        }

        ParseTree crossedProgram = crossover(randomProgram, anotherRandomProgram);
        System.out.println("_______________________________________-");
        System.out.println("Crossed Program: " + crossedProgram.getText());

        testProgram(crossedProgram, inputList, outputList);

        ParseTree mutatedProgram = mutate(randomProgram);
        System.out.println("_______________________________________-");
        System.out.println("Mutated Program: " + mutatedProgram.getText());

        testProgram(mutatedProgram, inputList, outputList);

        List<ParseTree> programList = Arrays.asList(randomProgram, anotherRandomProgram, crossedProgram, mutatedProgram);
        ParseTree selectedProgram = tournamentSelection(programList, 2);
        System.out.println("Selected Program: " + selectedProgram.getText());

        testProgram(selectedProgram, inputList, outputList);
    }
}