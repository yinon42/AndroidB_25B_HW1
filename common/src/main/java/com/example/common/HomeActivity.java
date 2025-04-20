package com.example.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static final String EXTRA_FILE_NAME = "json_file_name";
    private static final String DEFAULT_FILE_NAME = "dogs.json"; // Default file name

    // Views
    private Button startBtn;
    private Button favoritesBtn;
    private ImageView logoImage;
    private LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();              // Find views by ID
        applyDynamicBackground(); // Set dynamic background if available
        applyAppLogo();           // Set app logo if available
        initButtons();            // Set click listeners for buttons
    }

    /**
     * Initialize view references.
     */
    private void initViews() {
        startBtn = findViewById(R.id.startButton);
        favoritesBtn = findViewById(R.id.favoritesButton);
        logoImage = findViewById(R.id.logoImage);
        rootLayout = findViewById(R.id.rootLayout);
    }

    /**
     * Set up click listeners for the buttons.
     */
    private void initButtons() {
        startBtn.setOnClickListener(v -> {
            // Load JSON file name from Intent or fallback to default
            String fileName = getIntent().getStringExtra(EXTRA_FILE_NAME);
            if (fileName == null) fileName = DEFAULT_FILE_NAME;

            // Load items from JSON and open InfoViewerActivity
            List<InfoItem> items = JsonLoader.loadFromAssets(this, fileName);
            Intent intent = new Intent(this, InfoViewerActivity.class);
            intent.putExtra("data_list", (Serializable) items);
            startActivity(intent);
        });

        favoritesBtn.setOnClickListener(v -> {
            // Open Favorites screen
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Dynamically apply the background based on the app's package.
     */
    private void applyDynamicBackground() {
        int bgResId = getResources().getIdentifier("app_background", "drawable", getPackageName());
        if (bgResId != 0) {
            rootLayout.setBackgroundResource(bgResId);
        }
    }

    /**
     * Dynamically apply the app logo if it exists in drawable resources.
     */
    private void applyAppLogo() {
        int logoResId = getResources().getIdentifier("app_logo", "drawable", getPackageName());

        if (logoResId != 0) {
            logoImage.setImageResource(logoResId);
            logoImage.setVisibility(View.VISIBLE);
        } else {
            logoImage.setVisibility(View.GONE);
        }
    }
}
