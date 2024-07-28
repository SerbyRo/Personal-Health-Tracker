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
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Button toTemperatureButton;
    private TextView receivedDataFromAccelerometer;

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
        receivedDataFromAccelerometer = findViewById(R.id.receivedData);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null) {
            lightSensorData.setText("Light Sensor not available!");
        } else {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        // Retrieve data from AccelerometerActivity
        Intent intent = getIntent();
        String accelerometerData = intent.getStringExtra("accelerometer_data");
        if (accelerometerData != null) {
            receivedDataFromAccelerometer.setText(accelerometerData);
        } else {
            receivedDataFromAccelerometer.setText("No data received");
        }

        toTemperatureButton.setOnClickListener(v -> {
            Intent temperatureIntent = new Intent(LightSensorActivity.this, TemperatureActivity.class);
            temperatureIntent.putExtra("light_sensor_data", lightSensorData.getText().toString());
            startActivity(temperatureIntent);
        });
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        lightSensorData.startAnimation(fadeInAnimation);
        receivedDataFromAccelerometer.startAnimation(fadeInAnimation);
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
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            float light = sensorEvent.values[0];
            String data = String.format("Light: %.2f lx", light);
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
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String temperatureData = data.getStringExtra("temperature_data");
            if (temperatureData != null) {
                receivedDataFromAccelerometer.setText(temperatureData);
            }
        }
    }
}
