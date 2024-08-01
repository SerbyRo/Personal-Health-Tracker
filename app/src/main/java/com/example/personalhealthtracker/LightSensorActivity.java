package com.example.personalhealthtracker;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LightSensorActivity extends AppCompatActivity implements SensorEventListener {

    private TextView lightSensorData;
    private TextView receivedDataFromTemperature;
    private TextView receivedDataFromAccelerometer;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Button toTemperatureButton;

    private static final int REQUEST_CODE_TEMPERATURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_light_sensor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbarLightSensor);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lightSensorData = findViewById(R.id.lightSensorTextView);
        toTemperatureButton = findViewById(R.id.transferLightSensorButton);
        receivedDataFromTemperature = findViewById(R.id.receivedData);
        receivedDataFromAccelerometer = findViewById(R.id.receivedData);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null) {
            lightSensorData.setText("Light Sensor not available!");
        } else {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        // Retrieve data from StepCounterActivity
        Intent intent = getIntent();
        String accelerometerData = intent.getStringExtra("accelerometer_data");
        if (accelerometerData != null) {
            receivedDataFromAccelerometer.setText(accelerometerData);
        } else {
            receivedDataFromAccelerometer.setText("No accelerometer data received");
        }

        toTemperatureButton.setOnClickListener(v -> {
            Intent temperatureIntent = new Intent(LightSensorActivity.this, TemperatureActivity.class);
            temperatureIntent.putExtra("light_sensor_data", lightSensorData.getText().toString());
            startActivityForResult(temperatureIntent, REQUEST_CODE_TEMPERATURE);
        });

        Animation bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        lightSensorData.startAnimation(bounceAnimation);
        receivedDataFromTemperature.startAnimation(bounceAnimation);
        receivedDataFromAccelerometer.startAnimation(bounceAnimation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lightLevel = sensorEvent.values[0];
            String data = String.format("Light Level: %.2f lx", lightLevel);
            lightSensorData.setText(data);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Handle changes in sensor accuracy if needed
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TEMPERATURE && resultCode == RESULT_OK && data != null) {
            String temperatureData = data.getStringExtra("temperature_data");
            if (temperatureData != null) {
                receivedDataFromTemperature.setText(temperatureData);
            }
        }
    }
}
