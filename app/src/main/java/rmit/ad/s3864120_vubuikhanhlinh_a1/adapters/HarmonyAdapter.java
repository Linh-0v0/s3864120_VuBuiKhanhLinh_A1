package rmit.ad.s3864120_vubuikhanhlinh_a1.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import rmit.ad.s3864120_vubuikhanhlinh_a1.R;
import rmit.ad.s3864120_vubuikhanhlinh_a1.models.Color;
import rmit.ad.s3864120_vubuikhanhlinh_a1.utils.ColorUtils;

public class HarmonyAdapter extends RecyclerView.Adapter<HarmonyAdapter.HarmonyViewHolder> {

    private static final String TAG = "HarmonyAdapter";
    private Context context;
    private List<List<Color>> harmonies;
    private OnSaveToFavoritesListener listener;

    public interface OnSaveToFavoritesListener {
        void onSaveToFavorites(List<Color> harmony);
    }

    // Constructor with listener (for ColorHarmonyActivity)
    public HarmonyAdapter(Context context, List<List<Color>> harmonies, OnSaveToFavoritesListener listener) {
        this.context = context;
        this.harmonies = harmonies;
        this.listener = listener;
    }

    // Constructor without listener (for FavoriteHarmoniesActivity)
    public HarmonyAdapter(Context context, List<List<Color>> harmonies) {
        this.context = context;
        this.harmonies = harmonies;
        this.listener = null; // No listener
    }

    @NonNull
    @Override
    public HarmonyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_harmony, parent, false);
        return new HarmonyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HarmonyViewHolder holder, int position) {
        // Set the title based on the harmony type
        switch (position) {
            case 0:
                holder.tvHarmonyTitle.setText("Complementary Colors");
                break;
            case 1:
                holder.tvHarmonyTitle.setText("Analogous Colors");
                break;
            case 2:
                holder.tvHarmonyTitle.setText("Triadic Colors");
                break;
        }

        // Populate the color container dynamically
        List<Color> harmony = harmonies.get(position);
        holder.colorContainer.removeAllViews(); // Clear existing views
        for (Color color : harmony) {
            // Inflate a single color row
            View colorRow = LayoutInflater.from(context).inflate(R.layout.item_color_row, holder.colorContainer, false);

            // Set swatch color and hex code
            View swatch = colorRow.findViewById(R.id.colorSwatch);
            String hexCode = color.getHexCode();

            if (ColorUtils.isValidHexCode(hexCode)) {
                try {
                    swatch.setBackgroundColor(android.graphics.Color.parseColor(hexCode));
                } catch (IllegalArgumentException e) {
                    Log.e(TAG, "Invalid Hex code: " + hexCode, e);
                    swatch.setBackgroundColor(android.graphics.Color.TRANSPARENT); // Fallback color
                }
            } else {
                Log.e(TAG, "Invalid Hex code format: " + hexCode);
                swatch.setBackgroundColor(android.graphics.Color.TRANSPARENT); // Fallback color
            }

            TextView hexCodeTextView = colorRow.findViewById(R.id.tvColorHex);
            hexCodeTextView.setText(hexCode);

            // Add the row to the container
            holder.colorContainer.addView(colorRow);
        }

        // Handle Save to Favorites button (only if listener is provided)
        if (listener != null) {
            holder.btnSaveToFavorites.setVisibility(View.VISIBLE);
            holder.btnSaveToFavorites.setOnClickListener(v -> listener.onSaveToFavorites(harmony));
        } else {
            holder.btnSaveToFavorites.setVisibility(View.GONE); // Hide button if no listener
        }
    }

    @Override
    public int getItemCount() {
        return harmonies.size();
    }

    public static class HarmonyViewHolder extends RecyclerView.ViewHolder {
        TextView tvHarmonyTitle;
        LinearLayout colorContainer;
        View btnSaveToFavorites;

        public HarmonyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHarmonyTitle = itemView.findViewById(R.id.tvHarmonyTitle);
            colorContainer = itemView.findViewById(R.id.colorContainer);
            btnSaveToFavorites = itemView.findViewById(R.id.btnSaveToFavorites);
        }
    }
}
