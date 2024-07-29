package com.example.personalhealthtracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SharedMemory;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileNotFoundException;

public class NfcGeoActivity extends AppCompatActivity {
    private ImageView imageView;

    private EditText locationText;

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            onResultReceived(result.getData());
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nfc_geo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button button = findViewById(R.id.button);
        Button chooseCityButton = findViewById(R.id.chooseCityButton);
        locationText = findViewById(R.id.editTextText);
        imageView = findViewById(R.id.imageView);
        button.setOnClickListener(v -> showLocation(locationText.getText().toString()));

        chooseCityButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChooserActivity.class);
            launcher.launch(intent);
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void onResultReceived(Intent intent) {
        if (intent != null) {
            int imageId = intent.getIntExtra(ChooserActivity.RETURN_VALUE, -1);
            if (imageId != -1) {
                imageView.setImageResource(imageId);
            }
        }
    }




    private void showLocation(String location){
        String uriText = "geo: 0,0?q=" + location;
        Uri uri = Uri.parse(uriText);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(mapIntent);
    }


}