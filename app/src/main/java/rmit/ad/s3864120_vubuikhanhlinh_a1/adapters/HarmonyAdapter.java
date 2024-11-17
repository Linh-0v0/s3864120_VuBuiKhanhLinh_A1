package rmit.ad.s3864120_vubuikhanhlinh_a1.adapters;

import android.content.Context;
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
        List<Color> harmony = harmonies.get(position);

        // Use the first color's name as the type for the title
        if (!harmony.isEmpty()) {
            holder.tvHarmonyTitle.setText(harmony.get(0).getColorName()); // Get the type from the first color
        } else {
            holder.tvHarmonyTitle.setText("Unknown Harmony"); // Fallback for empty harmonies
        }

        holder.colorContainer.removeAllViews(); // Clear existing views
        for (Color color : harmony) {
            View colorRow = LayoutInflater.from(context).inflate(R.layout.item_color_row, holder.colorContainer, false);

            View swatch = colorRow.findViewById(R.id.colorSwatch);
            if (ColorUtils.isValidHexCode(color.getHexCode())) {
                swatch.setBackgroundColor(android.graphics.Color.parseColor(color.getHexCode()));
            } else {
                swatch.setBackgroundColor(android.graphics.Color.TRANSPARENT);
            }

            TextView hexCodeTextView = colorRow.findViewById(R.id.tvColorHex);
            hexCodeTextView.setText(color.getHexCode());

            holder.colorContainer.addView(colorRow);
        }

        if (listener != null) {
            holder.btnSaveToFavorites.setVisibility(View.VISIBLE);
            holder.btnSaveToFavorites.setOnClickListener(v -> listener.onSaveToFavorites(harmony));
        } else {
            holder.btnSaveToFavorites.setVisibility(View.GONE);
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
