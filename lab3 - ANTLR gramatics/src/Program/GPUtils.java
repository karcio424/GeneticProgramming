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

    public static ParseTree generateRandomProgram(int size) {
        if (size <= 0) return null;

        GPprojectParser parser = new GPprojectParser(null);

        return generateRandomStatement(parser, size);
    }

    private static ParseTree generateRandomStatement(GPprojectParser parser, int size) {
        if (size <= 0) {
            return null;
        }

        int randomRule = generateRandomNumber(1, 4);

        switch (randomRule) {
            case 1:
                return generateRandomLoopStatement(parser, size);
            case 2:
                return generateRandomConditionalStatement(parser, size);
            case 3:
                return generateRandomBlockStatement(parser, size);
            case 4:
                return generateRandomAssignmentStatement(parser, size);
        }

        return null;
    }

    private static ParseTree generateRandomLoopStatement(GPprojectParser parser, int size) {
        if (size <= 0) {
            return null;
        }

        String var = generateRandomVariableName();
        String loopStatementText = "loop(" + var + ") {" + var + "=" + generateRandomNumber(1, 100) + ";}";

        // Adjust the size for the recursive call
        int newSize = size - loopStatementText.length();

        return parseText(parser, loopStatementText + generateRandomStatement(parser, newSize));
    }

    private static ParseTree generateRandomConditionalStatement(GPprojectParser parser, int size) {
        String var = generateRandomVariableName();
        String conditionalStatementText = "if (" + var + ") {" + var + "=" + generateRandomNumber(1, 100) + ";} else {" + var + "=" + generateRandomNumber(1, 100) + ";}";
        return parseText(parser, conditionalStatementText);
    }

    private static ParseTree generateRandomBlockStatement(GPprojectParser parser, int size) {
        String var = generateRandomVariableName();
        String blockStatementText = "{" + var + "=" + generateRandomNumber(1, 100) + ";}";
        return parseText(parser, blockStatementText);
    }

    private static ParseTree generateRandomAssignmentStatement(GPprojectParser parser, int size) {
        String var = generateRandomVariableName();
        String assignmentStatementText = var + "=" + generateRandomNumber(1, 100) + ";";
        return parseText(parser, assignmentStatementText);
    }

    private static ParseTree parseText(GPprojectParser parser, String text) {
        GPprojectLexer lexer = new GPprojectLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
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

        String newProgramText = programText1.substring(0, splitIndex1) + programText2.substring(splitIndex2);

        return parseText(new GPprojectParser(null), newProgramText);
    }

    public static ParseTree mutate(ParseTree program) {
        String programText = program.getText();
        int mutationIndex = generateRandomNumber(0, programText.length() - 1);
        char mutationChar = (char) generateRandomNumber(32, 126);
        String mutatedProgramText = programText.substring(0, mutationIndex) + mutationChar + programText.substring(mutationIndex + 1);

        return parseText(new GPprojectParser(null), mutatedProgramText);
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

        ParseTree randomProgram = generateRandomProgram(10);

        if (randomProgram != null) {
            System.out.println("Random Program: " + randomProgram.getText());
            testProgram(randomProgram, inputList, outputList);
        } else {
            System.out.println("Failed to generate a random program.");
        }

        ParseTree anotherRandomProgram = generateRandomProgram(10);

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