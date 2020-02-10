package pointsets;

import java.util.Collections;
import java.util.List;

/**
 * Fast nearest-neighbor implementation using a k-d tree.
 */
public class KDTreePointSet<T extends Point> implements PointSet<T> {

    private class KDNode {
        KDNode less;
        KDNode more;
        T data;

        public KDNode(T data) {
            this.less = null;
            this.more = null;
            this.data = data;
        }
    }

    private KDNode root;
    private List<T> points;

    /**
     * Instantiates a new KDTreePointSet with a shuffled version of the given points.
     *
     * Randomizing the point order decreases likeliness of ending up with a spindly tree if the
     * points are sorted somehow.
     *
     * @param points a non-null, non-empty list of points to include.
     *               Assumes that the list will not be used externally afterwards (and thus may
     *               directly store and mutate the array).
     */
    public static <T extends Point> KDTreePointSet<T> createAfterShuffling(List<T> points) {
        Collections.shuffle(points);
        return new KDTreePointSet<T>(points);
    }

    /**
     * Instantiates a new KDTreePointSet with the given points.
     *
     * @param points a non-null, non-empty list of points to include.
     *               Assumes that the list will not be used externally afterwards (and thus may
     *               directly store and mutate the array).
     *
     * Learned about handling up/down vs left/right insertions from:
     * https://github.com/mgruben/Kd-Trees/blob/master/KdTree.java
     */
    KDTreePointSet(List<T> points) {
        for (T point : points) {
            this.root = buildTree(this.root, point, true);
        }
        this.points = points;
    }

    private KDNode buildTree(KDNode node, T point, boolean leftRight) {
        // When you reach the end of the tree
        if (node == null) {
            return new KDNode(point);
        }
        double compare = compareLocation(node, point, leftRight);

        // Whichever value is lesser is put on the lesser side
        // Compare determines whether to compare at x or y
        if (compare < 0) {
            node.less = buildTree(node.less, point, !leftRight);

            // Whichever value is greater is put on the greater side
            // Compare determines whether to compare at x or y
        } else if (compare > 0) {
            node.more = buildTree(node.more, point, !leftRight);

            // Nodes on same dividing line that are not the same
            // are automatically put left
        } else if (!node.data.equals(point)) {
            node.less = buildTree(node.less, point, !leftRight);
        }
        return node;
    }

    // if comparing left and right compare x
    // if comparing up and down compare y
    private double compareLocation(KDNode node, T point, boolean leftRight) {
        if (leftRight) {
            return point.x() - node.data.x();
        } else {
            return point.y() - node.data.y();
        }
    }

    /**
     * Returns the point in this set closest to the given point in (usually) O(log N) time, where
     * N is the number of points in this set.
     * <p>
     * Pruning conditions inspired by https://github.com/mgruben/Kd-Trees/blob/master/KdTree.java
     */
    @Override
    public T nearest(Point target) {
        return nearest(root, target, root.data, true);
    }

    private T nearest(KDNode current, Point target, T closest, boolean leftRight) {

        // return last point in tree
        if (current == null) {
            return closest;
        }

        // return a direct click on point
        if (current.data.equals(target)) {
            return (T) target;
        }

        // if the current point is closer to the target than the closest pt from before,
        // current pt is now closest pt
        if (current.data.distanceSquaredTo(target) <
            closest.distanceSquaredTo(target)) {
            closest = current.data;
        }

        // Determines which side to most likely look at
        double section = compareLocation(current, (T) target, leftRight);

        // If pt is expected to be on the left/bottom side
        if (section <= 0) {
            closest = nearest(current.less, target, closest, !leftRight);

            // if the distance from the current point is less than the distance from the closest point,
            // we explore the other side
            if (Math.pow(section, 2) < closest.distanceSquaredTo(target)) {
                closest = nearest(current.more, target, closest, !leftRight);
            }

            // pt expected to be on right/top side
        } else {
            closest = nearest(current.more, target, closest, !leftRight);

            // if the distance from the current point is less than the distance from the closest point,
            // we explore the other side
            if (Math.pow(section, 2) < closest.distanceSquaredTo(target)) {
                closest = nearest(current.less, target, closest, !leftRight);
            }
        }
        return closest;
    }

    @Override
    public List<T> allPoints() {
        return this.points;
    }
}
