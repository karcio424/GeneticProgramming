package Program;

import java.util.*;
import java.util.stream.Stream;

public class GPUtils {
    public static int max = 10;
    public static int min = 1;
    public static int expressionNumOfOperators = 0;
    public static int numberOfStatements = 10;
    private static final double mutationProbability = 0.7;
    public static Random rand = new Random();
    private static final List<Character> BASIC_MUTATION_OPERATORS = Stream.of('+', '-', '*', '/', '%').toList();
    private static final List<String> BASIC_COMPARE_OPERATIONS = Stream.of("<", ">", "<=", ">=", "!=", "==").toList();

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
                return generateRandomLogicTerm(variableList);
            }
        }

        return "";
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
        int randomRule = generateRandomNumber(0, 2);
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

    private static int generateRandomNumber(int min_r, int max_r) {
        return min_r + (int) (Math.random() * ((max_r - min_r) + 1));
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
            int var_length;
            switch (c) {
                case '+', '-', '/', '*', '%' -> {
                    temp = c;
                    while (temp == c)
                        temp = BASIC_MUTATION_OPERATORS.get(rand.nextInt(BASIC_MUTATION_OPERATORS.size()));
                    mutatedProgram = mutatedProgram.substring(0, mutationPoint) + temp
                            + mutatedProgram.substring(mutationPoint + 1);
                }
                case '>', '<', '=', '!' -> {
                    int length = 1;
                    String operator = String.valueOf(c);
                    if (mutatedProgram.charAt(mutationPoint + 1) == '=') {
                        length = 2;
                        operator += '=';
                    }
                    String newTemp = operator;
                    while (Objects.equals(newTemp, operator))
                        newTemp = BASIC_COMPARE_OPERATIONS.get(rand.nextInt(BASIC_COMPARE_OPERATIONS.size()));
                    mutatedProgram = mutatedProgram.substring(0, mutationPoint) + newTemp
                            + mutatedProgram.substring(mutationPoint + length);
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
                    while (variableList.size() != 1 && var_name.contentEquals(sb)) {
                        var_name = variableList.get(rand.nextInt(variableList.size()));
                    }
                    mutatedProgram = mutatedProgram.substring(0, mutationPoint) + var_name
                            + mutatedProgram.substring(mutationPoint + var_length);
                }
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                    int whichOperation = rand.nextInt(4); // liczba, input czy var
                    int oldLength = 1;
                    int j = mutationPoint;
                    while (mutatedProgram.charAt(j + 1) >= '0' && mutatedProgram.charAt(j + 1) <= '9') {
                        j += 1;
                        oldLength += 1;
                    }
                    String newString;
                    if (whichOperation == 0) {
                        newString = "input";
                    } else if (whichOperation == 1) {
                        newString = variableList.get(rand.nextInt(variableList.size()));
                    } else {
                        newString = String.valueOf(rand.nextInt(max));
                    }
                    mutatedProgram = mutatedProgram.substring(0, mutationPoint) + newString
                            + mutatedProgram.substring(mutationPoint + oldLength);

                }
                default -> throw new IllegalStateException("Invalid mutation operation!");
            }
        }
        return mutatedProgram;
    }

    private static int findMutationPoint(String programText) {
        return new GramaticsAnalyzer(programText).analyze().getRandomMutationPoint();
    }

    public static List<Integer> tournamentSelection(int tournamentSize, int numOfWinners) {
        List<Integer> selectedIndexes = new ArrayList<>();
        Random rand = new Random();
        List<Integer> tournamentParticipantsIndexes = new ArrayList<>();

        // tworze turniej indeksow
        while (tournamentParticipantsIndexes.size() < tournamentSize) {
            int randNum = rand.nextInt(100);
            if (!tournamentParticipantsIndexes.contains(randNum)) {
                tournamentParticipantsIndexes.add(randNum);
            }
        }
        //sortuje indeksy (bo z posortowanej populacji najmniejsze maja najlepszy fitness
        Collections.sort(tournamentParticipantsIndexes);
        //dodaje 1 albo 2 (w zaleznosci od liczby wygranych)
        for (int i = 0; i < numOfWinners; i++) {
            selectedIndexes.add(tournamentParticipantsIndexes.get(i));
        }

        return selectedIndexes;
    }

    public static List<String> generateNextGeneration(List<String> currentGeneration, List<String> variableList, Vector<Double> outputFitness) {
        Random rand = new Random();
        int populationSize = currentGeneration.size();
        int elitePrograms = 10; // ILOSC PROGRAMOW NAJLEPSZYCH KTORA WCHODZI SAMOCZYNNIE DO KOLEJNEJ GENERACJI
        int tournamentSize = 5;
        List<Population> sortedPopulation = new ArrayList<>();
        for (int i = 0; i < populationSize; i++)
            sortedPopulation.add(new Population(currentGeneration.get(i), outputFitness.get(i)));

        List<Population> bestPopulation = new ArrayList<>();
        List<String> newGeneration = new ArrayList<>();
        Collections.sort(sortedPopulation);
        for (int i = 0; i < elitePrograms; i++) {
            bestPopulation.add(sortedPopulation.get(i));
            newGeneration.add(sortedPopulation.get(i).getProgram());
        }

        while (newGeneration.size() < populationSize) {
            boolean whichOperation = rand.nextInt(10) < 10 * mutationProbability; // true - mutation, false - crossover
            if (whichOperation) { // Mutacja
                int mutatedProgramIndex = tournamentSelection(tournamentSize, 1).get(0);
                String programToMutate = sortedPopulation.get(mutatedProgramIndex).getProgram();
                String mutatedProgram = mutate(programToMutate, variableList);
                newGeneration.add(mutatedProgram);
            } else { // Krzyżowanie
                List<Integer> crossedIndexes = tournamentSelection(tournamentSize, 2);
                String program1 = sortedPopulation.get(crossedIndexes.get(0)).getProgram();
                String program2 = sortedPopulation.get(crossedIndexes.get(1)).getProgram();

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

        List<String> first = Arrays.asList("var1", "var2", "var3");

        String randomProgram = generateRandomProgram(10, first, 1, 100);
        String mutated = mutate(randomProgram, first);

        System.out.println(randomProgram);
        System.out.println("=========");
        System.out.println(mutated);
    }
}