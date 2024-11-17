package rmit.ad.s3864120_vubuikhanhlinh_a1.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import rmit.ad.s3864120_vubuikhanhlinh_a1.R;

public class ColorWheelActivity extends AppCompatActivity {

    private ImageView imgColorWheel;
    private View colorSwatch;
    private TextView tvColorDetails;
    private Button btnBackToMain, btnViewHarmonies;
    private Bitmap colorWheelBitmap;
    private String selectedHexCode = null; // Holds the selected color's Hex code

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_wheel);

        // Initialize views
        imgColorWheel = findViewById(R.id.imgColorWheel);
        colorSwatch = findViewById(R.id.colorSwatch);
        tvColorDetails = findViewById(R.id.tvColorDetails);
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnViewHarmonies = findViewById(R.id.btnViewHarmonies); // New button for viewing harmonies

        // Set up back button
        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(ColorWheelActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // Set up button to view harmonies
        btnViewHarmonies.setOnClickListener(v -> {
            if (selectedHexCode != null) {
                Intent intent = new Intent(ColorWheelActivity.this, ColorHarmonyActivity.class);
                intent.putExtra("selectedColor", selectedHexCode); // Pass the selected color
                startActivity(intent);
            } else {
                tvColorDetails.setText("Please select a color first!");
            }
        });

        // Set up touch listener for the color wheel
        imgColorWheel.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                if (colorWheelBitmap == null) {
                    imgColorWheel.setDrawingCacheEnabled(true);
                    imgColorWheel.buildDrawingCache();
                    colorWheelBitmap = Bitmap.createBitmap(imgColorWheel.getDrawingCache());
                    imgColorWheel.setDrawingCacheEnabled(false);
                }

                if (x >= 0 && y >= 0 && x < colorWheelBitmap.getWidth() && y < colorWheelBitmap.getHeight()) {
                    int pixelColor = colorWheelBitmap.getPixel(x, y);

                    // Check if the color is not transparent
                    if (Color.alpha(pixelColor) != 0) {
                        // Update UI with selected color
                        colorSwatch.setBackgroundColor(pixelColor);
                        selectedHexCode = String.format("#%06X", (0xFFFFFF & pixelColor));
                        tvColorDetails.setText("Hex: " + selectedHexCode);
                    }
                }
            }
            return true;
        });
    }
}
