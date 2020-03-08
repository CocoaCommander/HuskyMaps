package seamcarving;

import graphpathfinding.AStarGraph;
import graphpathfinding.AStarPathFinder;
import graphpathfinding.ShortestPathFinder;
import graphpathfinding.WeightedEdge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public class Node {
        int x;
        int y;
        double energy;
        boolean vertical;

        public Node(int x, int y, double energy) {
            this.x = x;
            this.y = y;
            this.energy = energy;
            this.vertical = true;
        }
    }

    public class PictureGraph implements AStarGraph<Node> {

        Map<Double, List<Double>> neighbors;

        public PictureGraph(double[][] energies, boolean vertical) {
            if (vertical) {
                for (double[] i : energies) {
                    for (double j : i) {
                        if (j == 0) {

                        }
                    }
                }
            } else {

            }
        }

        @Override
        public List<WeightedEdge<Node>> neighbors(Node node) {
            List<WeightedEdge<Node>> neighbors = new ArrayList<>();

            return neighbors;
        }

        @Override
        public double estimatedDistanceToGoal(Node current, Node goal) {
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
        // TODO replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Calculates and returns a minimum-energy vertical seam in the current image.
     * The returned array will have the same length as the height of the image.
     * A value of v at index i of the output indicates that pixel (v, i) is in the seam.
     */

    @Override
    public List<Integer> findVerticalSeam(double[][] energies) {
        // TODO replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
