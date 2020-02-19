package graphpathfinding;

import priorityqueues.DoubleMapMinPQ;
import timing.Timer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @see ShortestPathFinder for more method documentation
 */
public class AStarPathFinder<VERTEX> extends ShortestPathFinder<VERTEX> {

    private AStarGraph<VERTEX> graph;
    /**
     * Creates a new AStarPathFinder that works on the provided graph.
     */
    public AStarPathFinder(AStarGraph<VERTEX> graph) {
        this.graph = graph;
    }

    @Override
    public ShortestPathResult<VERTEX> findShortestPath(VERTEX start, VERTEX end, Duration timeout) {

        Timer timer = new Timer(timeout);
        DoubleMapMinPQ<VERTEX> fringe = new DoubleMapMinPQ<>();
        List<VERTEX> solution = new ArrayList<>();
        Map<VERTEX, Double> distances = new HashMap<>();
        Map<VERTEX, VERTEX> prevPoint = new HashMap<>();
        int states = 0;

        fringe.add(start, this.graph.estimatedDistanceToGoal(start, end));
        distances.put(start, 0.0);

        while (!fringe.isEmpty() && !timer.isTimeUp()) {
            VERTEX min = fringe.removeMin();
            if (min.equals(end)) {
                break;
            }
            states++;
            for (WeightedEdge<VERTEX> neighbor : this.graph.neighbors(min)) {
                if (distances.containsKey(neighbor.to()) &&
                    distances.get(neighbor.to()) >
                        neighbor.weight() + distances.get(min)) {

                    distances.put(neighbor.to(), neighbor.weight() +
                        distances.get(min));
                    prevPoint.put(neighbor.to(), min);
                    if (fringe.contains(neighbor.to())) {
                        fringe.changePriority(neighbor.to(), neighbor.weight() +
                            distances.get(min) +
                            this.graph.estimatedDistanceToGoal(min, end));
                    }

                } else if (!distances.containsKey(neighbor.to())) {
                    distances.put(neighbor.to(), neighbor.weight() +
                        distances.get(min));
                    prevPoint.put(neighbor.to(), min);
                    fringe.add(neighbor.to(),
                        neighbor.weight() + distances.get(min) +
                            this.graph.estimatedDistanceToGoal(neighbor.to(),
                                end));
                }
            }
        }

        if (timer.isTimeUp()) {
            return new ShortestPathResult.Timeout<>(states, timer.elapsedDuration());
        }

        if (!distances.containsKey(end)) {
            return new ShortestPathResult.Unsolvable<>(states, timer.elapsedDuration());
        }

        VERTEX current = end;
        while (!current.equals(start)) {
            solution.add(0, current);
            current = prevPoint.get(current);
        }
        solution.add(0, start);
        return new ShortestPathResult.Solved<>(
            solution,
            distances.get(end),
            states,
            timer.elapsedDuration()
        );
    }

    @Override
    protected AStarGraph<VERTEX> graph() {
        return this.graph;
    }
}
