package seamcarving;

import edu.princeton.cs.algs4.Picture;

public class DualGradientEnergyFunction implements EnergyFunction {

    /**
     * Returns the energy of pixel (x, y) in the given image.
     *
     * @throws IndexOutOfBoundsException if (x, y) is not inside of the given image.
     */

    @Override
    public double apply(Picture picture, int x, int y) {
        if (!Utils.inBounds(x, y, picture.width(), picture.height())) {
            throw new IndexOutOfBoundsException("pixel not in image");
        }
        double xGrad = xGradient(picture, x, y);
        double yGrad = yGradient(picture, x, y);

        return Math.sqrt(xGrad + yGrad);
    }

    private double xGradient(Picture picture, int x, int y) {
        double i = 0;
        int red = 0;
        int green = 0;
        int blue = 0;

        if (x == 0) {
            red = -3 * picture.get(x, y).getRed() +
                4 * picture.get(x + 1, y).getRed() -
                picture.get(x + 2, y).getRed();

            blue = -3 * picture.get(x, y).getBlue() +
                4 * picture.get(x + 1, y).getBlue() -
                picture.get(x + 2, y).getBlue();

            green = -3 * picture.get(x, y).getGreen() +
                4 * picture.get(x + 1, y).getGreen() -
                picture.get(x + 2, y).getGreen();

        } else if (x == picture.width() - 1) {
            red = -3 * picture.get(x, y).getRed() +
                4 * picture.get(x - 1, y).getRed() -
                picture.get(x - 2, y).getRed();

            blue = -3 * picture.get(x, y).getBlue() +
                4 * picture.get(x - 1, y).getBlue() -
                picture.get(x - 2, y).getBlue();

            green = -3 * picture.get(x, y).getGreen() +
                4 * picture.get(x - 1, y).getGreen() -
                picture.get(x - 2, y).getGreen();

        } else {
            red = picture.get(x + 1, y).getRed() -
                picture.get(x - 1, y).getRed();

            blue = picture.get(x + 1, y).getBlue() -
                picture.get(x - 1, y).getBlue();

            green = picture.get(x + 1, y).getGreen() -
                picture.get(x - 1, y).getGreen();

        }
        i = Math.pow(red, 2) + Math.pow(blue, 2) + Math.pow(green, 2);
        return i;
    }

    private double yGradient(Picture picture, int x, int y) {
        double i = 0;
        int red = 0;
        int green = 0;
        int blue = 0;

        if (y == 0) {
            red = -3 * picture.get(x, y).getRed() +
                4 * picture.get(x, y + 1).getRed() -
                picture.get(x, y + 2).getRed();

            blue = -3 * picture.get(x, y).getBlue() +
                4 * picture.get(x, y + 1).getBlue() -
                picture.get(x, y + 2).getBlue();

            green = -3 * picture.get(x, y).getGreen() +
                4 * picture.get(x, y + 1).getGreen() -
                picture.get(x, y + 2).getGreen();

            i = red + blue + green;
        } else if (y == picture.height() - 1) {
            red = -3 * picture.get(x, y).getRed() +
                4 * picture.get(x, y - 1).getRed() -
                picture.get(x, y - 2).getRed();

            blue = -3 * picture.get(x, y).getBlue() +
                4 * picture.get(x, y - 1).getBlue() -
                picture.get(x, y - 2).getBlue();

            green = -3 * picture.get(x, y).getGreen() +
                4 * picture.get(x, y - 1).getGreen() -
                picture.get(x, y - 2).getGreen();

            i = red + blue + green;
        } else {
            red = picture.get(x, y + 1).getRed() -
                picture.get(x, y - 1).getRed();

            blue = picture.get(x, y + 1).getBlue() -
                picture.get(x, y - 1).getBlue();

            green = picture.get(x, y + 1).getGreen() -
                picture.get(x, y - 1).getGreen();

            i = red + blue + green;
        }
        i = Math.pow(red, 2) + Math.pow(blue, 2) + Math.pow(green, 2);
        return i;
    }
}
