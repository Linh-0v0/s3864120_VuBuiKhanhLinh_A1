package rmit.ad.s3864120_vubuikhanhlinh_a1.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rmit.ad.s3864120_vubuikhanhlinh_a1.R;
import rmit.ad.s3864120_vubuikhanhlinh_a1.adapters.HarmonyAdapter;
import rmit.ad.s3864120_vubuikhanhlinh_a1.models.Color;
import rmit.ad.s3864120_vubuikhanhlinh_a1.utils.ColorUtils;
import rmit.ad.s3864120_vubuikhanhlinh_a1.utils.HarmonyCalculator;

public class ColorHarmonyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HarmonyAdapter harmonyAdapter;
    private List<List<Color>> harmonyList;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_harmony);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getSharedPreferences("Favorites", MODE_PRIVATE);

        // Generate harmonies
        String selectedColorHex = getIntent().getStringExtra("selectedColor");
        harmonyList = generateHarmonySchemes(selectedColorHex);

        // Set up adapter with the updated saveToFavorites
        harmonyAdapter = new HarmonyAdapter(this, harmonyList, harmony -> {
            int position = harmonyList.indexOf(harmony);
            String type = getHarmonyType(position); // Determine type based on position
            saveToFavorites(type, harmony); // Pass type and harmony to save
        });
        recyclerView.setAdapter(harmonyAdapter);
    }


    private List<List<Color>> generateHarmonySchemes(String selectedColorHex) {
        List<List<Color>> harmonies = new ArrayList<>();

        // Generate Complementary Colors
        List<Color> complementary = HarmonyCalculator.getComplementaryColor(ColorUtils.hexToHSL(selectedColorHex));
        for (Color color : complementary) {
            color.setColorName("Complementary Colors");
        }
        harmonies.add(complementary);

        // Generate Analogous Colors
        List<Color> analogous = HarmonyCalculator.getAnalogousColors(ColorUtils.hexToHSL(selectedColorHex));
        for (Color color : analogous) {
            color.setColorName("Analogous Colors");
        }
        harmonies.add(analogous);

        // Generate Triadic Colors
        List<Color> triadic = HarmonyCalculator.getTriadicColors(ColorUtils.hexToHSL(selectedColorHex));
        for (Color color : triadic) {
            color.setColorName("Triadic Colors");
        }
        harmonies.add(triadic);

        return harmonies;
    }

    private String getHarmonyType(int position) {
        switch (position) {
            case 0:
                return "Complementary Colors";
            case 1:
                return "Analogous Colors";
            case 2:
                return "Triadic Colors";
            default:
                return "Unknown Harmony";
        }
    }

    private void saveToFavorites(String type, List<Color> harmony) {
        SharedPreferences sharedPreferences = getSharedPreferences("Favorites", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> favorites = sharedPreferences.getStringSet("favoriteHarmonies", new HashSet<>());

        // Serialize the harmony with its type
        StringBuilder serializedHarmony = new StringBuilder(type).append("|"); // Add type
        for (Color color : harmony) {
            serializedHarmony.append(color.getHexCode()).append(",");
        }

        // Remove trailing comma if necessary
        if (serializedHarmony.length() > 0) {
            serializedHarmony.setLength(serializedHarmony.length() - 1);
        }

        favorites.add(serializedHarmony.toString());
        editor.putStringSet("favoriteHarmonies", favorites).apply();

        Toast.makeText(this, "Harmony saved to favorites!", Toast.LENGTH_SHORT).show();
    }

}
