package namenotfoundunica.houseworkcalendar.other;

import android.graphics.Color;
import android.graphics.Paint;

public class ColorUtils {

    public static int getComplimentColor(Paint paint) {
        return getComplimentColor(paint.getColor());
    }

    /**
     * Returns the complimentary (opposite) color.
     * @param color int RGB color to return the compliment of
     * @return int RGB of compliment color
     */
    public static int getComplimentColor(int color) {
        // get existing colors
        //int alpha = Color.alpha(color);
        int red = Color.red(color);
        int blue = Color.blue(color);
        int green = Color.green(color);

        // find compliments
        red = (~red) & 0xff;
        blue = (~blue) & 0xff;
        green = (~green) & 0xff;

        return Color.rgb(red, green, blue);
    }

    /**
     * Converts an int RGB color representation into a hexadecimal {@link String}.
     * @param argbColor int RGB color
     * @return {@link String} hexadecimal color representation
     */
    public static String getHexStringForARGB(int argbColor) {
        String hexString = "#";
        hexString += ARGBToHex(Color.alpha(argbColor));
        hexString += ARGBToHex(Color.red(argbColor));
        hexString += ARGBToHex(Color.green(argbColor));
        hexString += ARGBToHex(Color.blue(argbColor));

        return hexString;
    }

    /**
     * Converts an int R, G, or B value into a hexadecimal {@link String}.
     * @param rgbVal int R, G, or B value
     * @return {@link String} hexadecimal value
     */
    private static String ARGBToHex(int rgbVal) {
        String hexReference = "0123456789ABCDEF";

        rgbVal = Math.max(0,rgbVal);
        rgbVal = Math.min(rgbVal,255);
        rgbVal = Math.round(rgbVal);

        return String.valueOf( hexReference.charAt((rgbVal-rgbVal%16)/16) + "" + hexReference.charAt(rgbVal%16) );
    }

    /**
     * Converts the given int color into a black o white color according Contrast Rules
     * @param colorIntValue int RGB color
     * @return int RGB of contrasted color (white or black)
     */
    public static int getContrastColor(int colorIntValue) {
        int red = Color.red(colorIntValue);
        int green = Color.green(colorIntValue);
        int blue = Color.blue(colorIntValue);
        double lum = (((0.299 * red) + ((0.587 * green) + (0.114 * blue))));
        return lum > 150 /*186*/ ? 0xFF000000 : 0xFFFFFFFF;
    }
}
