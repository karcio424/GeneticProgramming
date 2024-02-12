package Program;

import Interpreter.GPprojectLexer;
import Interpreter.GPprojectParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;

public class GPUtils {
    public static int max = 10;
    public static int min = 1;
    public static int expressionNumOfOperators = 0;
    public static int numberOfStatements = 10;
    public static Random rand = new Random();

    public static String generateRandomProgram(int length, List<String> list, int min_r, int max_r) {
        min = min_r;
        max = max_r;
        numberOfStatements = length;
        return generateRandomStatement(length, list, 1, 5);
    }

    private static String generateRandomStatement(int length, List<String> variableList, int from, int to) {
        if (numberOfStatements <= 0 || length <= 0) {
            return "";
        }

        String statement = "";
        int randomRule = generateRandomNumber(from, to);
        int randomVar = generateRandomNumber(0, variableList.size() - 1);

        switch (randomRule) {
            case 1 -> statement = generateRandomLoopStatement(variableList);
            case 2 -> statement = generateRandomOutputStatement(variableList);
            //            case 3 -> statement = generateRandomBlockStatement(length - 1, variableList);
            case 3, 4 -> statement = generateRandomAssignmentStatement(variableList.get(randomVar), variableList);
            case 5 -> statement = generateRandomConditionalStatement(variableList);
        }

        numberOfStatements--;
        return statement + generateRandomStatement(length - 1, variableList, 1, 5);
    }

    private static String generateRandomOutputStatement(List<String> variableList) {
        int randomVar = generateRandomNumber(0, variableList.size() - 1);
        return "output(" + variableList.get(randomVar) + ");\n";
    }

    private static String generateRandomConditionalStatement(List<String> variableList) {
        return "if (" + generateRandomExpression(variableList) + ") " +
                generateRandomBlockStatement(variableList) +
                " else" + generateRandomBlockStatement(variableList);
    }

    private static String generateRandomLoopStatement(List<String> variableList) {
        return "loop(" + generateRandomExpression(variableList) + ")" +
                generateRandomBlockStatement(variableList);
    }

    private static String generateRandomBlockStatement(List<String> variableList) {
        int numberStatementsInside = generateRandomNumber(1, 1);
        if (numberOfStatements <= 0)
            numberOfStatements -= numberOfStatements - 1;
        return "{\n" + generateRandomStatement(numberStatementsInside, variableList, 2, 4) + "}\n";
    }

    private static String generateRandomAssignmentStatement(String
                                                                    var, List<String> variableList) {
        return var + "=" + generateRandomExpression(variableList) + ";\n";
    }

    private static String generateRandomExpression(List<String> variableList) {
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
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private static int generateRandomNumber(int min_r, int max_r) {
        return min_r + (int) (Math.random() * ((max_r - min_r) + 1));
    }

    private static String generateRandomVariableName() {
        return "var" + generateRandomNumber();
    }

    private static final double mutationProbability = 0.3;

    public static List<String> crossover(String parent1, String parent2) {
        int crossoverPoint1 = findCrossoverPoint(parent1);
        int crossoverPoint2 = findCrossoverPoint(parent2);

        String crossedText1 = parent1.substring(0, crossoverPoint1) + parent2.substring(crossoverPoint2);
        String crossedText2 = parent2.substring(0, crossoverPoint2) + parent1.substring(crossoverPoint1);

        List<String> crossedList = new ArrayList<>(2);
        crossedList.add(crossedText1);
        crossedList.add(crossedText2);

        return crossedList;
    }

    private static int findCrossoverPoint(String programText) {
        return new GramaticsAnalyzer(programText).analyze().getRandomCrossoverPoint();
    }

    public static String mutate(String program, List<String> variableList) {

        int mutationPoint = findMutationPoint(program);

        String mutatedText = generateRandomProgram(1, variableList, 1, max);

        return program.substring(0, mutationPoint) +
                mutatedText +
                program.substring(mutationPoint);
    }

    private static int findMutationPoint(String programText) {
        return new GramaticsAnalyzer(programText).analyze().getRandomMutationPoint();
    }

    public static List<String> tournamentSelection(List<String> population, Vector<Double> outputFitness) {
        List<Population> bestPopulation = new ArrayList<>();
        List<String> selectedPopulation = new ArrayList<>();

        int populationSize = population.size() - 1;
        int tournamentSize = 100, selectedPopulationSize = 10, finalPopulationSize = 2;
        Collections.sort(population);

        for (int i = populationSize - tournamentSize; i < populationSize; i++)
            bestPopulation.add(new Population(population.get(i), outputFitness.get(i)));

        for (int i = tournamentSize - selectedPopulationSize; i < tournamentSize; i++) {
            selectedPopulation.add(bestPopulation.get(rand.nextInt(tournamentSize)).getProgram());
        }

        Collections.sort(selectedPopulation);
        for (int i = 0; i < selectedPopulationSize - finalPopulationSize; i++) {
            selectedPopulation.remove(rand.nextInt(selectedPopulationSize - i));
        }

        return selectedPopulation;
    }

//    public static List<String> tournamentSelection(List<String> population, Vector<Double> outputFitness) {
//        List<Pair> chosenPopulation = new ArrayList<>();
//        List<String> selectedPopulation = new ArrayList<>();
//
//        Random rand = new Random();
//        int tournamentSize = 1000, selectedPopulationSize = 25, finalPopulationSize = 2;
//
//        for (int i = 0; i < tournamentSize; i++) {
//            int randomIndex = rand.nextInt(population.size());
//            chosenPopulation.add(new Pair(population.get(randomIndex), outputFitness.get(randomIndex)));
//            population.remove(randomIndex);
//        }
//
//        Collections.sort(chosenPopulation);
//        for (int i = tournamentSize - selectedPopulationSize; i < tournamentSize; i++) {
//            selectedPopulation.add(chosenPopulation.get(i).getProgram());
//        }
//        for (int i = 0; i < selectedPopulationSize - finalPopulationSize; i++) {
//            selectedPopulation.remove(rand.nextInt(selectedPopulationSize - i));
//        }
//
//        return selectedPopulation;
//    }

    public static List<String> generateNextGeneration(List<String> currentGeneration, List<String> variableList, Vector<Double> outputFitness) {
        List<String> newGeneration = tournamentSelection(currentGeneration, outputFitness);
        Random rand = new Random();
        int populationSize = 5000;
        int programIndex1, programIndex2;

        while (newGeneration.size() < populationSize) {
            int currentSize = newGeneration.size();
            programIndex1 = rand.nextInt(currentSize);
            String program1 = newGeneration.get(programIndex1);

            int whichOperation = rand.nextInt(10);
            if (whichOperation < 10 * mutationProbability) { // Mutacja
                String mutatedProgram = mutate(program1, variableList);
                newGeneration.add(mutatedProgram);
            } else { // Krzyżowanie
                programIndex2 = rand.nextInt(currentSize);
                while (programIndex2 == programIndex1)
                    programIndex2 = rand.nextInt(currentSize);
                String program2 = newGeneration.get(programIndex2);
                List<String> crossedProgram = crossover(program1, program2);
                String crossedProgram1 = crossedProgram.get(0);
                String crossedProgram2 = crossedProgram.get(1);
                newGeneration.add(crossedProgram1);
                newGeneration.add(crossedProgram2);
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

        List<Integer> inputList = Arrays.asList(inputData);
        List<Integer> outputList = Arrays.asList(outputData);
        List<String> first = Arrays.asList("var1", "var2", "var3");
        List<String> second = Arrays.asList("var4", "var5", "var6");

        ParseTree randomProgram = parseText(generateRandomProgram(10, first, 1, 100));
        ParseTree anotherRandomProgram = parseText(generateRandomProgram(10, second, 1, 100));
    }
}