package Program;

import java.util.ArrayList;
import java.util.List;

import Program.Main;

public class Generate {
    static int populationSize = 10;
    static List<String> variableList = new ArrayList<>(Main.generateRandomVariableArray(2, 5));

    public static void main(String[] args) {
        List<String> population = generatePopulation(populationSize);
        for (int i = 0; i < populationSize; i++) {
            System.out.println(population.get(i));
        }
        System.out.println(variableList);
    }

    public static List<String> generatePopulation(int size) {
        List<String> population = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            String randomProgram = Main.generateRandomProgram(10, variableList);
            population.add(randomProgram);
        }

        return population;
    }
}
