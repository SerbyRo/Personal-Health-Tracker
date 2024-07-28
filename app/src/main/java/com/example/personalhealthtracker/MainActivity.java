package com.example.personalhealthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button accelerometerButton;
    private Button stepCounterButton;
    private Button temperatureButton;

    private Button nfcGeoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        accelerometerButton = findViewById(R.id.accelerometerButton);
        accelerometerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AccelerometerActivity.class);
            startActivity(intent);
        });

        stepCounterButton = findViewById(R.id.stepCounterButton);
        stepCounterButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StepCounterActivity.class);
            startActivity(intent);
        });

        temperatureButton = findViewById(R.id.temperatureButton);
        temperatureButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TemperatureActivity.class);
            startActivity(intent);
        });

        nfcGeoButton = findViewById(R.id.nfcGeoButton);
        nfcGeoButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NfcGeoActivity.class);
            startActivity(intent);
        });


    }
}