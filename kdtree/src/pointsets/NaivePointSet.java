package pointsets;

import java.util.List;

/**
 * Naive nearest-neighbor implementation using a linear scan.
 */
public class NaivePointSet<T extends Point> implements PointSet<T> {
    // TODO: add fields as necessary

    /**
     * Instantiates a new NaivePointSet with the given points.
     * @param points a non-null, non-empty list of points to include
     *               Assumes that the list will not be used externally afterwards (and thus may
     *               directly store and mutate the array).
     */
    public NaivePointSet(List<T> points) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Returns the point in this set closest to the given point in O(N) time, where N is the number
     * of points in this set.
     */
    @Override
    public T nearest(Point target) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<T> allPoints() {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
