package rmit.ad.s3864120_vubuikhanhlinh_a1.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rmit.ad.s3864120_vubuikhanhlinh_a1.R;
import rmit.ad.s3864120_vubuikhanhlinh_a1.adapters.HarmonyAdapter;
import rmit.ad.s3864120_vubuikhanhlinh_a1.utils.ColorUtils;
import rmit.ad.s3864120_vubuikhanhlinh_a1.models.Color;

public class FavoriteHarmoniesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavorites;
    private HarmonyAdapter harmonyAdapter;
    private List<List<Color>> favoriteHarmonies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_harmonies);

        recyclerViewFavorites = findViewById(R.id.recyclerViewFavorites);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));

        // Load favorite harmonies
        favoriteHarmonies = loadFavoriteHarmonies();

        // Use the HarmonyAdapter without the listener
        harmonyAdapter = new HarmonyAdapter(this, favoriteHarmonies);
        recyclerViewFavorites.setAdapter(harmonyAdapter);
    }

    private List<List<Color>> loadFavoriteHarmonies() {
        List<List<Color>> harmonies = new ArrayList<>();
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("Favorites", MODE_PRIVATE);
            Set<String> favoriteStrings = sharedPreferences.getStringSet("favoriteHarmonies", new HashSet<>());
            for (String harmonyString : favoriteStrings) {
                List<Color> harmony = deserializeHarmony(harmonyString);
                harmonies.add(harmony);
            }
        } catch (Exception e) {
            Log.e("SharedPreferences", "Failed to access shared preferences", e);
        }
        return harmonies;
    }

    private List<Color> deserializeHarmony(String serializedHarmony) {
        List<Color> harmony = new ArrayList<>();
        String[] hexCodes = serializedHarmony.split(",");
        for (String hexCode : hexCodes) {
            if (ColorUtils.isValidHexCode(hexCode)) {
                harmony.add(new Color(hexCode)); // Use the single-parameter constructor
            } else {
                Log.e("FavoriteHarmonies", "Invalid Hex code during deserialization: " + hexCode);
            }
        }
        return harmony;
    }



}
