package huskymaps.rastering;

import huskymaps.graph.Coordinate;
import huskymaps.utils.Constants;

/**
 * @see Rasterer
 */
public class DefaultRasterer implements Rasterer {
    /**
     * Given the bounding box of a user viewport, finds the grid of images that best matches the
     * viewport. These images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the grid is referred
     * to as a "tile".
     * <ul>
     *     <li>The tiles must be from the depth requested.</li>
     *     <li>The tiles collected must cover the most longitudinal distance per pixel
     *     (LonDPP) possible, while still covering less than or equal to the amount of
     *     longitudinal distance per pixel in the query box for the user viewport size. </li>
     *     <li>Contains all tiles that intersect the query bounding box that fulfill the
     *     above condition.</li>
     *     <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param ul    the upper-left coordinate of the user viewport
     * @param lr    the lower-right coordinate of the user viewport
     * @param depth the depth of tiles to return
     * @return the grid of tiles meeting the conditions described
     */

    public TileGrid rasterizeMap(Coordinate ul, Coordinate lr, int depth) {

        int latStart = (int) ((Constants.ROOT_ULLAT - ul.lat()) / Constants.LAT_PER_TILE[depth]);
        if (latStart < 0) {
            latStart = 0;
        }

        int latEnd = (int) ((Constants.ROOT_ULLAT - lr.lat()) / Constants.LAT_PER_TILE[depth]);
        if (latEnd >= Constants.NUM_X_TILES_AT_DEPTH[depth]) {
            latEnd = Constants.NUM_X_TILES_AT_DEPTH[depth] - 1;
        }

        int lonStart = (int) ((ul.lon() - Constants.ROOT_ULLON) / Constants.LON_PER_TILE[depth]);
        if (lonStart < 0) {
            lonStart = 0;
        }

        int lonEnd = (int) ((lr.lon() - Constants.ROOT_ULLON) / Constants.LON_PER_TILE[depth]);
        if (lonEnd >= Constants.NUM_X_TILES_AT_DEPTH[depth]) {
            lonEnd = 0;
        }

        int width = latEnd - latStart + 1;
        int height = lonEnd - lonStart + 1;
        Tile[][] grid = new Tile[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new Tile(depth, j + lonStart, i + latStart);
            }
        }
        return new TileGrid(grid);
    }
}
