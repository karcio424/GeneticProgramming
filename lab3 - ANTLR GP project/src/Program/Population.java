package Program;

public class Population implements Comparable<Population> {
    private final String program;
    private final double fitness;

    public Population(String program, double fitness) {
        this.program = program;
        this.fitness = fitness;
    }

    public String getProgram() {
        return program;
    }

    @Override
    public int compareTo(Population population) {
        return Double.compare(this.fitness, population.fitness);
    }
}
