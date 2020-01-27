package arrayutils;

import java.util.Arrays;
import java.util.Comparator;
/**
 * Make sure to check out the interface for more method details:
 *
 * @see ArraySearcher
 */
public class BinaryRangeSearcher<T, U> implements ArraySearcher<T, U> {

    public class BSTNode {
        public int index;
        public T term;
        public BSTNode left;
        public BSTNode right;

        public BSTNode(T term, int index) {
            this.index = index;
            this.term = term;
            this.left = null;
            this.right = null;
        }
    }

    private BSTNode overallRoot;
    private T[] termArray;
    private Matcher<T, U> matcher;

    /**
     * Creates a BinaryRangeSearcher for the given array of items that matches items using the
     * Matcher matchUsing.
     *
     * First sorts the array in place using the Comparator sortUsing. (Assumes that the given array
     * will not be used externally afterwards.)
     *
     * Requires that sortUsing sorts the array such that for any possible reference item U,
     * calling matchUsing.match(T, U) on each T in the sorted array will result in all negative
     * values first, then all 0 values, then all positive.
     *
     * For example:
     * sortUsing lexicographic string sort: [  aaa,  abc,   ba,  bzb, cdef ]
     * matchUsing T is prefixed by U
     * matchUsing.match for prefix "b":     [   -1,   -1,    0,    0,    1 ]
     *
     * @throws IllegalArgumentException if array is null or contains null
     * @throws IllegalArgumentException if sortUsing or matchUsing is null
     */
    public static <T, U> BinaryRangeSearcher<T, U> forUnsortedArray(T[] array,
                                                                    Comparator<T> sortUsing,
                                                                    Matcher<T, U> matchUsing) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (sortUsing == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        if (matchUsing == null) {
            throw new IllegalArgumentException("Matcher cannot be null");
        }
        for (T item : array) {
            if (item == null) {
                throw new IllegalArgumentException("Array cannot be null");
            }
        }
        Arrays.sort(array, sortUsing);
        return new BinaryRangeSearcher<>(array, matchUsing);
    }

    /**
     * Requires that array is sorted such that for any possible reference item U,
     * calling matchUsing.match(T, U) on each T in the sorted array will result in all negative
     * values first, then all 0 values, then all positive.
     *
     * Assumes that the given array will not be used externally afterwards (and thus may directly
     * store and mutate the array).
     * @throws IllegalArgumentException if array is null or contains null
     * @throws IllegalArgumentException if matcher is null
     */
    protected BinaryRangeSearcher(T[] array, Matcher<T, U> matcher) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        for (T item : array) {
            if (item == null) {
                throw new IllegalArgumentException("Array cannot be null");
            }
        }
        if (matcher == null) {
            throw new IllegalArgumentException("Matcher cannot be null");
        }
        this.overallRoot = buildTree(array, 0, array.length - 1);
        this.termArray = array;
        this.matcher = matcher;
    }

    private BSTNode buildTree(T[] array, int first, int last) {
        if (first > last) {
            return null;
        }
        int mid = (first + last) / 2;
        BSTNode node = new BSTNode(array[mid], mid);
        node.left = buildTree(array, first, mid - 1);
        node.right = buildTree(array, mid + 1, last);
        return node;
    }

    public MatchResult<T> findAllMatches(U target) {
        if (target == null) {
            throw new IllegalArgumentException("target cannot be null");
        }
        int end = getFirst(this.overallRoot, target);
        int start = getLast(this.overallRoot, target);
        MatchResult<T> matches = new MatchResult<T>(this.termArray, start, end);
        return matches;
    }

    private int getFirst(BSTNode node, U target) {
        if (node.right == null &&
            node.left == null) {
            if (matcher.match(node.term, target) == 0) {
                return node.index + 1;
            } else {
                return 0;
            }
        } else if (matcher.match(node.right.term, target) == 0) {
            return node.right.index + 1;
        } else if (matcher.match(node.term, target) >= 0) {
            return getFirst(node.left, target);
        } else {
            return getFirst(node.right, target);
        }
    }

    private int getLast(BSTNode node, U target) {
        if (node.right == null &&
            node.left == null) {
            if (matcher.match(node.term, target) == 0) {
                return node.index;
            } else {
                return 0;
            }
        } else if (matcher.match(node.left.term, target) == 0) {
            return node.left.index;
        } else if (matcher.match(node.term, target) < 0) {
            return getLast(node.right, target);
        } else {
            return getLast(node.left, target);
        }
    }

    public static class MatchResult<T> extends AbstractMatchResult<T> {
        final T[] array;
        final int start;
        final int end;

        /**
         * Use this constructor if there are no matching results.
         * (This lets us use Arrays.copyOfRange to make a new T[], which can be difficult to
         * acquire otherwise due to the way Java handles generics.)
         */
        protected MatchResult(T[] array) {
            this(array, 0, 0);
        }

        protected MatchResult(T[] array, int startInclusive, int endExclusive) {
            this.array = array;
            this.start = startInclusive;
            this.end = endExclusive;
        }

        @Override
        public int count() {
            return this.end - this.start;
        }

        @Override
        public T[] unsorted() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }
    }
}
