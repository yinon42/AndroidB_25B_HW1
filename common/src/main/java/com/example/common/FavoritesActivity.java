package com.example.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private LinearLayout favoritesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        applyDynamicBackground();      // Apply app-specific background
        initViews();                   // Initialize layout views
        initToolbar();                 // Initialize toolbar
        populateFavorites();           // Load and display favorite items
    }

    /**
     * Initialize view components.
     */
    private void initViews() {
        favoritesLayout = findViewById(R.id.favoritesContainer);
    }

    /**
     * Setup the toolbar with a back button.
     */
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.customToolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back arrow
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * Load favorite items from SharedPreferences and display them.
     */
    private void populateFavorites() {
        List<InfoItem> favorites = loadFavorites();

        for (InfoItem item : favorites) {
            TextView textView = new TextView(this);
            textView.setText(item.title);
            textView.setTextSize(20);
            textView.setPadding(16, 16, 16, 16);
            textView.setBackgroundColor(0xFFE0E0E0); // Light grey background

            // Handle click to open detail view
            textView.setOnClickListener(v -> {
                Intent intent = new Intent(this, InfoViewerActivity.class);
                ArrayList<InfoItem> singleItemList = new ArrayList<>();
                singleItemList.add(item);
                intent.putExtra("data_list", singleItemList);
                startActivity(intent);
            });

            favoritesLayout.addView(textView);
        }
    }

    /**
     * Load the favorites list from SharedPreferences.
     *
     * @return list of favorite InfoItem objects
     */
    private List<InfoItem> loadFavorites() {
        SharedPreferences prefs = getSharedPreferences("favorites", Context.MODE_PRIVATE);
        String json = prefs.getString("favorites_list", "[]");
        Log.d("FAV_DEBUG", "Raw JSON: " + json);

        Type type = new TypeToken<List<InfoItem>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    /**
     * Dynamically apply the background based on the app's package.
     */
    private void applyDynamicBackground() {
        String packageName = getPackageName();
        int bgResId = getResources().getIdentifier("app_background", "drawable", packageName);
        if (bgResId != 0) {
            LinearLayout rootLayout = findViewById(R.id.rootLayout);
            rootLayout.setBackgroundResource(bgResId);
        }
    }

    /**
     * Handle toolbar back navigation.
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
