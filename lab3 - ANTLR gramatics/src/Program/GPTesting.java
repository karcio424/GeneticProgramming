package Program;

import Interpreter.InterpreterInterface;

import java.util.*;

public class GPTesting {
    int maxVariables;
    int populationSize;
    int generations;
    int maxOperations;
    int minValue, maxValue;
    ArrayList<int[]> input;
    ArrayList<Object[]> expectedOutput;
    ArrayList<int[]> parameters;
    List<String> population;
    Vector<Double> outputFitness = new Vector<>();
    List<String> globalVariables;
    double bestFitness;
    int numberOfFails = 0;
    double bestGlobalFitness;
    String bestProgram;
    int bestGeneration;

    public GPTesting(ArrayList<int[]> t_input, ArrayList<Object[]> t_output,
                     int t_maxVariables, int t_populationSize, int t_generations,
                     int t_maxOperations, int t_minValue, int t_maxValue) {

        maxVariables = t_maxVariables;
        populationSize = t_populationSize;
        generations = t_generations;
        maxOperations = t_maxOperations;
        minValue = t_minValue;
        maxValue = t_maxValue;
        input = t_input;
        expectedOutput = t_output;
        parameters = new ArrayList<>();
        bestGlobalFitness = Double.MAX_VALUE;
        bestProgram = "";
        bestGeneration = -1;
    }

    public List<Object> runTest() {
        List<List<String>> grupaZGeneracji = Generate.generatePopulation(populationSize, minValue, maxValue, maxVariables);
        population = grupaZGeneracji.get(0);
        globalVariables = grupaZGeneracji.get(1);
        ArrayList<Integer> currentInput = new ArrayList<>();
        calculate_generation(currentInput);
        List<Object> resultList = new ArrayList<>();
        resultList.add(bestGlobalFitness);
        resultList.add("");

        int bestIndex = getBestProgram();
        System.out.println("------    GENERACJA 0    -------");
        System.out.println("NAJLEPSZY: " + bestIndex + " " + bestFitness);
        System.out.println(population.get(bestIndex));
        System.out.println("--------------------------------");
        for (int gen = 1; gen < generations; gen++) {
            System.out.println(bestGlobalFitness);
            if (bestGlobalFitness > bestFitness) {
                bestGlobalFitness = bestFitness;
                bestProgram = population.get(bestIndex);
                resultList.set(0, bestGlobalFitness);
                resultList.set(1, bestProgram);
                bestGeneration = gen - 1;
                System.out.println(bestGlobalFitness);
                if (bestGlobalFitness == 0) {
                    return resultList;
                }
            }
            System.out.println("WIELKOSC POPULACJI: " + population.size());
            population = GPUtils.generateNextGeneration(population, globalVariables, outputFitness);
            calculate_generation(currentInput);
            bestIndex = getBestProgram();
            System.out.println("------    GENERACJA " + gen + "  -------");
            System.out.println("NAJLEPSZY: " + bestIndex + " " + bestFitness);
            System.out.println("--------------------------------");
            if (gen == 9 && bestGlobalFitness > bestFitness) {
                bestGlobalFitness = bestFitness;
                bestProgram = population.get(bestIndex);
                resultList.set(0, bestGlobalFitness);
                resultList.set(1, bestProgram);
                bestGeneration = gen;
            }
        }
        System.out.println("NUMBER OF FAILS: " + numberOfFails);
        System.out.println(bestGlobalFitness);
        System.out.println(bestGeneration);
        resultList.set(0, bestGlobalFitness);
        return resultList;
    }

    private void calculate_generation(ArrayList<Integer> currentInput) {
        String program;
        double fitness;
        ArrayList<Object> actualOutput;
        outputFitness.clear();
        for (int i = 0; i < populationSize; i++) {
            program = population.get(i);
            fitness = 0;
            for (int j = 0; j < input.size(); j++) {
                currentInput.clear();
                for (int value : input.get(j)) {
                    currentInput.add(value);
                }

                actualOutput = InterpreterInterface.evaluateProgram(program, currentInput, maxOperations);
                if (InterpreterInterface.getDidProgramFail()) {
                    numberOfFails++;
                }
                fitness += InterpreterInterface.evaluateFitness(actualOutput, expectedOutput.get(j));
            }
            outputFitness.add(fitness);
        }
    }

    public int getBestProgram() {
        double fitness;
        bestFitness = Double.MAX_VALUE;
        int bestProgram = -1;
        for (int i = 0; i < outputFitness.size(); i++) {
            fitness = outputFitness.get(i);
            if (fitness < bestFitness) {
                bestFitness = fitness;
                bestProgram = i;
            }
            if (fitness == 0) {
                break;
            }
        }
        return bestProgram;
    }

    public static double main(int[][] inputMatrix, int maxVal) {
        int maxVariables = 2;
        int populationSize = 5000;
        int generations = 8;
        int maxOperations = 2000;
        int minValue = 1;
        int testCases;
        ArrayList<int[]> inputVector = new ArrayList<>();
        ArrayList<Object[]> expectedOutputVector = new ArrayList<>();

        testCases = inputMatrix[0][0];
        int inputNumbers = inputMatrix[0][1];
        int outputNumbers = inputMatrix[0][2];

        for (int i = 0; i < testCases; i++) {
            int[] inputValues = new int[inputNumbers];
            System.arraycopy(inputMatrix[i + 1], 0, inputValues, 0, inputNumbers);
            inputVector.add(inputValues);

            Object[] outputValues = new Object[outputNumbers];
            int[] intArray = new int[outputNumbers];
            System.arraycopy(inputMatrix[i + 1], inputNumbers, intArray, 0, outputNumbers);
            for (int j = 0; j < outputNumbers; j++) {
                outputValues[j] = intArray[j];
            }
            expectedOutputVector.add(outputValues);
        }
        GPTesting test = new GPTesting(inputVector, expectedOutputVector, maxVariables, populationSize,
                generations, maxOperations, minValue, maxVal);
        List<Object> result = test.runTest();
        double doubleValue = (double) result.get(0);
        String BESTBEST = (String) result.get(1);
        System.out.println("------------- BEST Program: -------------");
        System.out.println(BESTBEST);
        System.out.println("-------------------------------------------");

        return doubleValue;
    }

}
