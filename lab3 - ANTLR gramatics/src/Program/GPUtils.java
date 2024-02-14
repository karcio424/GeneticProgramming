package Program;

import Interpreter.GPprojectLexer;
import Interpreter.GPprojectParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;
import java.util.stream.Stream;

public class GPUtils {
    public static int max = 10;
    public static int min = 1;
    public static int expressionNumOfOperators = 0;
    public static int numberOfStatements = 10;
    private static final double mutationProbability = 0.3;
    public static Random rand = new Random();
    private static final List<Character> BASIC_MUTATION_OPERATORS = Stream.of('+', '-', '*', '/', '%').toList();

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

    public static List<String> crossover(String parent1, String parent2) {
        int crossoverPoint1 = findCrossoverPoint(parent1);
        int crossoverPoint2 = findCrossoverPoint(parent2);
        List<String> crossedList = new ArrayList<>(2);

        if (crossoverPoint1 == -1 || crossoverPoint2 == -1) {
            crossedList.add(parent1);
            crossedList.add(parent2);
        } else {
            String crossedText1 = parent1.substring(0, crossoverPoint1) + parent2.substring(crossoverPoint2);
            String crossedText2 = parent2.substring(0, crossoverPoint2) + parent1.substring(crossoverPoint1);

            crossedList.add(crossedText1);
            crossedList.add(crossedText2);
        }

        return crossedList;
    }

    private static int findCrossoverPoint(String programText) {
        return new GramaticsAnalyzer(programText).analyze().getRandomCrossoverPoint();
    }

    public static String mutate(String program, List<String> variableList) {
        int mutationsAmount = rand.nextInt(3) + 1;
        String mutatedProgram = program;

        for (int i = 0; i < mutationsAmount; i++) {
            int mutationPoint = findMutationPoint(mutatedProgram);
            if (mutationPoint == -1)
                return program;

            char c = mutatedProgram.charAt(mutationPoint), temp;
            int var_length = 3;
            switch (c) {
                case '+', '-', '/', '*', '%' -> {
                    temp = c;
                    while (temp == c)
                        temp = BASIC_MUTATION_OPERATORS.get(rand.nextInt(BASIC_MUTATION_OPERATORS.size()));
                    mutatedProgram = mutatedProgram.substring(0, mutationPoint) + temp
                            + mutatedProgram.substring(mutationPoint + 1);
                }
                case 'i' -> {
                    if (rand.nextInt(2) == 0)
                        return mutatedProgram.substring(0, mutationPoint) + rand.nextInt(1000)
                                + mutatedProgram.substring(mutationPoint + 5);
                    else
                        mutatedProgram = mutatedProgram.substring(0, mutationPoint)
                                + variableList.get(rand.nextInt(variableList.size()))
                                + mutatedProgram.substring(mutationPoint + 5);
                }
                case 'v' -> {
                    StringBuilder sb = new StringBuilder("var");
                    for (int j = mutationPoint + 3; j < mutatedProgram.length(); j++) {
                        if (!(mutatedProgram.charAt(j) >= '0' && mutatedProgram.charAt(j) <= '9'))
                            break;
                        sb.append(mutatedProgram.charAt(j));
                    }

                    String var_name = sb.toString();
                    var_length = var_name.length();
                    while (variableList.size() != 1 && var_name.contentEquals(sb))
                        var_name = variableList.get(rand.nextInt(variableList.size()));
                    mutatedProgram = mutatedProgram.substring(0, mutationPoint) + var_name
                            + mutatedProgram.substring(mutationPoint + var_length);
                }
                default -> throw new IllegalStateException("Invalid mutation operation!");
            }
        }
        return mutatedProgram;
    }

    private static int findMutationPoint(String programText) {
        return new GramaticsAnalyzer(programText).analyze().getRandomMutationPoint();
    }

    public static List<String> tournamentSelection(List<String> population, Vector<Double> outputFitness,
                                                   int tournamentSize, int selectedPopulationSize, int finalPopulationSize) {
        int populationSize = population.size() - 1;
        List<Population> preparedPopulation = new ArrayList<>();
        for (int i = 0; i < populationSize; i++)
            preparedPopulation.add(new Population(population.get(i), outputFitness.get(i)));

        List<Population> bestPopulation = new ArrayList<>();
        List<Population> selectedPopulation = new ArrayList<>();
        List<String> selectedPrograms = new ArrayList<>();

        Collections.sort(preparedPopulation);
        for (int i = populationSize - tournamentSize; i < populationSize; i++)
            bestPopulation.add(preparedPopulation.get(i));

        for (int i = tournamentSize - selectedPopulationSize; i < tournamentSize; i++) {
            int randomIndex = rand.nextInt(2 * tournamentSize - 2 * selectedPopulationSize - i);
            selectedPopulation.add(bestPopulation.get(randomIndex));
            bestPopulation.remove(randomIndex);
        }

        for (int i = 0; i < selectedPopulationSize - finalPopulationSize; i++) {
            selectedPopulation.remove(rand.nextInt(selectedPopulationSize - i));
        }

        for (Population p : selectedPopulation)
            selectedPrograms.add(p.getProgram());
        return selectedPrograms;
    }

    public static List<String> generateNextGeneration(List<String> currentGeneration, List<String> variableList, Vector<Double> outputFitness) {
        Random rand = new Random();
        int populationSize = currentGeneration.size();
        int programIndex1, programIndex2;

        List<String> newGeneration = tournamentSelection(currentGeneration, outputFitness, 100, 10, 5);
        int initialSizeOfNewGeneration = newGeneration.size();

        while (newGeneration.size() < populationSize) {
            programIndex1 = rand.nextInt(initialSizeOfNewGeneration);
            String program1 = newGeneration.get(programIndex1);

            boolean whichOperation = rand.nextInt(10) < 10 * mutationProbability; // true - mutation, false - crossover
            if (whichOperation) { // Mutacja
                String mutatedProgram = mutate(program1, variableList);
                newGeneration.add(mutatedProgram);
            } else { // Krzyżowanie
                programIndex2 = rand.nextInt(initialSizeOfNewGeneration);
                while (programIndex2 == programIndex1)
                    programIndex2 = rand.nextInt(initialSizeOfNewGeneration);
                String program2 = newGeneration.get(programIndex2);
                List<String> crossedProgram = crossover(program1, program2);
                String crossedProgram1 = crossedProgram.get(0);
                String crossedProgram2 = crossedProgram.get(1);
                newGeneration.add(crossedProgram1);
                newGeneration.add(crossedProgram2);
            }
        }

        while (newGeneration.size() > populationSize)
            newGeneration.remove(newGeneration.size() - 1);

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

        String randomProgram = generateRandomProgram(10, first, 1, 100);
        String anotherRandomProgram = generateRandomProgram(10, second, 1, 100);
//        String mutated = mutate(randomProgram, first);
        List<String> crossed = crossover(randomProgram, anotherRandomProgram);

//        ParseTree randomProgram = parseText(generateRandomProgram(10, first, 1, 100));
//        ParseTree anotherRandomProgram = parseText(generateRandomProgram(10, second, 1, 100));

//        System.out.println(randomProgram);
//        System.out.println(anotherRandomProgram);
        System.out.println("=========");
//        System.out.println(mutated);
//        System.out.println("=========");
        System.out.println(crossed.get(0));
        System.out.println("=========");
        System.out.println(crossed.get(1));
    }
}