package ir.Parka.keychi.helper;

import android.graphics.Color;

/**
 * Static utility class for working with colors.
 */
public final class HelperColor {
    /**
     * To prevent instantiation, this constructor is private and throws an exception when invoked.
     */
    private HelperColor() {
        throw new UnsupportedOperationException("HelperColor cannot be instantiated");
    }

    /**
     * Blends two colours to produce a single output colour. No check is done to ensure the
     * provided colors are valid ARGB hex codes. Providing invalid codes will result in an
     * undefined result.
     *
     * @param color1
     * 		ARGB hex code for the first colour to blend
     * @param color2
     * 		ARGB hex code for the second colour to blend
     * @param ratio
     * 		the proportion of {@code color1} to use in the blended result, between 0 and 1 (inclusive)
     * @return the ARGB code for the blended colour
     * @throws IllegalArgumentException
     * 		if ratio is not between 0 and 1 (inclusive)
     */
    public static int blendColors(final int color1, final int color2, final float ratio) {
        if (ratio < 0 || ratio > 1) {
            throw new IllegalArgumentException("ratio must be between 0 and 1 (inclusive)");
        }

        final float a = (Color.alpha(color1) * ratio) + (Color.alpha(color2) * (1f - ratio));
        final float r = (Color.red(color1) * ratio) + (Color.red(color2) * (1f - ratio));
        final float g = (Color.green(color1) * ratio) + (Color.green(color2) * (1f - ratio));
        final float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * (1f - ratio));

        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }
}