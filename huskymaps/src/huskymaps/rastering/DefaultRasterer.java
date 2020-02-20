package huskymaps.rastering;

import huskymaps.graph.Coordinate;

/**
 * @see Rasterer
 */
public class DefaultRasterer implements Rasterer {
    public TileGrid rasterizeMap(Coordinate ul, Coordinate lr, int depth) {
        System.out.println("Since you haven't implemented rasterizeMap, nothing is displayed in your browser.");
        // TODO: replace this with your code
        Tile[][] grid = null;
        return new TileGrid(grid);
    }
}
