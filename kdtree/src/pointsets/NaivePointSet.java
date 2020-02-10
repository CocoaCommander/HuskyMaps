package pointsets;

import java.util.List;

/**
 * Naive nearest-neighbor implementation using a linear scan.
 */
public class NaivePointSet<T extends Point> implements PointSet<T> {

    private List<T> pointList;

    /**
     * Instantiates a new NaivePointSet with the given points.
     *
     * @param points a non-null, non-empty list of points to include
     *               Assumes that the list will not be used externally afterwards (and thus may
     *               directly store and mutate the array).
     */
    public NaivePointSet(List<T> points) {
        this.pointList = points;
    }

    /**
     * Returns the point in this set closest to the given point in O(N) time, where N is the number
     * of points in this set.
     */
    @Override
    public T nearest(Point target) {
        double initial = pointList.get(0).distanceSquaredTo(target);
        T nearest = pointList.remove(0);
        for (T point : this.pointList) {
            double compare = point.distanceSquaredTo(target);
            if (compare < initial) {
                nearest = point;
                initial = compare;
            }
        }
        return nearest;
    }

    @Override
    public List<T> allPoints() {
        return pointList;
    }
}
