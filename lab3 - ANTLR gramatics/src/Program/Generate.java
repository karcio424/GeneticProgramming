package Program;

import java.util.ArrayList;
import java.util.List;

public class Generate {
    static int populationSize = 10;
    static List<String> variableList;

    public static void main(String[] args) {
        List<String> population = generatePopulation(populationSize, 1, 10, 3);
        for (int i = 0; i < populationSize; i++) {
            System.out.println(population.get(i));
        }
        System.out.println(variableList);
    }

    public static List<String> generatePopulation(int size, int min_r, int max_r, int numOfVars) {
        List<String> population = new ArrayList<>();
         variableList = new ArrayList<>(Main.generateRandomVariableArray(numOfVars, numOfVars));

        for (int i = 0; i < size; i++) {
            String randomProgram = GPUtils.generateRandomProgram(10, variableList, min_r, max_r);
            population.add(randomProgram);
        }

        return population;
    }
}
