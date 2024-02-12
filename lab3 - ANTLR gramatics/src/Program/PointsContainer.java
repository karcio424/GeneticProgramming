package Program;

import java.util.List;
import java.util.Random;

public class PointsContainer {
    private final List<Integer> mutationPoints;
    private final List<Integer> crossoverPoints;
    private final Random rand = new Random();

    public PointsContainer(List<Integer> mutationPoints, List<Integer> crossoverPoints, boolean prepare) {
        this.mutationPoints = mutationPoints;
        this.crossoverPoints = crossoverPoints;
        if (prepare) {
            prepareLists();
        }
    }

    public int getRandomMutationPoint() {
        return mutationPoints.get(rand.nextInt(mutationPoints.size()));
    }

    public int getRandomCrossoverPoint() {
        return crossoverPoints.get(rand.nextInt(crossoverPoints.size()));
    }

    private void prepareLists() {
        if (mutationPoints.size() < 2)
            mutationPoints.add(0);
        else
            mutationPoints.remove(mutationPoints.size() - 1);
        if (crossoverPoints.size() < 2)
            crossoverPoints.add(0);
        else
            crossoverPoints.remove(crossoverPoints.size() - 1);
    }
}