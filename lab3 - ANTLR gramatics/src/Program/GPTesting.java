package Program;

import Interpreter.InterpreterInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class GPTesting {
    int maxVariables;
    int populationSize = 10;
    int generations = 20;
    int maxOperations = 100;
    int minValue = 1;
    int maxValue = 10;
    ArrayList<int[]> input;
    ArrayList<Object[]> expectedOutput;
    ArrayList<int[]> parameters;
    List<String> population;
    Vector<Double> outputFitness = new Vector<>();
    List<String> globalVariables;
    //    Vector<List<List<Object>>> outputList = new Vector<>(populationSize);
    double bestFitness;
    int numberOfFails = 0;
    double bestGlobalFitness;
    String bestProgram;
    int bestGeneration;

    public GPTesting(ArrayList<int[]> t_input, ArrayList<Object[]> t_output,
                     int t_maxVariables, int t_populationSize, int t_generations,
                     int t_maxOperations, int t_minValue, int t_maxValue) {
//        FitnessFunction fitnessFunction = (output) -> {
//            return output == 1 ? 10 : 5;
//        };

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
        bestProgram="";
        bestGeneration=-1;
    }

    public List<Object> runTest() {
        List<List<String>> grupaZGeneracji = Generate.generatePopulation(populationSize, minValue, maxValue, maxVariables);
        population = grupaZGeneracji.get(0);
        globalVariables = grupaZGeneracji.get(1);
        ArrayList<Object> actualOutput;
        double fitness;
        String program;
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
                bestGeneration=gen-1;
                System.out.println(bestGlobalFitness);
                if(bestGlobalFitness==0){
                    return resultList;
                }
            }
            population = GPUtils.generateNextGeneration(population, globalVariables);
            calculate_generation(currentInput);
            bestIndex = getBestProgram();
            System.out.println("------    GENERACJA "+ gen +"  -------");
            System.out.println("NAJLEPSZY: " + bestIndex + " " + bestFitness);
            System.out.println(population.get(bestIndex));
            System.out.println("--------------------------------");
            if (gen==9 && bestGlobalFitness > bestFitness) {
                bestGlobalFitness = bestFitness;
                bestProgram = population.get(bestIndex);
                resultList.set(0, bestGlobalFitness);
                resultList.set(1, bestProgram);
                bestGeneration=gen;
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
//            System.out.println("------------- Program: -------------");
//            System.out.println(program);
//            System.out.println("------------------------------------");
            fitness = 0;
//            List<List<Object>> outputyDanegoProgramu = outputList.get(i);
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
//                outputList.add(actualOutput);
//                outputyDanegoProgramu.add(actualOutput);
            }
            outputFitness.add(fitness);
        }
    }

    public int getBestProgram() {
        double fitness;
        bestFitness = Double.MAX_VALUE;
        String program = null;
        int bestProgram = -1;
        int JUST_TEST = 0, poprawne = 0;
        for (int i = 0; i < outputFitness.size(); i++) {
//            fitness = InterpreterInterface.evaluateFitness(outputList.get(i), expectedOutput, expectedOutput.size());
//            System.out.println(i + " " + fitness);
            fitness = outputFitness.get(i);
            if (fitness == 100) {
                JUST_TEST++;
            }
            if (fitness < bestFitness) {
                bestFitness = fitness;
                bestProgram = i;
            }
            if (fitness == 0) {
                break;
            }
        }
//        System.out.println(JUST_TEST + " " + poprawne);
        return bestProgram;
    }

    public static double main(int[][] inputMatrix, int maxVal) {
        int maxVariables = 3;
        int populationSize = 10000;
        int generations = 5;
        int maxOperations = 3000;
        int minValue = 1;
        int testCases;
        ArrayList<int[]> inputVector = new ArrayList<>();
        ArrayList<Object[]> expectedOutputVector = new ArrayList<>();
//        this.isProblemSolved = false;

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
//        System.out.println(Arrays.toString(inputVector.get(0)));
//        System.out.println(Arrays.toString(expectedOutputVector.get(0)));
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
