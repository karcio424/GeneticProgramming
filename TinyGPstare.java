import java.util.*;
import java.io.*;

public class TinyGP {
    private static final int ADD = 110;
    private static final int SUB = 111;
    private static final int MUL = 112;
    private static final int DIV = 113;
    private static final int FSET_START = ADD;
    private static final int FSET_END = DIV;

    private static final int MAX_LEN = 10000;
    private static final int POPSIZE = 100000;
    private static final int DEPTH = 5;
    private static final int GENERATIONS = 100;
    private static final int TSIZE = 2;

    private static final double PMUT_PER_NODE = 0.05;
    private static final double CROSSOVER_PROB = 0.9;

    private static double MIN_RANDOM = -10.0;
    private static double MAX_RANDOM = 10.0;

    private double[] fitness;
    private char[][] population;
    private static final Random random = new Random();

    private static double[] variables;
    private static int variableCount;
    private static int fitnessCases;
    private static int randomVariableCount;
    private static double[][] targets;

    private double bestPopulationFitness = 0.0;
    private double averagePopulationFitness = 0.0;

    private static long seed;
    private static double averageLength;

    public static void main(String[] args) {
        String filename = "zad1 fun4 dzi1 0 1.dat"; //zad1 fun1 dzi1 -10 10
        long seed = -1;

        if (args.length == 2) {
            seed = Long.parseLong(args[0]);
            filename = args[1];
        } else if (args.length == 1) {
            filename = args[0];
        }

        TinyGP tinyGP = new TinyGP(filename, seed);
        tinyGP.evolve();
    }

    public TinyGP(String filename, long randomSeed) {
        fitness = new double[POPSIZE];
        seed = randomSeed;
        if (seed >= 0)
            random.setSeed(seed);
        setupFitness(filename);
        variables = new double[FSET_START];
        population = createRandomPopulation(POPSIZE, DEPTH, fitness);
    }

    private void setupFitness(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String firstLine = reader.readLine();
            StringTokenizer tokenizer = new StringTokenizer(firstLine);
            variableCount = Integer.parseInt(tokenizer.nextToken().trim());
            randomVariableCount = Integer.parseInt(tokenizer.nextToken().trim());
            MIN_RANDOM = Double.parseDouble(tokenizer.nextToken().trim());
            MAX_RANDOM = Double.parseDouble(tokenizer.nextToken().trim());
            fitnessCases = Integer.parseInt(tokenizer.nextToken().trim());
            targets = new double[fitnessCases][variableCount + 1];

            if (variableCount + randomVariableCount >= FSET_START)
                System.out.println("Too many variables and constants");

            for (int i = 0; i < fitnessCases; i++) {
                String line = reader.readLine();
                tokenizer = new StringTokenizer(line);
                for (int j = 0; j <= variableCount; j++) {
                    targets[i][j] = Double.parseDouble(tokenizer.nextToken().trim());
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Please provide a data file");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("ERROR: Incorrect data format");
            System.exit(0);
        }
    }

    private double run(char[] program) {
        int programCounter = 0;
        if (program[programCounter] < FSET_START)
            return variables[program[programCounter]];
        switch (program[programCounter]) {
        case ADD:
            return run(program) + run(program);
        case SUB:
            return run(program) - run(program);
        case MUL:
            return run(program) * run(program);
        case DIV: {
            double num = run(program);
            double den = run(program);
            if (Math.abs(den) <= 0.001)
                return num;
            else
                return num / den;
        }
        }
        return 0.0;
    }

    private int traverse(char[] buffer, int bufferCount) {
        if (buffer[bufferCount] < FSET_START)
            return ++bufferCount;

        switch (buffer[bufferCount]) {
        case ADD:
        case SUB:
        case MUL:
        case DIV:
            return traverse(buffer, traverse(buffer, ++bufferCount));
        }
        return 0;
    }

    private int grow(char[] buffer, int pos, int max, int depth) {
        char primitive = (char) random.nextInt(2);
        int oneChild;

        if (pos >= max)
            return -1;

        if (pos == 0)
            primitive = 1;

        if (primitive == 0 || depth == 0) {
            primitive = (char) random.nextInt(variableCount + randomVariableCount);
            buffer[pos] = primitive;
            return pos + 1;
        } else {
            primitive = (char)(random.nextInt(FSET_END - FSET_START + 1) + FSET_START);
            switch (primitive) {
            case ADD:
            case SUB:
            case MUL:
            case DIV:
                buffer[pos] = primitive;
                oneChild = grow(buffer, pos + 1, max, depth - 1);
                if (oneChild < 0)
                    return -1;
                return grow(buffer, oneChild, max, depth - 1);
            }
        }
        return 0;
    }

    private char[] createRandomIndividual(int depth) {
        char[] individual;
        int len;

        len = grow(new char[MAX_LEN], 0, MAX_LEN, depth);

        while (len < 0)
            len = grow(new char[MAX_LEN], 0, MAX_LEN, depth);

        individual = new char[len];
        System.arraycopy(new char[MAX_LEN], 0, individual, 0, len);

        return individual;
    }

    private char[][] createRandomPopulation(int n, int depth, double[] fitness) {
        char[][] population = new char[n][];
        for (int i = 0; i < n; i++) {
            population[i] = createRandomIndividual(depth);
            fitness[i] = evaluateFitness(population[i]);
        }
        return population;
    }

    private void evaluatePopulationStats() {
        int bestIndex = random.nextInt(POPSIZE);
        int nodeCount = 0;

        bestPopulationFitness = fitness[bestIndex];
        averagePopulationFitness = 0.0;

        for (int i = 0; i < POPSIZE; i++) {
            nodeCount += traverse(population[i], 0);
            averagePopulationFitness += fitness[i];
            if (fitness[i] > bestPopulationFitness) {
                bestIndex = i;
                bestPopulationFitness = fitness[i];
            }
        }
        averageLength = (double) nodeCount / POPSIZE;
        averagePopulationFitness /= POPSIZE;
    }

    private int tournamentSelection(double[] fitness, int tournamentSize) {
        int best = random.nextInt(POPSIZE);
        double bestFitness = -1.0e34;

        for (int i = 0; i < tournamentSize; i++) {
            int competitor = random.nextInt(POPSIZE);
            if (fitness[competitor] > bestFitness) {
                bestFitness = fitness[competitor];
                best = competitor;
            }
        }
        return best;
    }

    private int negativeTournamentSelection(double[] fitness, int tournamentSize) {
        int worst = random.nextInt(POPSIZE);
        double worstFitness = 1e34;

        for (int i = 0; i < tournamentSize; i++) {
            int competitor = random.nextInt(POPSIZE);
            if (fitness[competitor] < worstFitness) {
                worstFitness = fitness[competitor];
                worst = competitor;
            }
        }
        return worst;
    }

    private char[] crossover(char[] parent1, char[] parent2) {
        int crossoverPoint1Start, crossoverPoint1End, crossoverPoint2Start, crossoverPoint2End;
        char[] offspring;
        int length1 = traverse(parent1, 0);
        int length2 = traverse(parent2, 0);
        int offspringLength;

        crossoverPoint1Start = random.nextInt(length1);
        crossoverPoint1End = traverse(parent1, crossoverPoint1Start);

        crossoverPoint2Start = random.nextInt(length2);
        crossoverPoint2End = traverse(parent2, crossoverPoint2Start);

        offspringLength = crossoverPoint1Start + (crossoverPoint2End - crossoverPoint2Start) + (length1 - crossoverPoint1End);

        offspring = new char[offspringLength];

        System.arraycopy(parent1, 0, offspring, 0, crossoverPoint1Start);
        System.arraycopy(parent2, crossoverPoint2Start, offspring, crossoverPoint1Start, (crossoverPoint2End - crossoverPoint2Start));
        System.arraycopy(parent1, crossoverPoint1End, offspring, crossoverPoint1Start + (crossoverPoint2End - crossoverPoint2Start), (length1 - crossoverPoint1End));

        return offspring;
    }

    private char[] mutation(char[] parent, double mutationProbability) {
        int length = traverse(parent, 0);
        char[] parentCopy = new char[length];
        System.arraycopy(parent, 0, parentCopy, 0, length);

        for (int i = 0; i < length; i++) {
            if (random.nextDouble() < mutationProbability) {
                int mutationSite = i;
                if (parentCopy[mutationSite] < FSET_START)
                    parentCopy[mutationSite] = (char) random.nextInt(variableCount + randomVariableCount);
                else {
                    switch (parentCopy[mutationSite]) {
                    case ADD:
                    case SUB:
                    case MUL:
                    case DIV:
                        parentCopy[mutationSite] = (char)(random.nextInt(FSET_END - FSET_START + 1) + FSET_START);
                    }
                }
            }
        }
        return parentCopy;
    }

    private double evaluateFitness(char[] program) {
        int i = 0;
        double result;
        double fitnessValue = 0.0;
        int length = traverse(program, 0);

        for (i = 0; i < fitnessCases; i++) {
            for (int j = 0; j < variableCount; j++)
                variables[j] = targets[i][j];
            result = run(program);
            fitnessValue += Math.abs(result - targets[i][variableCount]);
        }
        return -fitnessValue;
    }

    private void printParameters() {
        System.out.println("-- TINY GP (Java version) --");
        System.out.println("SEED=" + seed);
        System.out.println("MAX_LEN=" + MAX_LEN);
        System.out.println("POPSIZE=" + POPSIZE);
        System.out.println("DEPTH=" + DEPTH);
        System.out.println("CROSSOVER_PROB=" + CROSSOVER_PROB);
        System.out.println("PMUT_PER_NODE=" + PMUT_PER_NODE);
        System.out.println("MIN_RANDOM=" + MIN_RANDOM);
        System.out.println("MAX_RANDOM=" + MAX_RANDOM);
        System.out.println("GENERATIONS=" + GENERATIONS);
        System.out.println("TSIZE=" + TSIZE);
        System.out.println("----------------------------------");
    }

    public void evolve() {
        int gen;
        char[] newIndividual;
        double newFitness;

        printParameters();
        evaluatePopulationStats();
        System.out.println("Generation=0 Avg Fitness=" + (-averagePopulationFitness) +
            " Best Fitness=" + (-bestPopulationFitness) +
            " Avg Size=" + averageLength);

        for (gen = 1; gen < GENERATIONS; gen++) {
            if (bestPopulationFitness > -1e-5) {
                System.out.println("PROBLEM SOLVED");
                System.exit(0);
            }

            for (int indivs = 0; indivs < POPSIZE; indivs++) {
                if (random.nextDouble() < CROSSOVER_PROB) {
                    int parent1 = tournamentSelection(fitness, TSIZE);
                    int parent2 = tournamentSelection(fitness, TSIZE);
                    newIndividual = crossover(population[parent1], population[parent2]);
                } else {
                    int parent = tournamentSelection(fitness, TSIZE);
                    newIndividual = mutation(population[parent], PMUT_PER_NODE);
                }

                newFitness = evaluateFitness(newIndividual);

                int offspring = negativeTournamentSelection(fitness, TSIZE);
                population[offspring] = newIndividual;
                fitness[offspring] = newFitness;
            }

            evaluatePopulationStats();
            System.out.println("Generation=" + gen + " Avg Fitness=" + (-averagePopulationFitness) +
                " Best Fitness=" + (-bestPopulationFitness) +
                " Avg Size=" + averageLength);
        }

        System.out.println("PROBLEM *NOT* SOLVED");
        System.exit(1);
    }
}