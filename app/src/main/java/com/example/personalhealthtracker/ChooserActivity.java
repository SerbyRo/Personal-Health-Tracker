package com.example.personalhealthtracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChooserActivity extends AppCompatActivity implements CityAdapterActivity.CityClickListener{
    public final static String RETURN_VALUE = "returnvalue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CityAdapterActivity adapter = new CityAdapterActivity(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCityClick(int imageResId) {
        Intent intent = new Intent();
        intent.putExtra(RETURN_VALUE, imageResId);
        setResult(RESULT_OK, intent);
        finish();
    }
}