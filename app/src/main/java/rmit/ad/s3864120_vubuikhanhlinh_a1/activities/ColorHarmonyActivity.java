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

        // Set up adapter
        harmonyAdapter = new HarmonyAdapter(this, harmonyList, this::saveToFavorites);
        recyclerView.setAdapter(harmonyAdapter);
    }

    private List<List<Color>> generateHarmonySchemes(String selectedColorHex) {
        List<List<Color>> harmonies = new ArrayList<>();

        // Convert the Hex code to HSL using ColorUtils
        float[] hsl = ColorUtils.hexToHSL(selectedColorHex);

        // Generate harmony schemes using HarmonyCalculator
        List<Color> complementary = HarmonyCalculator.getComplementaryColor(hsl);
        List<Color> analogous = HarmonyCalculator.getAnalogousColors(hsl);
        List<Color> triadic = HarmonyCalculator.getTriadicColors(hsl);

        // Add the generated harmonies to the list
        harmonies.add(complementary);
        harmonies.add(analogous);
        harmonies.add(triadic);

        return harmonies;
    }

    private void saveToFavorites(List<Color> harmony) {
        SharedPreferences sharedPreferences = getSharedPreferences("Favorites", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Get the current favorites from SharedPreferences
        Set<String> favorites = sharedPreferences.getStringSet("favoriteHarmonies", new HashSet<>());

        // Serialize the harmony into a single JSON-like string
        StringBuilder serializedHarmony = new StringBuilder();
        for (Color color : harmony) {
            serializedHarmony.append(color.getHexCode()).append(",");
        }

        // Remove the trailing comma
        if (serializedHarmony.length() > 0) {
            serializedHarmony.setLength(serializedHarmony.length() - 1);
        }

        // Add the serialized harmony to the favorites
        favorites.add(serializedHarmony.toString());
        editor.putStringSet("favoriteHarmonies", favorites).apply();

        Toast.makeText(this, "Harmony saved to favorites!", Toast.LENGTH_SHORT).show();
    }

}
