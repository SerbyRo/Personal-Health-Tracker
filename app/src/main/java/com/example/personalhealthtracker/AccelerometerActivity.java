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

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    private TextView accelerometerData;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private Button toLightSensorButton;
    private String currentAccelerometerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accelerometer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbarAccelerometer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        accelerometerData = findViewById(R.id.accelerometerTextView);
        toLightSensorButton = findViewById(R.id.transferAccelerometerButton);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometerSensor == null) {
            accelerometerData.setText("Accelerometer not available!");
        } else {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        toLightSensorButton.setOnClickListener(v -> {
            Intent lightSensorIntent = new Intent(AccelerometerActivity.this, LightSensorActivity.class);
            lightSensorIntent.putExtra("accelerometer_data", currentAccelerometerData);
            startActivity(lightSensorIntent);
        });

        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        accelerometerData.startAnimation(rotateAnimation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
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
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            currentAccelerometerData = String.format("Accelerometer:\nX: %.2f\nY: %.2f\nZ: %.2f", x, y, z);
            accelerometerData.setText(currentAccelerometerData);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Handle changes in sensor accuracy if needed
    }
}
