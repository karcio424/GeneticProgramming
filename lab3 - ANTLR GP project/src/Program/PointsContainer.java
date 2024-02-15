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
    }

    public int getRandomMutationPoint() {
        try {
            int point = mutationPoints.get(rand.nextInt(mutationPoints.size()));
            clear();
            return point;
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }

    public int getRandomCrossoverPoint() {
        try {
            int point = crossoverPoints.get(rand.nextInt(crossoverPoints.size()));
            clear();
            return point;
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }

    public List<Integer> getAllMutationPoints() {
        return mutationPoints;
    }

    public List<Integer> getAllCrossoverPoints() {
        return crossoverPoints;
    }

    private void clear() {
        mutationPoints.clear();
        crossoverPoints.clear();
    }
}