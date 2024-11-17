package rmit.ad.s3864120_vubuikhanhlinh_a1.utils;

public class ColorUtils {
    public static boolean isValidHexCode(String hexCode) {
        return hexCode != null && hexCode.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
    }
    // Converts a hex code to HSL values
    public static float[] hexToHSL(String hexCode) {
        int[] rgb = hexToRGB(hexCode); // First, convert Hex to RGB
        return rgbToHSL(rgb[0], rgb[1], rgb[2]); // Then convert RGB to HSL
    }

    // Converts a hex code to RGB values (0-255)
    public static int[] hexToRGB(String hexCode) {
        int r = Integer.valueOf(hexCode.substring(1, 3), 16);
        int g = Integer.valueOf(hexCode.substring(3, 5), 16);
        int b = Integer.valueOf(hexCode.substring(5, 7), 16);
        return new int[]{r, g, b};
    }

    // Converts RGB to HSL (Hue in degrees, Saturation and Lightness in percentages)
    public static float[] rgbToHSL(int r, int g, int b) {
        float red = r / 255f;
        float green = g / 255f;
        float blue = b / 255f;

        float max = Math.max(red, Math.max(green, blue));
        float min = Math.min(red, Math.min(green, blue));
        float h = 0, s, l = (max + min) / 2;

        if (max == min) {
            h = s = 0; // achromatic (gray)
        } else {
            float d = max - min;
            s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
            if (max == red) {
                h = (green - blue) / d + (green < blue ? 6 : 0);
            } else if (max == green) {
                h = (blue - red) / d + 2;
            } else {
                h = (red - green) / d + 4;
            }
            h *= 60; // convert to degrees
        }

        return new float[]{h, s * 100, l * 100};
    }

    // Converts HSL to Hex
    public static String hslToHex(float hue, float saturation, float lightness) {
        // Convert saturation and lightness to a 0-1 scale
        float s = saturation / 100f;
        float l = lightness / 100f;

        float c = (1 - Math.abs(2 * l - 1)) * s; // Chroma
        float x = c * (1 - Math.abs((hue / 60f) % 2 - 1)); // Second largest component
        float m = l - c / 2; // Match lightness

        float r = 0, g = 0, b = 0; // RGB in range [0, 1]

        if (hue < 60) {
            r = c; g = x; b = 0;
        } else if (hue < 120) {
            r = x; g = c; b = 0;
        } else if (hue < 180) {
            r = 0; g = c; b = x;
        } else if (hue < 240) {
            r = 0; g = x; b = c;
        } else if (hue < 300) {
            r = x; g = 0; b = c;
        } else {
            r = c; g = 0; b = x;
        }

        // Convert to [0, 255] range
        int red = Math.round((r + m) * 255);
        int green = Math.round((g + m) * 255);
        int blue = Math.round((b + m) * 255);

        // Convert RGB to Hex
        return String.format("#%02X%02X%02X", red, green, blue);
    }
}