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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {
    private TextView stepCounterData;
    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private Button toTemperatureButton;
    private TextView receivedDataFromAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step_counter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbarStepCounter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        stepCounterData = findViewById(R.id.stepCounterTextView);
        toTemperatureButton = findViewById(R.id.transferStepCountButton);
        receivedDataFromAccelerometer = findViewById(R.id.receivedData);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Retrieve data from AccelerometerActivity
        Intent intent = getIntent();
        String accelerometerData = intent.getStringExtra("accelerometer_data");
        if (accelerometerData != null) {
            receivedDataFromAccelerometer.setText(accelerometerData);
        } else {
            receivedDataFromAccelerometer.setText("No data received");
        }

        toTemperatureButton.setOnClickListener(v -> {
            Intent temperatureIntent = new Intent(StepCounterActivity.this, TemperatureActivity.class);
            temperatureIntent.putExtra("step_count_data", stepCounterData.getText().toString());
            startActivityForResult(temperatureIntent, 1);
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
        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            float steps = sensorEvent.values[0];
            String data = String.format("Steps: %f", steps);
            stepCounterData.setText(data);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String temperatureData = data.getStringExtra("temperature_data");
            if (temperatureData != null) {
                receivedDataFromAccelerometer.setText(temperatureData);
            }
        }
    }
}
