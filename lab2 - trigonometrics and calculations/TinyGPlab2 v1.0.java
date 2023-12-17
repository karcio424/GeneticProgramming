import java.util.*;
import java.io.*;

public class TinyGP {
    private static final int ADD = 110;
    private static final int SUB = 111;
    private static final int MUL = 112;
    private static final int SIN = 113;
    private static final int COS = 114;
    private static final int DIV = 115;
    private static final int FSET_START = ADD;
    private static final int FSET_END = DIV;

    private static final int MAX_LEN = 10000;
    private static final int POPSIZE = 100000;
    private static final int DEPTH = 5;
    private static final int GENERATIONS = 100;
    // private static final int GENERATIONS = 25;
    private static final int TSIZE = 2;

    private static final double PMUT_PER_NODE = 0.05;
    private static final double CROSSOVER_PROB = 0.9;

    private static double MIN_RANDOM;
    private static double MAX_RANDOM;

    private double[] fitness;
    private char[][] population;
    private static final Random random = new Random();

    private static double[] variables = new double[FSET_START]; //!!!!!!!!!
    private static int variableCount;
    private static int fitnessCases;
    private static int randomVariableCount;
    private static double[][] targets;

    private double bestPopulationFitness = 0.0;
    private double averagePopulationFitness = 0.0;

    private static long seed;
    private static double averageLength;

    //dodane
    private static char[] program;
    private static int PC;
    private static char[] buffer = new char[MAX_LEN];
    private static String PLIK="";

    public static String tekstPliku(String filename) {
        // String newString = filename;
        System.out.println(filename);
        // newString = newString.replace("")
        int index = filename.indexOf("\\data\\");
        // System.out.println(index);
        if (index > 0) {
            return filename.replace("\\data\\", "\\done\\done ");
        }
        System.out.println(filename);
        return filename;
    }

    // funkcja uruchamiająca program
    public static void main(String[] args) {
        String filename = "lab2fun1dzi1 0.0 6.283184 0.03141592.dat";
        // zapisDoPliku("ASASAS");
        long seed = -1;

        if (args.length == 2) {
            seed = Long.parseLong(args[0]);
            filename = args[1];
        } else if (args.length == 1) {
            filename = args[0];
        }
        
        PLIK = tekstPliku(filename);
        zapisDoPliku("");
        System.out.println(PLIK);
        TinyGP TinyGP = new TinyGP(filename, seed);
        TinyGP.evolve();
    }

    // ustawianie parametrów początkowych
    public TinyGP(String filename, long randomSeed) {
        fitness = new double[POPSIZE];
        seed = randomSeed;
        if (seed >= 0)
            random.setSeed(seed);
        setupFitness(filename);
        for (int i = 0; i < FSET_START; i++)
            variables[i] = (MAX_RANDOM - MIN_RANDOM) * random.nextDouble() + MIN_RANDOM;
        // pop = create_random_pop(POPSIZE, DEPTH, fitness);
        // variables = new double[FSET_START];
        population = createRandomPopulation(POPSIZE, DEPTH, fitness);
    }

    // ustawianie wartości początkowych
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

    // inny parametr wejsciowy!!
    // private double run(char[] program) {
    private double run() {
        // int programCounter = 0;
        char primitive = program[PC++];
        if (primitive < FSET_START)
            return variables[primitive];
        switch (primitive) {
            case ADD:
                return run() + run();
            case SUB:
                return run() - run();
            case MUL:
                return run() * run();
            case SIN:
                return Math.sin(run());                 
            case COS:
                return Math.cos(run());
            case DIV: {
                double num = run();
                double den = run();
                if (Math.abs(den) <= 0.001)
                    return num;
                else
                    return num / den;
            }
        }
        return 0.0;
    }

    // 
    private int traverse(char[] buffer, int bufferCount) {
        if (buffer[bufferCount] < FSET_START)
            return ++bufferCount;

        switch (buffer[bufferCount]) {
            case ADD:
            case SUB:
            case MUL:
            case SIN:
            case COS:
            case DIV:
                return traverse(buffer, traverse(buffer, ++bufferCount));
        }
        return 0;
    }

    //generacja losowego programu
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
        } 
        else {
            primitive = (char)(random.nextInt(FSET_END - FSET_START + 1) + FSET_START);
            switch (primitive) {
                case ADD:
                case SUB:
                case MUL:
                case SIN:
                case COS:
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

    // tworzenie pojedynczego ...
    private char[] createRandomIndividual(int depth) {
        char[] individual;
        int len;

        len = grow(buffer, 0, MAX_LEN, depth);

        while (len < 0)
            len = grow(buffer, 0, MAX_LEN, depth);

        individual = new char[len];
        System.arraycopy(buffer, 0, individual, 0, len);

        return individual;
    }

    // 
    private char[][] createRandomPopulation(int n, int depth, double[] fitness) {
        char[][] population = new char[n][];
        for (int i = 0; i < n; i++) {
            population[i] = createRandomIndividual(depth);
            fitness[i] = evaluateFitness(population[i]);
        }
        return population;
    }

    //!!!!!!!!!!!!!
    private int evaluatePopulationStats(double[] fitness, char[][] population) { //double[] fitness, char[][] population
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
        return bestIndex;
        // printGeneration(population[bestIndex], 0);
        // 
    }

    //
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

    //
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

    // 
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
                        case SIN:
                        case COS:
                        case DIV:
                            parentCopy[mutationSite] = (char)(random.nextInt(FSET_END - FSET_START + 1) + FSET_START);
                    }
                }
            }
        }
        return parentCopy;
    }

    //bez program = program i pc=0
    private double evaluateFitness(char[] Prog) {
        int i = 0;
        double result;
        double fitnessValue = 0.0;
        int length = traverse(Prog, 0);

        for (i = 0; i < fitnessCases; i++) {
            for (int j = 0; j < variableCount; j++)
                variables[j] = targets[i][j];
            program = Prog;
            PC = 0;
            // result = run(program);
            result = run();
            fitnessValue += Math.abs(result - targets[i][variableCount]);
        }
        return -fitnessValue;
    }

    //
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
        int parent;
        int parent1;
        int parent2;
        int offspring;
        char[] newIndividual;
        double newFitness;
        int bestGenIndex;
        int output;

        printParameters();
        bestGenIndex = evaluatePopulationStats(fitness,population); //fitness, population, 0
        System.out.println("Generation=0 Avg Fitness=" + (-averagePopulationFitness) +
            " Best Fitness=" + (-bestPopulationFitness) +
            " Avg Size=" + averageLength);
        System.out.print("\n");
        System.out.flush();

        for (gen = 1; gen < GENERATIONS; gen++) {
            if (bestPopulationFitness > -0.01) {
                System.out.println("PROBLEM SOLVED");
                output = printGeneration(population[bestGenIndex], 0, "");
                System.out.println("PROBLEM SOLVED");
                // System.out.println(output);
                // zapisDoPliku("To jest tekst");
                // zapisDoPliku(output);
                System.exit(0);
            }

            for (int indivs = 0; indivs < POPSIZE; indivs++) {
                if (random.nextDouble() < CROSSOVER_PROB) {
                    parent1 = tournamentSelection(fitness, TSIZE);
                    parent2 = tournamentSelection(fitness, TSIZE);
                    newIndividual = crossover(population[parent1], population[parent2]);
                } else {
                    parent = tournamentSelection(fitness, TSIZE);
                    newIndividual = mutation(population[parent], PMUT_PER_NODE);
                }

                newFitness = evaluateFitness(newIndividual);

                offspring = negativeTournamentSelection(fitness, TSIZE);
                population[offspring] = newIndividual;
                fitness[offspring] = newFitness;
            }

            bestGenIndex = evaluatePopulationStats(fitness, population); //fitness, pop, 0
            System.out.println("Generation=" + gen + " Avg Fitness=" + (-averagePopulationFitness) +
                " Best Fitness=" + (-bestPopulationFitness) +
                " Avg Size=" + averageLength);
            if (gen == GENERATIONS-1) {
                output = printGeneration(population[bestGenIndex], 0, "");
                System.out.print("\n");
                System.out.flush();
                // zapisDoPliku("To jest tekst");
                // System.out.println(output);
                // zapisDoPliku(output);
            }
        }

        System.out.println("PROBLEM *NOT* SOLVED");
        System.exit(1);
    }
    
    private int printGeneration(char[] buffer, int buffercounter, String output) {
        int a1=0;
        int sin_bool=0;
        int cos_bool=0;
        // Wynik wynik = new Wynik();
        if (buffer[buffercounter] < FSET_START) {
            if (buffer[buffercounter] < variableCount) {
                int temp = buffer[buffercounter] + 1;
                String zlaczony = "X" + temp + " ";
                System.out.print(zlaczony);
                // output = output + "X" + String.valueOf(temp) + " ";
                zapisDoPliku(zlaczony);
            }
            else {
                double var = variables[buffer[buffercounter]];
                System.out.print(var);
                output += String.valueOf(var);
                zapisDoPliku(String.valueOf(var));
            }
            // wynik.pierwszaWartosc = a1; 
            // wynik.drugaWartosc = output; 
            return (++buffercounter);
        }
        switch (buffer[buffercounter]) {
        case ADD:
            System.out.print("(");
            output+="(";
            zapisDoPliku("(");
            a1 = printGeneration(buffer, ++buffercounter,output);
            System.out.print(" + ");
            output+=" + ";
            zapisDoPliku(" + ");
            break;
        case SUB:
            System.out.print("(");
            output+="(";
            zapisDoPliku("(");
            a1 = printGeneration(buffer, ++buffercounter,output);
            System.out.print(" - ");
            output+=" - ";
            zapisDoPliku(" - ");
            break;
        case MUL:
            System.out.print("(");
            output+="(";
            zapisDoPliku("(");
            a1 = printGeneration(buffer, ++buffercounter,output);
            System.out.print(" * ");
            output+=" * ";
            zapisDoPliku(" * ");
            break;
        case SIN:
            System.out.print("(");
            output+="(";
            zapisDoPliku("(");
            a1 = printGeneration(buffer, ++buffercounter, output);
            System.out.print(" * sin (");
            output+=" * sin (";
            zapisDoPliku(" * sin (");
            sin_bool = 1;
            break;
        case COS:
            System.out.print("(");
            output+="(";
            zapisDoPliku("(");
            a1 = printGeneration(buffer, ++buffercounter, output);
            System.out.print(" * cos (");
            output+=" * cos (";
            zapisDoPliku(" * cos (");
            cos_bool = 1;
            break;
        case DIV:
            System.out.print("(");
            output+="(";
            zapisDoPliku("(");
            a1 = printGeneration(buffer, ++buffercounter,output);
            System.out.print(" / ");
            output+=" / ";
            zapisDoPliku(" / ");
            break;
        }
        // Wynik wynik2 = printGeneration(buffer, a1, output);
        int a2 = printGeneration(buffer, a1, output);
        if (cos_bool == 1 || sin_bool == 1){
            System.out.print(")");
            output+=")";
            zapisDoPliku(")");
        }
        System.out.print(")");
        output+=")";
        zapisDoPliku(")");
        return a2;
        // return wynik;
    }

    private static void zapisDoPliku(String tekstDoZapisu){
        // String tekstDoZapisu = "To jest tekst, który zostanie zapisany do pliku.";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PLIK,true))) {
            writer.write(tekstDoZapisu);
            // System.out.println("Tekst został pomyślnie zapisany do pliku.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // private Wynik funkcjaZwrocWieleWartosci() {
    //     Wynik wynik = new Wynik();
    //     wynik.pierwszaWartosc = 42;
    //     wynik.drugaWartosc = 3.14;
    //     return wynik;
    // }
}