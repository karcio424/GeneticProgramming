package Program;

import Interpreter.InterpreterInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class GPTesting {
    int maxVariables;
    int populationSize=10;
    int generations=20;
    int maxOperations=100;
    int minValue=1;
    int maxValue=10;
    ArrayList<Integer> input;
    ArrayList<Object> expectedOutput;
    ArrayList<Integer> parameters;
    List<String> population;
    Vector<ArrayList<Object>> outputList = new Vector<>();
    double bestFitness;
    public GPTesting(ArrayList<Integer> t_input, ArrayList<Object> t_output,
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
        parameters=new ArrayList<>();
    }

    public void runTest(){
        population = Generate.generatePopulation(populationSize, minValue, maxValue);
        ArrayList<Object> actualOutput;
        double fitness, bestfitness=1000000;
        String program=null, bestProgram=null;
        for(int i=0;i<populationSize;i++){
            program = population.get(i);
            actualOutput = InterpreterInterface.evaluateProgram(program,"", maxOperations);
            outputList.add(actualOutput);
        }
    }

    public int getBestProgram(){
        double fitness;
        bestFitness=1000000;
        String program=null;
        int bestProgram=-1;
        int JUST_TEST = 0, poprawne=0;
        for(int i=0;i<outputList.size();i++){
            fitness = InterpreterInterface.evaluateFitness(outputList.get(i), expectedOutput, parameters);
            System.out.println(i + " " + fitness);
            if (fitness == 100){
                JUST_TEST++;
            }
            if(fitness == 0){
                poprawne++;
            }
            if (fitness < bestFitness) {
                bestFitness = fitness;
                bestProgram = i;
            }
        }
        System.out.println(JUST_TEST + " " + poprawne);
        return bestProgram;
    }

//    public void testGeneticProgramming() {
//        // Definicja funkcji przystosowania
////        FitnessFunction fitnessFunction = (output) -> output == 1 ? 10 : 5;
//        int maxVariables = 3;
//        int populationSize = 100;
//        int generations = 10;
//        int maxOperations = 100;
//
//        ArrayList<Integer> inputVector = new ArrayList<>();
//        inputVector.add(5);
//
//        ArrayList<Object> expectedOutputVector = new ArrayList<>();
//        expectedOutputVector.add(1);
//
//        GPTesting test = new GPTesting(inputVector, expectedOutputVector, maxVariables, populationSize, generations, maxOperations);
//        test.runTest();
//
//        assertNotNull(getBestProgram());
//        assertEquals(bestFitness, 0);
//    }

    public void test1(){
        System.out.println("AAAAA");
    }

    public static void main(String[] args) {
        int maxVariables = 3;
        int populationSize = 10000;
        int generations = 10;
        int maxOperations = 1000;
        int minValue=1;
        int maxValue=1000;
        ArrayList<Integer> inputVector = new ArrayList<>();
        inputVector.add(5);

        ArrayList<Object> expectedOutputVector = new ArrayList<>();
        expectedOutputVector.add(789);

        GPTesting test = new GPTesting(inputVector, expectedOutputVector, maxVariables, populationSize,
                generations, maxOperations, minValue, maxValue);
        test.runTest();
        int bestIndex = test.getBestProgram();
        System.out.println("--------------------------------");
        System.out.println(bestIndex + " " + test.bestFitness);
        System.out.println(test.outputList.get(bestIndex));
        System.out.println(test.population.get(bestIndex));
//        assertNotNull(test.getBestProgram());
//        assertEquals(test.bestFitness, 0);
    }
}
