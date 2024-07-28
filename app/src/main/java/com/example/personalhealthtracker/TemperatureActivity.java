package com.example.personalhealthtracker;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TemperatureActivity extends AppCompatActivity implements SensorEventListener {
    private TextView temperatureData;
    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private Button toStepCounterButton;
    private TextView receivedDataFromStepCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_temperature);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbarTemperature);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        temperatureData = findViewById(R.id.temperatureTextView);
        toStepCounterButton = findViewById(R.id.transferTemperatureButton);
        receivedDataFromStepCounter = findViewById(R.id.receivedData);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Retrieve data from StepCounterActivity
        Intent intent = getIntent();
        String stepCountData = intent.getStringExtra("step_count_data");
        if (stepCountData != null) {
            receivedDataFromStepCounter.setText(stepCountData);
        } else {
            receivedDataFromStepCounter.setText("No data received");
        }

        toStepCounterButton.setOnClickListener(v -> {
            Intent stepCounterIntent = new Intent();
            stepCounterIntent.putExtra("temperature_data", temperatureData.getText().toString());
            setResult(RESULT_OK, stepCounterIntent);
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            float temperature = sensorEvent.values[0];
            String data = String.format("Temperature: %f°C", temperature);
            temperatureData.setText(data);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
