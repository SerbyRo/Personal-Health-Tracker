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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TemperatureActivity extends AppCompatActivity implements SensorEventListener {

    private TextView temperatureData;
    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private Button toLightSensorButton;
    private TextView receivedDataFromLightSensor;

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
        toLightSensorButton = findViewById(R.id.transferTemperatureButton);
        receivedDataFromLightSensor = findViewById(R.id.receivedData);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if (temperatureSensor == null) {
            temperatureData.setText("Temperature Sensor not available!");
        } else {
            sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        Intent intent = getIntent();
        String lightSensorData = intent.getStringExtra("light_sensor_data");
        if (lightSensorData != null) {
            receivedDataFromLightSensor.setText(lightSensorData);
        } else {
            receivedDataFromLightSensor.setText("No data received");
        }

        toLightSensorButton.setOnClickListener(v -> {
            Intent lightSensorIntent = new Intent(TemperatureActivity.this, LightSensorActivity.class);
            lightSensorIntent.putExtra("temperature_data", temperatureData.getText().toString());
            setResult(RESULT_OK, lightSensorIntent);
            finish();
        });

        Animation bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        temperatureData.startAnimation(bounceAnimation);
        receivedDataFromLightSensor.startAnimation(bounceAnimation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (temperatureSensor != null) {
            sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
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
        if (sensorEvent.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            float temperature = sensorEvent.values[0];
            String data = String.format("Temperature: %.2f°C", temperature);
            temperatureData.setText(data);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Handle changes in sensor accuracy if needed
    }
}
