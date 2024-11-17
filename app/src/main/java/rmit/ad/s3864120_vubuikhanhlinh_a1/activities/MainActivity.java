package rmit.ad.s3864120_vubuikhanhlinh_a1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import rmit.ad.s3864120_vubuikhanhlinh_a1.R;

public class MainActivity extends AppCompatActivity {

    private Button btnColorWheel, btnFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        btnColorWheel = findViewById(R.id.btnColorWheel);
        btnFavorites = findViewById(R.id.btnFavorites);

        // Set up click listeners
        btnColorWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ColorWheelActivity.class);
                startActivity(intent);
            }
        });

        btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavoriteHarmoniesActivity.class);
                startActivity(intent);
            }
        });
    }
}
