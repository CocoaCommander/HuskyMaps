package seamcarving;

import graphpathfinding.AStarGraph;
import graphpathfinding.AStarPathFinder;
import graphpathfinding.ShortestPathFinder;

import java.util.List;

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

    @Override
    public List<Integer> findHorizontalSeam(double[][] energies) {
        // TODO replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<Integer> findVerticalSeam(double[][] energies) {
        // TODO replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
