package rmit.ad.s3864120_vubuikhanhlinh_a1.models;

public class Color {
    // Fields for each attribute
    private String colorName;
    private String hexCode;

    // Constructor
    public Color(String colorName, String hexCode) {
        this.colorName = colorName;
        this.hexCode = hexCode;
    }

    public Color(String hexCode) {
        this.colorName = "Unnamed"; // Default name
        this.hexCode = hexCode;
    }

    // Getters and Setters
    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }

    // Utility Method: Convert hex to RGB (optional)
    public int[] getRGB() {
        int r = Integer.valueOf(hexCode.substring(1, 3), 16);
        int g = Integer.valueOf(hexCode.substring(3, 5), 16);
        int b = Integer.valueOf(hexCode.substring(5, 7), 16);
        return new int[]{r, g, b};
    }

    // Optional: Convert to HSL if needed for color harmonies
    public float[] getHSL() {
        int[] rgb = getRGB();
        float r = rgb[0] / 255f;
        float g = rgb[1] / 255f;
        float b = rgb[2] / 255f;

        float max = Math.max(r, Math.max(g, b));
        float min = Math.min(r, Math.min(g, b));
        float h = 0, s, l = (max + min) / 2;

        if (max == min) {
            h = s = 0; // achromatic
        } else {
            float d = max - min;
            s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
            if (max == r) {
                h = (g - b) / d + (g < b ? 6 : 0);
            } else if (max == g) {
                h = (b - r) / d + 2;
            } else if (max == b) {
                h = (r - g) / d + 4;
            }
            h /= 6;
        }
        return new float[]{h * 360, s * 100, l * 100};
    }

    // ToString method (optional, useful for debugging)
    @Override
    public String toString() {
        return "Color{" +
                "colorName='" + colorName + '\'' +
                ", hexCode='" + hexCode + '\'' +
                '}';
    }
}
