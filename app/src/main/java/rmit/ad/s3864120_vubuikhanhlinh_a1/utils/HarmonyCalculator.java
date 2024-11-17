package rmit.ad.s3864120_vubuikhanhlinh_a1.utils;

import java.util.ArrayList;
import java.util.List;

import rmit.ad.s3864120_vubuikhanhlinh_a1.models.Color;

public class HarmonyCalculator {

    // Complementary Color: Accepts HSL as a float array
    public static List<Color> getComplementaryColor(float[] hsl) {
        List<Color> result = new ArrayList<>();
        float complementaryHue = (hsl[0] + 180) % 360; // 180° shift
        String complementaryHex = ColorUtils.hslToHex(complementaryHue, hsl[1], hsl[2]);
        result.add(new Color("Complementary", complementaryHex));
        return result;
    }

    // Analogous Colors: Accepts HSL as a float array
    public static List<Color> getAnalogousColors(float[] hsl) {
        List<Color> result = new ArrayList<>();
        float hue1 = (hsl[0] + 30) % 360; // +30°
        float hue2 = (hsl[0] - 30 + 360) % 360; // -30°
        result.add(new Color("Analogous 1", ColorUtils.hslToHex(hue1, hsl[1], hsl[2])));
        result.add(new Color("Base Color", ColorUtils.hslToHex(hsl[0], hsl[1], hsl[2])));
        result.add(new Color("Analogous 2", ColorUtils.hslToHex(hue2, hsl[1], hsl[2])));
        return result;
    }

    // Triadic Colors: Accepts HSL as a float array
    public static List<Color> getTriadicColors(float[] hsl) {
        List<Color> result = new ArrayList<>();
        float hue1 = (hsl[0] + 120) % 360; // +120°
        float hue2 = (hsl[0] + 240) % 360; // +240°
        result.add(new Color("Triadic 1", ColorUtils.hslToHex(hue1, hsl[1], hsl[2])));
        result.add(new Color("Base Color", ColorUtils.hslToHex(hsl[0], hsl[1], hsl[2])));
        result.add(new Color("Triadic 2", ColorUtils.hslToHex(hue2, hsl[1], hsl[2])));
        return result;
    }
}
