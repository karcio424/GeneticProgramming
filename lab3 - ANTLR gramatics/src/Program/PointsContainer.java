package Program;

import java.util.List;
import java.util.Random;

public class PointsContainer {
    private final List<Integer> mutationPoints;
    private final List<Integer> crossoverPoints;
    private final static Random rand = new Random(1915);

    public PointsContainer(List<Integer> mutationPoints, List<Integer> crossoverPoints) {
        this.mutationPoints = mutationPoints;
        this.crossoverPoints = crossoverPoints;
        prepareLists();
    }

    public int getRandomMutationPoint() {
        int temp = rand.nextInt(mutationPoints.size());
        return mutationPoints.get(temp);
    }

    public int getRandomCrossoverPoint() {
        int temp = rand.nextInt(crossoverPoints.size());
        return crossoverPoints.get(temp);
    }

    private void prepareLists() {
        if (mutationPoints.size() < 2)
            mutationPoints.add(0);
        if (mutationPoints.size() < 3)
            mutationPoints.remove(mutationPoints.size() - 1);
        if (crossoverPoints.size() < 2)
            crossoverPoints.add(0);
        if (crossoverPoints.size() < 3)
            crossoverPoints.remove(crossoverPoints.size() - 1);
    }
}