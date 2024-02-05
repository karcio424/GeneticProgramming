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
//    Vector<List<List<Object>>> outputList = new Vector<>(populationSize);
    double bestFitness;

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
    }

    public void runTest() {
        population = Generate.generatePopulation(populationSize, minValue, maxValue);
        ArrayList<Object> actualOutput;
        double fitness, bestfitness = 1000000;
        String program, bestProgram = null;
        ArrayList<Integer> currentInput = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            program = population.get(i);
            System.out.println("------------- Program: -------------");
            System.out.println(program);
            System.out.println("------------------------------------");
            fitness=0;
//            List<List<Object>> outputyDanegoProgramu = outputList.get(i);
            for (int j = 0; j < input.size(); j++) {
                currentInput.clear();
                for (int value : input.get(i)) {
                    currentInput.add(value);
                }

                actualOutput = InterpreterInterface.evaluateProgram(program, currentInput, maxOperations);
                fitness += InterpreterInterface.evaluateFitness(actualOutput, expectedOutput.get(j));
//                outputList.add(actualOutput);
//                outputyDanegoProgramu.add(actualOutput);
            }
            outputFitness.add(fitness);
        }
    }

    public int getBestProgram() {
        double fitness;
        bestFitness = 1000000;
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

    public static void main(int[][] inputMatrix) {
        int maxVariables = 3;
        int populationSize = 5;
        int generations = 10;
        int maxOperations = 1000;
        int minValue = 1;
        int maxValue = 10;
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
                generations, maxOperations, minValue, maxValue);
        test.runTest();
        int bestIndex = test.getBestProgram();
        System.out.println("--------------------------------");
        System.out.println("NAJLEPSZY: " + bestIndex + " " + test.bestFitness);
//        System.out.println(test.outputList.get(bestIndex));
//        List<List<Object>> programData = test.outputList.get(bestIndex);
//        for (List<Object> data : programData) {
//            System.out.println(data.toString());
//        }

        System.out.println(test.population.get(bestIndex));

    }

}
