package com.example.sneakers;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.common.HomeActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Launch HomeActivity from the shared module with the filename
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(HomeActivity.EXTRA_FILE_NAME, "sneakers.json");
        startActivity(intent);
        finish(); // Don't show MainActivity, just skip through it
    }
}
