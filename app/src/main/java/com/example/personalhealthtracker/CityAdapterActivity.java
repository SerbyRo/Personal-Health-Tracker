package com.example.personalhealthtracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CityAdapterActivity extends RecyclerView.Adapter<CityAdapterActivity.CityViewHolder> {

    private final City[] cities = {
            new City(R.drawable.berlin, "Berlin"),
            new City(R.drawable.munich, "München"),
            new City(R.drawable.venice, "Venezia"),
            new City(R.drawable.milano, "Milano"),
            new City(R.drawable.madrid, "Madrid"),
            new City(R.drawable.barcelona, "Barcelona"),
            new City(R.drawable.paris, "Paris"),
            new City(R.drawable.lyon, "Lyon"),
            new City(R.drawable.brasov, "Brașov"),
            new City(R.drawable.cluj, "Cluj-Napoca")
    };

    private final CityClickListener clickListener;

    public interface CityClickListener {
        void onCityClick(int imageResId);
    }

    public CityAdapterActivity(CityClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_city_adapter, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        holder.bind(cities[position]);
    }

    @Override
    public int getItemCount() {
        return cities.length;
    }

    class CityViewHolder extends RecyclerView.ViewHolder {

        private final ImageButton imageButton;
        private final TextView cityName;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.cityImageButton);
            cityName = itemView.findViewById(R.id.cityName);
        }

        public void bind(City city) {
            imageButton.setImageResource(city.getImageResId());
            cityName.setText(city.getName());
            imageButton.setOnClickListener(v -> clickListener.onCityClick(city.getImageResId()));
        }
    }

    static class City {
        private final int imageResId;
        private final String name;

        public City(int imageResId, String name) {
            this.imageResId = imageResId;
            this.name = name;
        }

        public int getImageResId() {
            return imageResId;
        }

        public String getName() {
            return name;
        }
    }
}