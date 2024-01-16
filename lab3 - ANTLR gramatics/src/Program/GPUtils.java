package Program;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.util.Arrays;

public class GPUtils {
    int depth;
    public enum Statements {
        loopStatement,
        conditionalStatement,
        blockStatement,
        assignmentStatement,
        ioStatement
    }

    public static ParseTree generateRandomProgram(int size) {
        if (size == 0) return null;

        Statements[] commandsValues = Statements.values();

        if (size == 1)
            commandsValues = new Statements[]{Statements.blockStatement /*, Commands.ASSIGN, Commands.OUTPUT*/};

        Statements randomCommand = commandsValues[0];

        switch (randomCommand) {
            case loopStatement -> {

//                return generateLoopStatement(size);
            }
            case conditionalStatement -> {
//                return generateConditionalStatement(size);
            }
            case blockStatement -> {
//                return generateBlockStatement(size);
            }
            case assignmentStatement -> {
//                return generateAssignmentStatement(size);
            }
            case ioStatement -> {
//                return generateIOStatement(size);
            }
            default -> {
                return null;
            }
        }
        return null;
    }

    public static ParseTree crossover(ParseTree program1, ParseTree program2) {
        ParseTree randomNodeProgram1 = getRandomNode(program1);
        ParseTree randomNodeProgram2 = getRandomNode(program2);
        return combineTrees(randomNodeProgram1, randomNodeProgram2);
    }

    public static ParseTree getRandomNode(ParseTree tree) {
        int childCount = tree.getChildCount();
        if (childCount == 0) {
            return tree; // Jeśli liść, zwróć ten węzeł
        } else {
            int randomChildIndex = (int) (Math.random() * childCount);
            return getRandomNode(tree.getChild(randomChildIndex));
        }
    }

    public static ParseTree combineTrees(ParseTree node1, ParseTree node2) {
        replaceNode(node1, node2);

        return node1;
    }

    public static void replaceNode(ParseTree originalNode, ParseTree newNode) {
    }



    public static ParseTree mutate(ParseTree program) {
        // Implementacja operacji mutacji
        return null;
    }

    public static void testProgram(ParseTree program, int[] input, int[] output) {
        System.out.println("Testing program: " + program.toStringTree());
        System.out.println("Input program: " + Arrays.toString(input));
        System.out.println("Output program: " + Arrays.toString(output));
        // Implementacja testowania programu na podstawie danych wejściowych i wyjściowych
    }

    public static ParseTree tournamentSelection(ParseTree[] programs, int tournamentSize) {
        // Implementacja selekcji turniejowej na podstawie wartości funkcji przystosowania
        return null;
    }

    public static String serialize(ParseTree program) {
        // Implementacja serializacji programu
        return null;
    }

    public static ParseTree deserialize(String serializedData) {
        // Implementacja deserializacji programu
        return null;
    }

    public static void main(String[] args) {
        int[] inputData = {1, 2, 3};
        int[] outputData = {2, 4, 6};

        ParseTree randomProgram = generateRandomProgram(10);
        testProgram(randomProgram, inputData, outputData);

    }
}