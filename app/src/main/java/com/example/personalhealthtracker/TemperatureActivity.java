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

    private TextView temperatureTextView;
    private SensorManager sensorManager;
    private Sensor temperatureSensor;

    private String accelerometerData;

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

        temperatureTextView = findViewById(R.id.temperatureTextView);
        Button transferData = findViewById(R.id.transferTemperatureButton);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);



        accelerometerData = getIntent().getStringExtra("step_counter_data");
        if (accelerometerData != null) {
            temperatureTextView.setText(accelerometerData);
        }

        transferData.setOnClickListener(v-> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("temperature_data","Temperature " +  temperatureTextView.getText().toString()+  " | Accelerometer Data: " + accelerometerData);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
        Animation bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        temperatureTextView.startAnimation(bounceAnimation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
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
    public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
                String data = "Temperature: " + sensorEvent.values[0] + "°C"; //Alt + 0176
                temperatureTextView.setText(data);
            }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}