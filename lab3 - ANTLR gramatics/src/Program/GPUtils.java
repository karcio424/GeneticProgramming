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

    private static ParseTree generateRandomProgram(int length, List<String> list) {
        GPprojectLexer lexer = new GPprojectLexer(CharStreams.fromString(generateRandomStatement(null, length, list)));
        GPprojectParser parser = new GPprojectParser(new CommonTokenStream(lexer));
        return parseText(parser, generateRandomStatement(parser, length, list));
    }

    private static String generateRandomStatement(GPprojectParser parser, int length, List<String> variableList) {
        if (length <= 0) {
            return "";
        }

        String statement = "";
        int randomRule = generateRandomNumber(1, 4);
        int randomVar = generateRandomNumber(0, variableList.size() - 1);

        switch (randomRule) {
            case 1 -> statement = generateRandomLoopStatement(parser, length - 1, variableList.get(randomVar));
            case 2 -> statement = generateRandomConditionalStatement(parser, length - 1, variableList.get(randomVar));
            case 3 -> statement = generateRandomBlockStatement(parser, length - 1, variableList.get(randomVar));
            case 4 -> statement = generateRandomAssignmentStatement(parser, variableList.get(randomVar));
        }

        return statement + generateRandomStatement(parser, length - 1, variableList);
    }

    private static String generateRandomLoopStatement(GPprojectParser parser, int length, String var) {
        return "loop(" + var + ") {" + var + "=" + generateRandomNumber(1, 100) + ";}\n";
    }

    private static String generateRandomConditionalStatement(GPprojectParser parser, int length, String var) {
        String condition = var;
        String ifStatement = "if (" + condition + ") ";
        String thenStatement = "{" + var + "=" + generateRandomNumber(1, 100) + ";}";
        String elseStatement = "else {" + var + "=" + generateRandomNumber(1, 100) + ";}";

        return ifStatement + thenStatement + elseStatement + "\n";
    }

    private static String generateRandomBlockStatement(GPprojectParser parser, int length, String var) {
        return "{" + var + "="+ generateRandomNumber(1,100) + ";}\n";
    }

    private static String generateRandomAssignmentStatement(GPprojectParser parser, String var) {
        return var + "="+ generateRandomNumber(1,100) + ";\n";
    }

    private static ParseTree parseText(GPprojectParser parser, String text) {
        GPprojectLexer lexer = new GPprojectLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        System.out.println(parser + " " + text);
        return parser.program();
    }

    private static int generateRandomNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private static String generateRandomVariableName() {
        return "var" + generateRandomNumber(1, 100);
    }

    public static ParseTree crossover(ParseTree program1, ParseTree program2) {
        String programText1 = program1.getText();
        String programText2 = program2.getText();

        int splitIndex1 = generateRandomNumber(1, programText1.length() - 1);
        int splitIndex2 = generateRandomNumber(1, programText2.length() - 1);

        // Ensure split indices are not equal
        while (splitIndex1 == splitIndex2) {
            splitIndex2 = generateRandomNumber(1, programText2.length() - 1);
        }

        // Adjusting the substring boundaries
        String newProgramText = programText1.substring(0, splitIndex1) + programText2.substring(splitIndex2);

        // Using the parser from program1, assuming they share the same grammar
        return parseText(new GPprojectParser(new CommonTokenStream(new GPprojectLexer(CharStreams.fromString(programText1)))), newProgramText);
    }

    public static ParseTree mutate(ParseTree program) {
        String programText = program.getText();
        int mutationIndex = generateRandomNumber(0, programText.length() - 1);

        // Remove the entire statement at the mutation index
        String mutatedProgramText = programText.substring(0, mutationIndex);
        int statementLength = generateRandomNumber(1, 10); // Set a reasonable length for the new statement
        mutatedProgramText += generateRandomStatement(null, statementLength, Arrays.asList("var1", "var2", "var3"));
        mutatedProgramText += programText.substring(mutationIndex + 1);

        // Use a valid parser with CommonTokenStream
        GPprojectParser parser = new GPprojectParser(new CommonTokenStream(new GPprojectLexer(CharStreams.fromString(mutatedProgramText))));

        return parseText(parser, mutatedProgramText);
    }

    public static void testProgram(ParseTree program, List<Integer> input, List<Integer> output) {
        AntlrProgram programVisitor = new AntlrProgram("input.txt", 100);
        programVisitor.visit(program);
        System.out.println("Testing program: " + program.getText());
        System.out.println("Input program: " + input);
        System.out.println("Output program: " + output);
        // TODO/TOFINISH
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
        AntlrProgram programVisitor = new AntlrProgram("input.txt", 100);
        programVisitor.visit(program);
        // TODO
        return 0.0;
    }

    public static String serialize(ParseTree program) {
        return program.getText();
    }

    public static ParseTree deserialize(String serializedData) {
        return parseText(new GPprojectParser(null), serializedData);
    }

    public static void main(String[] args) {
        Integer[] inputData = {1, 2, 3};
        Integer[] outputData = {2, 4, 6};

        List<Integer> inputList = Arrays.asList(inputData);
        List<Integer> outputList = Arrays.asList(outputData);

        ParseTree randomProgram = generateRandomProgram(10, Arrays.asList("var1", "var2", "var3"));

        if (randomProgram != null) {
            System.out.println("Random Program: " + randomProgram.getText());
            testProgram(randomProgram, inputList, outputList);
        } else {
            System.out.println("Failed to generate a random program.");
        }

        ParseTree anotherRandomProgram = generateRandomProgram(10, Arrays.asList("var4", "var5", "var6"));

        if (anotherRandomProgram != null) {
            System.out.println("Random Program: " + anotherRandomProgram.getText());
            testProgram(anotherRandomProgram, inputList, outputList);
        } else {
            System.out.println("Failed to generate a random program.");
        }

        ParseTree crossedProgram = crossover(randomProgram, anotherRandomProgram);
        System.out.println("Crossed Program: " + crossedProgram.getText());

        testProgram(crossedProgram, inputList, outputList);

        ParseTree mutatedProgram = mutate(randomProgram);
        System.out.println("Mutated Program: " + mutatedProgram.getText());

        testProgram(mutatedProgram, inputList, outputList);

        List<ParseTree> programList = Arrays.asList(randomProgram, anotherRandomProgram, crossedProgram, mutatedProgram);
        ParseTree selectedProgram = tournamentSelection(programList, 2);
        System.out.println("Selected Program: " + selectedProgram.getText());

        testProgram(selectedProgram, inputList, outputList);
    }
}