package arrayutils;

import java.util.Arrays;
import java.util.Comparator;
/**
 * Make sure to check out the interface for more method details:
 *
 * @see ArraySearcher
 */
public class BinaryRangeSearcher<T, U> implements ArraySearcher<T, U> {

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
        if (sortUsing == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
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
        this.termArray = array;
        this.matcher = matcher;
    }

    public MatchResult<T> findAllMatches(U target) {
        if (target == null) {
            throw new IllegalArgumentException("target cannot be null");
        }
        int start = getFirst(0, this.termArray.length - 1, target);
        int end = getLast(0, this.termArray.length - 1, target);
        MatchResult<T> matches = new MatchResult<T>(this.termArray, start, end + 1);
        return matches;
    }

    private int getFirst(int front, int back, U target) {
        if (front > back) {
            return 0;
        }
        int mid = (front + back) / 2;
        if (matcher.match(termArray[mid], target) == 0 &&
            (mid == 0 || (matcher.match(termArray[mid - 1], target) < 0))) {
            return mid;
        } else if (matcher.match(termArray[mid], target) > 0) {
            return getFirst(front, mid - 1, target);
        } else if (matcher.match(termArray[mid], target) < 0) {
            return getFirst(mid + 1, back, target);
        } else {
            return getFirst(front, mid - 1, target);
        }
    }

    private int getLast(int front, int back, U target) {
        if (front > back) {
            return -1;
        }
        int mid = (front + back) / 2;
        if (matcher.match(termArray[mid], target) == 0 &&
            (mid == termArray.length - 1 || (matcher.match(termArray[mid + 1], target) > 0))) {
            return mid;
        } else if (matcher.match(termArray[mid], target) > 0) {
            return getLast(front, mid - 1, target);
        } else if (matcher.match(termArray[mid], target) < 0) {
            return getLast(mid + 1, back, target);
        } else {
            return getLast(mid + 1, back, target);
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
