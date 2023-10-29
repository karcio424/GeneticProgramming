#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <time.h>

#define MAX_LEN 10000
#define POPSIZE 100000
#define DEPTH 5
#define GENERATIONS 100
#define TSIZE 2
#define PMUT_PER_NODE 0.05
#define CROSSOVER_PROB 0.9

#define ADD 110
#define SUB 111
#define MUL 112
#define DIV 113
#define FSET_START ADD
#define FSET_END DIV

double fitness[POPSIZE];
char pop[POPSIZE][MAX_LEN];
static double x[FSET_START];
static double minrandom, maxrandom;
static char program[MAX_LEN];
static int PC;
static int varnumber, fitnesscases, randomnumber;
static double fbestpop = 0.0, favgpop = 0.0;
static long seed;
static double avg_len;

#define MAX_TARGETS 100  // Maximum fitness cases
double targets[MAX_TARGETS][10];

double run() { /* Interpreter */
    char primitive = program[PC++];
    if (primitive < FSET_START)
        return x[primitive];
    switch (primitive) {
        case ADD: return run() + run();
        case SUB: return run() - run();
        case MUL: return run() * run();
        case DIV: {
            double num = run(), den = run();
            if (fabs(den) <= 0.001)
                return num;
            else
                return num / den;
        }
    }
    return 0.0;
}

int traverse(char buffer[], int buffercount) {
    if (buffer[buffercount] < FSET_START)
        return buffercount + 1;

    switch (buffer[buffercount]) {
        case ADD:
        case SUB:
        case MUL:
        case DIV:
            return traverse(buffer, traverse(buffer, buffercount + 1));
    }
    return 0;
}

void setup_fitness(char *fname) {
    FILE *fp = fopen(fname, "r");
    if (fp == NULL) {
        printf("ERROR: Please provide a data file\n");
        exit(0);
    }

    fscanf(fp, "%d %d %lf %lf %d", &varnumber, &randomnumber, &minrandom, &maxrandom, &fitnesscases);

    if (varnumber + randomnumber >= FSET_START)
        printf("too many variables and constants\n");

    for (int i = 0; i < fitnesscases; i++) {
        for (int j = 0; j <= varnumber; j++) {
            fscanf(fp, "%lf", &targets[i][j]);
        }
    }

    fclose(fp);
}

double fitness_function(char Prog[], double targets[][10]) {
    int i = 0, len;
    double result, fit = 0.0;
    len = traverse(Prog, 0);

    for (i = 0; i < fitnesscases; i++) {
        for (int j = 0; j < varnumber; j++)
            x[j] = targets[i][j];

        strcpy(program, Prog);  // Copy the content of Prog into program
        PC = 0;
        result = run();
        fit += fabs(result - targets[i][varnumber]);
    }
    return -fit;
}

int grow(char buffer[], int pos, int max, int depth) {
    char prim = rand() % 2;
    int one_child;

    if (pos >= max)
        return -1;

    if (pos == 0)
        prim = 1;

    if (prim == 0 || depth == 0) {
        prim = rand() % (varnumber + randomnumber);
        buffer[pos] = prim;
        return pos + 1;
    } else {
        prim = rand() % (FSET_END - FSET_START + 1) + FSET_START;

        switch (prim) {
            case ADD:
            case SUB:
            case MUL:
            case DIV:
                buffer[pos] = prim;
                one_child = grow(buffer, pos + 1, max, depth - 1);
                if (one_child < 0)
                    return -1;
                return grow(buffer, one_child, max, depth - 1);
        }
    }
    return 0;
}

int print_indiv(char buffer[], int buffercounter) {
    int a1 = 0, a2;
    if (buffer[buffercounter] < FSET_START) {
        if (buffer[buffercounter] < varnumber)
            printf("X%d ", buffer[buffercounter] + 1);
        else
            printf("%lf", x[buffer[buffercounter]]);
        return buffercounter + 1;
    }

    switch (buffer[buffercounter]) {
        case ADD:
            printf("(");
            a1 = print_indiv(buffer, buffercounter + 1);
            printf(" + ");
            break;
        case SUB:
            printf("(");
            a1 = print_indiv(buffer, buffercounter + 1);
            printf(" - ");
            break;
        case MUL:
            printf("(");
            a1 = print_indiv(buffer, buffercounter + 1);
            printf(" * ");
            break;
        case DIV:
            printf("(");
            a1 = print_indiv(buffer, buffercounter + 1);
            printf(" / ");
            break;
    }
    a2 = print_indiv(buffer, a1);
    printf(")");
    return a2;
}

char buffer[MAX_LEN];

char *create_random_indiv(int depth) {
    char *ind;
    int len;
    len = grow(buffer, 0, MAX_LEN, depth);

    while (len < 0)
        len = grow(buffer, 0, MAX_LEN, depth);

    ind = (char *)malloc(len);
    memcpy(ind, buffer, len);
    return ind;
}

void create_random_pop(int n, int depth, double fitness[]) {
    for (int i = 0; i < n; i++) {
        char *ind = create_random_indiv(depth);
        strcpy(pop[i], ind);
        fitness[i] = fitness_function(ind, targets);
        free(ind);
    }
}

void stats(double fitness[], int gen) {
    int i, best = rand() % POPSIZE;
    int node_count = 0;
    fbestpop = fitness[best];
    favgpop = 0.0;

    for (i = 0; i < POPSIZE; i++) {
        node_count += traverse(pop[i], 0);
        favgpop += fitness[i];
        if (fitness[i] > fbestpop) {
            best = i;
            fbestpop = fitness[i];
        }
    }
    avg_len = (double)node_count / POPSIZE;
    favgpop /= POPSIZE;
    printf("Generation=%d Avg Fitness=%lf Best Fitness=%lf Avg Size=%lf\nBest Individual: ",
           gen, -favgpop, -fbestpop, avg_len);
    print_indiv(pop[best], 0);
    printf("\n");
    fflush(stdout);
}

int tournament(double fitness[], int tsize) {
    int best = rand() % POPSIZE;
    int competitor;
    double fbest = -1.0e34;

    for (int i = 0; i < tsize; i++) {
        competitor = rand() % POPSIZE;
        if (fitness[competitor] > fbest) {
            fbest = fitness[competitor];
            best = competitor;
        }
    }
    return best;
}

int negative_tournament(double fitness[], int tsize) {
    int worst = rand() % POPSIZE;
    int competitor;
    double fworst = 1e34;

    for (int i = 0; i < tsize; i++) {
        competitor = rand() % POPSIZE;
        if (fitness[competitor] < fworst) {
            fworst = fitness[competitor];
            worst = competitor;
        }
    }
    return worst;
}

char *crossover(char *parent1, char *parent2) {
    int xo1start, xo1end, xo2start, xo2end;
    char *offspring;
    int len1 = traverse(parent1, 0);
    int len2 = traverse(parent2, 0);
    int lenoff;

    xo1start = rand() % len1;
    xo1end = traverse(parent1, xo1start);

    xo2start = rand() % len2;
    xo2end = traverse(parent2, xo2start);

    lenoff = xo1start + (xo2end - xo2start) + (len1 - xo1end);

    offspring = (char *)malloc(lenoff);
    memcpy(offspring, parent1, xo1start);
    memcpy(offspring + xo1start, parent2 + xo2start, (xo2end - xo2start));
    memcpy(offspring + xo1start + (xo2end - xo2start), parent1 + xo1end, (len1 - xo1end));

    return offspring;
}

char *mutation(char *parent, double pmut) {
    int len = traverse(parent, 0);
    int i;
    int mutsite;
    char *parentcopy = (char *)malloc(len);
    memcpy(parentcopy, parent, len);

    for (i = 0; i < len; i++) {
        if ((double)rand() / RAND_MAX < pmut) {
            mutsite = i;
            if (parentcopy[mutsite] < FSET_START)
                parentcopy[mutsite] = rand() % (varnumber + randomnumber);
            else
                switch (parentcopy[mutsite]) {
                    case ADD:
                    case SUB:
                    case MUL:
                    case DIV:
                        parentcopy[mutsite] = rand() % (FSET_END - FSET_START + 1) + FSET_START;
                }
        }
    }
    return parentcopy;
}

void print_parms() {
    printf("-- TINY GP (C version) --\n");
    printf("SEED=%ld\nMAX_LEN=%d\nPOPSIZE=%d\nDEPTH=%d\nCROSSOVER_PROB=%lf\nPMUT_PER_NODE=%lf\nMIN_RANDOM=%lf\nMAX_RANDOM=%lf\nGENERATIONS=%d\nTSIZE=%d\n----------------------------------\n",
           seed, MAX_LEN, POPSIZE, DEPTH, CROSSOVER_PROB, PMUT_PER_NODE, minrandom, maxrandom, GENERATIONS, TSIZE);
}

int main(int argc, char *argv[]) {
    char *fname = "xmxp2.dat"; //zad1 fun1 dzi1 -10 10
    long s = -1;

    if (argc == 3) {
        s = atoi(argv[1]);
        fname = argv[2];
    } else if (argc == 2) {
        fname = argv[1];
    }

    srand(time(NULL));

    setup_fitness(fname);
    for (int i = 0; i < FSET_START; i++)
        x[i] = (maxrandom - minrandom) * ((double)rand() / RAND_MAX) + minrandom;
    create_random_pop(POPSIZE, DEPTH, fitness);

    print_parms();
    stats(fitness, 0);

    for (int gen = 1; gen < GENERATIONS; gen++) {
        if (fbestpop > -1e-5) {
            printf("PROBLEM SOLVED\n");
            exit(0);
        }

        for (int indivs = 0; indivs < POPSIZE; indivs++) {
            if ((double)rand() / RAND_MAX < CROSSOVER_PROB) {
                int parent1 = tournament(fitness, TSIZE);
                int parent2 = tournament(fitness, TSIZE);
                char *newind = crossover(pop[parent1], pop[parent2]);
                double newfit = fitness_function(newind, targets);
                int offspring = negative_tournament(fitness, TSIZE);
                free(pop[offspring]);
                strcpy(pop[offspring], newind);
                fitness[offspring] = newfit;
                free(newind);
            } else {
                int parent = tournament(fitness, TSIZE);
                char *newind = mutation(pop[parent], PMUT_PER_NODE);
                double newfit = fitness_function(newind, targets);
                int offspring = negative_tournament(fitness, TSIZE);
                free(pop[offspring]);
                strcpy(pop[offspring], newind);
                fitness[offspring] = newfit;
                free(newind);
            }
        }
        stats(fitness, gen);
    }

    printf("PROBLEM *NOT* SOLVED\n");
    exit(1);
}
