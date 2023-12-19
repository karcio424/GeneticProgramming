/*
 * Original Author:    Riccardo Poli (email: rpoli@essex.ac.uk)
 *
 * Refactored by Jakub Banach and Karol Błaszczak
 * 
 * For the Genetic Programming laboratory classes in the Computer Science and Intelligent Systems course at AGH University of Science and Technology.
 * 
 * Final version for laboratory no.2
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;

public class TinyGP {
    double[] fitness;
    char[][] pop;
    static Random random = new Random();
    static final int
            ADD = 110,
            SUB = 111,
            MUL = 112,
            DIV = 113,
            SIN = 114,
            COS = 115,
            FSET_START = ADD,
            FSET_END = COS;
    static double[] x = new double[FSET_START];
    static double minRandom, maxRandom;
    static char[] program;
    static int PC;
    static int varNumber, fitnessCases, randomNumber;
    static double fBestPop = 0.0, fAvgPop = 0.0;
    static long seed;
    static double avgLen;
    static final int
            MAX_LEN = 10000,
            POPSIZE = 100000,
            DEPTH = 5,
            GENERATIONS = 500,
            TSIZE = 2;
    public static final double
            PMUT_PER_NODE = 0.05,
            CROSSOVER_PROB = 0.9;
    static double[][] targets;
    static char[] buffer = new char[MAX_LEN];
    static String solution = "";

    public static boolean solved = false;
    long longestProgram = 0L;
    public static String finalSolution;
    public static double finalBestFit = -Double.MAX_VALUE;

    double run() {
        char primitive = program[PC++];
        if (primitive < FSET_START) {
            return x[primitive];
        }
        switch (primitive) {
            case ADD:
                return run() + run();
            case SUB:
                return run() - run();
            case MUL:
                return run() * run();
            case DIV: {
                double num = run(), den = run();
                if (Math.abs(den) <= 0.001)
                    return num;
                else
                    return num / den;
            }
            case SIN:
                return Math.sin(run());
            case COS:
                return Math.cos(run());
        }
        return 0.0; // should never get here
    }

    int traverse(char[] buffer, int bufferCount) {
        return buffer[bufferCount] < FSET_START
                ? ++bufferCount
                : switch (buffer[bufferCount]) {
            case ADD, SUB, MUL, DIV -> traverse(buffer, traverse(buffer, ++bufferCount));
            case SIN, COS -> traverse(buffer, ++bufferCount);
            default -> 0; // should never get here
        };
    }

    void setupFitness(String fileName) {
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            String line = in.readLine();
            StringTokenizer tokens = new StringTokenizer(line);
            varNumber = Integer.parseInt(tokens.nextToken().trim());
            randomNumber = Integer.parseInt(tokens.nextToken().trim());
            minRandom = Double.parseDouble(tokens.nextToken().trim());
            maxRandom = Double.parseDouble(tokens.nextToken().trim());
            fitnessCases = Integer.parseInt(tokens.nextToken().trim());
            targets = new double[fitnessCases][varNumber + 1];
            if (varNumber + randomNumber >= FSET_START) {
                System.out.println("too many variables and constants");
            }

            for (int i = 0; i < fitnessCases; i++) {
                line = in.readLine();
                tokens = new StringTokenizer(line);
                for (int j = 0; j <= varNumber; j++) {
                    targets[i][j] = Double.parseDouble(tokens.nextToken().trim());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Please provide a data file");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("ERROR: Incorrect data format: " + e.getMessage());
            System.exit(0);
        }
    }

    double fitnessFunction(char[] Prog) {
        double result, fit = 0.0;

        traverse(Prog, 0);
        for (int i = 0; i < fitnessCases; i++) {
            if (varNumber >= 0) {
                System.arraycopy(targets[i], 0, x, 0, varNumber);
            }
            program = Prog;
            PC = 0;
            result = run();
            fit += Math.abs(result - targets[i][varNumber]);
        }
        return (-1) * fit;
    }

    int grow(int pos, int max, int depth) {
        if (pos >= max) {
            return -1;
        }
        char prim = pos == 0 ? 1 : (char) random.nextInt(2);

        if (prim == 0 || depth == 0) {
            prim = (char) random.nextInt(varNumber + randomNumber);
            buffer[pos] = prim;
            return pos + 1;
        } else {
            prim = (char) (random.nextInt(FSET_END - FSET_START + 1) + FSET_START);
            buffer[pos] = prim;
            int oneChild = grow(pos + 1, max, depth - 1);
            if (oneChild < 0) {
                return -1;
            }
            switch (prim) {
                case ADD, SUB, MUL, DIV -> {
                    return grow(oneChild, max, depth - 1);
                }
                case SIN, COS -> {
                    return oneChild;
                }
            }
            return 0; // should never get here
        }
    }


    char[] createRandomIndiv() {
        int len;
        do {
            len = grow(0, MAX_LEN, DEPTH);
        } while (len < 0);
        char[] ind = new char[len];
        System.arraycopy(buffer, 0, ind, 0, len);
        return ind;
    }

    char[][] createRandomPop() {
        char[][] pop = new char[POPSIZE][];
        for (int i = 0; i < POPSIZE; i++) {
            pop[i] = createRandomIndiv();
            fitness[i] = fitnessFunction(pop[i]);
        }
        return pop;
    }


    void stats(int gen) {
        int best = random.nextInt(POPSIZE);
        int nodeCount = 0;
        solution = "";
        fBestPop = fitness[best];
        fAvgPop = 0.0;

        for (int i = 0; i < POPSIZE; i++) {
            longestProgram = Long.MIN_VALUE;
            nodeCount += traverse(pop[i], 0);
            fAvgPop += fitness[i];
            StringBuilder stringBuilder = new StringBuilder();
            programOutput(stringBuilder, pop[i], 0);
            String output = stringBuilder.toString();
            long programLength = output.length();
            if (fitness[i] > fBestPop) {
                solution = output;
                fBestPop = fitness[i];
            }
            if (programLength > longestProgram) {
                longestProgram = programLength;
            }
        }
        avgLen = (double) nodeCount / POPSIZE;
        fAvgPop /= POPSIZE;
        System.out.print("Generation=" + gen + " Avg Fitness=" + (-fAvgPop) +
                " Best Fitness=" + (-fBestPop) + " Avg Size=" + avgLen +
                "\nBest Individual: ");
        System.out.println(solution);
        System.out.print("\n");
        System.out.flush();
        if (!solution.equals("") && (-fBestPop) < (-finalBestFit)) {
            finalSolution = solution;
            finalBestFit = fBestPop;
        }
    }

    int tournament() {
        int best = random.nextInt(POPSIZE), competitor;
        double fbest = -1.0e34;

        for (int i = 0; i < TSIZE; i++) {
            competitor = random.nextInt(POPSIZE);
            StringBuilder stringBuilder = new StringBuilder();
            programOutput(stringBuilder, pop[competitor], 0);
            int competitorLength = stringBuilder.toString().length();
            double value = fitness[competitor];
            if (value > fbest && competitorLength < longestProgram) {
                fbest = value;
                best = competitor;
            }
        }
        return best;
    }

    int negativeTournament() {
        int worst = random.nextInt(POPSIZE), competitor;
        double fworst = 1e34;

        for (int i = 0; i < TSIZE; i++) {
            competitor = random.nextInt(POPSIZE);
            double value = fitness[competitor];
            if (value < fworst) {
                fworst = value;
                worst = competitor;
            }
        }
        return worst;
    }

    char[] crossover(char[] parent1, char[] parent2) {
        int len1 = traverse(parent1, 0);
        int len2 = traverse(parent2, 0);

        int xo1start = random.nextInt(len1);
        int xo1end = traverse(parent1, xo1start);

        int xo2start = random.nextInt(len2);
        int xo2end = traverse(parent2, xo2start);

        int lenOff = xo1start + (xo2end - xo2start) + (len1 - xo1end);

        char[] offspring = new char[lenOff];

        System.arraycopy(parent1, 0, offspring, 0, xo1start);
        System.arraycopy(parent2, xo2start, offspring, xo1start, (xo2end - xo2start));
        System.arraycopy(parent1, xo1end, offspring, xo1start + (xo2end - xo2start), (len1 - xo1end));

        return offspring;
    }

    char[] mutation(char[] parent) {
        int len = traverse(parent, 0);
        char[] parentCopy = new char[len];

        System.arraycopy(parent, 0, parentCopy, 0, len);
        for (int i = 0; i < len; i++) {
            if (random.nextDouble() < PMUT_PER_NODE) {
                if (parentCopy[i] < FSET_START) {
                    parentCopy[i] = (char) random.nextInt(varNumber + randomNumber);
                } else
                    switch (parentCopy[i]) {
                        case ADD, SUB, MUL, DIV -> parentCopy[i] =
                                (char) (random.nextInt(DIV - FSET_START + 1) + FSET_START);
                        case SIN, COS -> parentCopy[i] =
                                (char) (random.nextInt(FSET_END - SIN + 1) + SIN);
                    }
            }
        }
        return parentCopy;
    }

    void printParams() {
        System.out.print("-- TINY GP (Java version) --\n");
        System.out.print("SEED=" + seed + "\nMAX_LEN=" + MAX_LEN +
                "\nPOPSIZE=" + POPSIZE + "\nDEPTH=" + DEPTH +
                "\nCROSSOVER_PROB=" + CROSSOVER_PROB +
                "\nPMUT_PER_NODE=" + PMUT_PER_NODE +
                "\nMIN_RANDOM=" + minRandom +
                "\nMAX_RANDOM=" + maxRandom +
                "\nGENERATIONS=" + GENERATIONS +
                "\nTSIZE=" + TSIZE +
                "\n----------------------------------\n");
    }

    public TinyGP(String fileName, long s) {
        fitness = new double[POPSIZE];
        seed = s;
        if (seed >= 0) {
            random.setSeed(seed);
        }
        setupFitness(fileName);
        for (int i = 0; i < FSET_START; i++) {
            x[i] = (maxRandom - minRandom) * random.nextDouble() + minRandom;
        }
        pop = createRandomPop();
    }

    void evolve() {
        solved = false;
        solution = "";
        finalSolution = "";
        finalBestFit = -Double.MAX_VALUE;
        int offspring, parent1, parent2, parent;
        double newFit;
        char[] newInd;
        printParams();
        stats(0);
        for (int gen = 1; gen < GENERATIONS; gen++) {
            if (fBestPop > -1e-5) {
                System.out.print("PROBLEM SOLVED\n");
                solved = true;
                break;
            }
            for (int indivs = 0; indivs < POPSIZE; indivs++) {
                if (random.nextDouble() < CROSSOVER_PROB) {
                    parent1 = tournament();
                    parent2 = tournament();
                    newInd = crossover(pop[parent1], pop[parent2]);
                } else {
                    parent = tournament();
                    newInd = mutation(pop[parent]);
                }
                newFit = fitnessFunction(newInd);
                offspring = negativeTournament();
                pop[offspring] = newInd;
                fitness[offspring] = newFit;
            }
            stats(gen);
        }
        if (!solved) {
            System.out.print("PROBLEM *NOT* SOLVED\n");
        }
    }

    int programOutput(StringBuilder stringBuilder, char[] buffer, int bufferCounter) {
        int a1 = 0;
        if (buffer[bufferCounter] < FSET_START) {
            if (buffer[bufferCounter] < varNumber) {
                stringBuilder.append("X").append(buffer[bufferCounter] + 1);
            } else {
                double value = x[buffer[bufferCounter]];
                String output = value < 0 ? "(" + value + ")" : Double.toString(value);
                stringBuilder.append(output);
            }
            return ++bufferCounter;
        }
        switch (buffer[bufferCounter]) {
            case ADD -> {
                stringBuilder.append("(");
                a1 = programOutput(stringBuilder, buffer, ++bufferCounter);
                stringBuilder.append(" + ");
            }
            case SUB -> {
                stringBuilder.append("(");
                a1 = programOutput(stringBuilder, buffer, ++bufferCounter);
                stringBuilder.append(" - ");
            }
            case MUL -> {
                stringBuilder.append("(");
                a1 = programOutput(stringBuilder, buffer, ++bufferCounter);
                stringBuilder.append(" * ");
            }
            case DIV -> {
                stringBuilder.append("(");
                a1 = programOutput(stringBuilder, buffer, ++bufferCounter);
                stringBuilder.append(" / ");
            }
            case SIN -> {
                stringBuilder.append("sin(");
                a1 = programOutput(stringBuilder, buffer, ++bufferCounter);
                stringBuilder.append(")");
                return a1;
            }
            case COS -> {
                stringBuilder.append("cos(");
                a1 = programOutput(stringBuilder, buffer, ++bufferCounter);
                stringBuilder.append(")");
                return a1;
            }
        }
        int a2 = programOutput(stringBuilder, buffer, a1);
        stringBuilder.append(")");
        return a2;
    }
    public static void main(String[] args) {
        String FileName = "lab2fun2dzi1 -1.570796 1.570796 0.01570796";
        String inputFileName = "./files/2 - function_calculated/" + FileName + ".dat";
        String outputFileName = "./files/3 - tinyGP_calculated/done " + FileName + ".dat";
        long seed = 123456;

        TinyGP tinyGP = new TinyGP(inputFileName, seed);
        tinyGP.evolve();

        if (TinyGP.solved) {
            System.out.println("Writing solution to output file: " + outputFileName);
            writeSolutionToFile(outputFileName, TinyGP.finalSolution);
        } else {
            System.out.println("Writing solution to output file: " + outputFileName);
            writeSolutionToFile(outputFileName, TinyGP.finalSolution);
        }
    }

    private static void writeSolutionToFile(String fileName, String solution) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(solution);
        } catch (IOException e) {
            System.err.println("Error writing to the output file: " + e.getMessage());
        }
    }
}