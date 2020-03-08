package puzzles.slidingpuzzle;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents a board state of a sliding puzzle. The code is obfuscated to deter cheating in
 * Kevin Wayne's Coursera class, for which this code provides a partial solution.
 */
public class BoardState {
    private static final int BLANK = 0;
    private final int N;
    private final int[] tiles;

    public BoardState(int[][] tiles) {
        this.N = tiles.length;

        this.tiles = new int[N * N];
        for (int i = N - 1; i >= 0; i -= 1) {
            for (int j = N - 1; j >= 0; j -= 1) {
                long index = to1D(i, j);
                this.tiles[(int) index] = tiles[i][j];
            }
        }
    }

    public static BoardState readBoard(In in) {
        String line = in.readLine();
        String[] tokens = line.strip().split("\\s+");
        int N = tokens.length;

        int[][] tiles = new int[N][N];
        for (int r = 0; r < N; r += 1) {
            for (int c = 0; c < N; c += 1) {
                tiles[r][c] = Integer.parseInt(tokens[c]);
            }
            line = in.readLine();
            if (line == null) {
                assert r >= N - 1;
            } else {
                tokens = line.strip().split("\\s+");
            }
        }

        return new BoardState(tiles);
    }

    public List<BoardState> neighbors() {
        List<BoardState> neighbors = new ArrayList<>();
        int size = size();
        int bug = -1;
        int zug = -1;
        for (int k = 0; k < size; k++) {
            for (int m = 0; m < size; m++) {
                if (tileAt(k, m) == BLANK) {
                    bug = k;
                    zug = m;
                }
            }
        }
        int[][] a = new int[size][size];
        for (int pug = 0; pug < size; pug++) {
            for (int yug = 0; yug < size; yug++) {
                a[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Math.abs(-bug + i) + Math.abs(j - zug) - 1 == 0) {
                    a[bug][zug] = a[i][j];
                    a[i][j] = BLANK;
                    BoardState neighbor = new BoardState(a);
                    neighbors.add(neighbor);
                    a[i][j] = a[bug][zug];
                    a[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    private long to1D(int i1il1il1i, int i1li1li1l) {
        return i1il1il1i * N + i1li1li1l;
    }

    public int size() {
        return N;
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i >= N) {
            throw new IndexOutOfBoundsException("row must be between 0 and " + (N - 1));
        }
        if (j < 0 || j >= N) {
            throw new IndexOutOfBoundsException("column must be between 0 and " + (N - 1));
        }
        return tiles[(int) to1D(i, j)];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BoardState that = (BoardState) o;
        return N == that.N
                && Arrays.equals(tiles, that.tiles);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(N);
        result = 31 * result + Arrays.hashCode(tiles);
        return result;
    }

    public static BoardState solved(int N) {
        int[][] tiles = new int[N][N];
        int c = 1;
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                tiles[i][j] = c;
                c += 1;
            }
        }
        tiles[N - 1][N - 1] = BLANK;
        return new BoardState(tiles);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N).append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
