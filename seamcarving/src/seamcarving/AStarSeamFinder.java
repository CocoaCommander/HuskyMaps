package seamcarving;

import graphpathfinding.AStarGraph;
import graphpathfinding.AStarPathFinder;
import graphpathfinding.ShortestPathFinder;
import graphpathfinding.ShortestPathResult;
import graphpathfinding.WeightedEdge;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AStarSeamFinder extends SeamFinder {
    /*
    Use this method to create your ShortestPathFinder.
    This will be overridden during grading to use our solution path finder, so you don't get
    penalized again for any bugs in code from previous assignments
    */
    @Override
    protected <VERTEX> ShortestPathFinder<VERTEX> createPathFinder(AStarGraph<VERTEX> graph) {
        return new AStarPathFinder<>(graph);
    }

    public class Pixel {
        int x;
        int y;
        double energy;
        boolean isFindVert;

        public Pixel(int x, int y, double energy, boolean isFindVert) {
            this.x = x;
            this.y = y;
            this.energy = energy;
            this.isFindVert = isFindVert;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Pixel pixel = (Pixel) o;
            if (isFindVert) {
                return y == pixel.y;
            } else {
                return x == pixel.x;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, energy);
        }
    }

    public class PictureGraph implements AStarGraph<Pixel> {

        int height;
        int width;
        double[][] energies;

        public PictureGraph(double[][] energies) {
            this.height = energies[0].length - 1;
            this.width = energies.length - 1;
            this.energies = energies;
        }

        @Override
        public List<WeightedEdge<Pixel>> neighbors(Pixel pixel) {
            List<WeightedEdge<Pixel>> neighbors = new ArrayList<>();
            if (pixel.isFindVert) {
                if (pixel.x == 0) {
                    for (int i = 0; i <= 1; i++) {
                        Pixel next = new Pixel(
                            0 + i,
                            pixel.y + 1,
                            energies[0 + i][pixel.y + 1],
                            pixel.isFindVert);
                        WeightedEdge<Pixel> edge = new WeightedEdge<>(pixel, next, next.energy);
                        neighbors.add(edge);
                    }
                } else if (pixel.x == width) {
                    for (int i = 0; i <= 1; i++) {
                        Pixel next = new Pixel(
                            pixel.x - i,
                            pixel.y + 1,
                            energies[pixel.x - i][pixel.y + 1],
                            pixel.isFindVert);
                        WeightedEdge<Pixel> edge = new WeightedEdge<>(pixel, next, next.energy);
                        neighbors.add(edge);
                    }
                } else {
                    for (int i = -1; i <= 1; i++) {
                        Pixel next = new Pixel(
                            pixel.x + i,
                            pixel.y + 1,
                            energies[pixel.x + i][pixel.y + 1],
                            pixel.isFindVert);
                        WeightedEdge<Pixel> edge = new WeightedEdge<>(pixel, next, next.energy);
                        neighbors.add(edge);
                    }
                }
            } else {
                if (pixel.y == 0) {
                    for (int i = 0; i <= 1; i++) {
                        Pixel next = new Pixel(
                            pixel.x + 1,
                            pixel.y + i,
                            energies[pixel.x + 1][pixel.y + i],
                            pixel.isFindVert);
                        WeightedEdge<Pixel> edge = new WeightedEdge(pixel, next, next.energy);
                        neighbors.add(edge);
                    }
                } else if (pixel.y == height) {
                    for (int i = 0; i <= 1; i++) {
                        Pixel next = new Pixel(
                            pixel.x + 1,
                            pixel.y - i,
                            energies[pixel.x + 1][pixel.y - i],
                            pixel.isFindVert);
                        WeightedEdge<Pixel> edge = new WeightedEdge(pixel, next, next.energy);
                        neighbors.add(edge);
                    }
                } else {
                    for (int i = -1; i <= 1; i++) {
                        Pixel next = new Pixel(
                            pixel.x + 1,
                            pixel.y + i,
                            energies[pixel.x + 1][pixel.y - i],
                            pixel.isFindVert);
                        WeightedEdge<Pixel> edge = new WeightedEdge(pixel, next, next.energy);
                        neighbors.add(edge);
                    }
                }
            }
            return neighbors;
        }

        @Override
        public double estimatedDistanceToGoal(Pixel current, Pixel goal) {
            return 0.0;
        }
    }


    /**
     * Calculates and returns a minimum-energy horizontal seam in the current image.
     * The returned array will have the same length as the width of the image.
     * A value of v at index i of the output indicates that pixel (i, v) is in the seam.
     */

    @Override
    public List<Integer> findHorizontalSeam(double[][] energies) {
        /**
         * For each y value run A*
         * Save that object as a ShortestPathResult object
         * Using the ShortestPath solution, get the solution weight and if it's
         * less than the current max weight overwrite previous ShortestPath
         * At the end get the y values and add them to the list to return
         */

        int goal = energies.length - 1;
        double minEnergy = Double.MAX_VALUE;
        Map<Double, List<Pixel>> weightToSolution = new HashMap<>();
        List<Integer> seam = new ArrayList<>();

        PictureGraph picture = new PictureGraph(energies);
        ShortestPathFinder<Pixel> pathFinder = createPathFinder(picture);


        for (int i = 0; i < energies[0].length; i++) {
            Pixel start = new Pixel(0, i, energies[0][i], false);
            Pixel end = new Pixel(goal, 0, 0.0, false);
            ShortestPathResult result = pathFinder.findShortestPath(start, end, Duration.ofSeconds(10));
            double weight = result.solutionWeight();
            weightToSolution.put(weight, result.solution());
            if (weight < minEnergy) {
                minEnergy = weight;
            }
        }
        for (Pixel p : weightToSolution.get(minEnergy)) {
            seam.add(p.y);
        }
        return seam;
    }

    /**
     * Calculates and returns a minimum-energy vertical seam in the current image.
     * The returned array will have the same length as the height of the image.
     * A value of v at index i of the output indicates that pixel (v, i) is in the seam.
     */

    @Override
    public List<Integer> findVerticalSeam(double[][] energies) {
        int goal = energies[0].length - 1;
        double minEnergy = Double.MAX_VALUE;
        Map<Double, List<Pixel>> weightToSolution = new HashMap<>();
        List<Integer> seam = new ArrayList<>();

        PictureGraph picture = new PictureGraph(energies);
        ShortestPathFinder<Pixel> pathFinder = createPathFinder(picture);


        for (int i = 0; i < energies.length; i++) {
            Pixel start = new Pixel(i, 0, energies[i][0], true);
            Pixel end = new Pixel(0, goal, 0.0, true);
            ShortestPathResult result = pathFinder.findShortestPath(start, end, Duration.ofSeconds(10));
            double weight = result.solutionWeight();
            weightToSolution.put(weight, result.solution());
            if (weight < minEnergy) {
                minEnergy = weight;
            }
        }
        for (Pixel p : weightToSolution.get(minEnergy)) {
            seam.add(p.x);
        }
        return seam;
    }
}
