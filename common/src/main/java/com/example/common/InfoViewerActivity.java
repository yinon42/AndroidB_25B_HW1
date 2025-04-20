package com.example.common;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class InfoViewerActivity extends AppCompatActivity {

    private List<InfoItem> itemList;
    private int currentIndex = 0;

    private ImageView imageView;
    private TextView titleView, descView, titleBar;
    private Button nextBtn, prevBtn, shareBtn;
    private ImageButton favBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_viewer);

        applyDynamicBackground(); // Set background based on app
        initViews();              // Link views by ID
        initAppBar();             // Set title bar
        initButtons();           // Set all button click listeners

        itemList = (List<InfoItem>) getIntent().getSerializableExtra("data_list");
        if (itemList != null && !itemList.isEmpty()) {
            showCurrentItem(); // Display first item
        }
    }

    /**
     * Link all layout views to Java fields.
     */
    private void initViews() {
        titleBar = findViewById(R.id.titleBar);
        imageView = findViewById(R.id.imageView);
        titleView = findViewById(R.id.titleView);
        descView = findViewById(R.id.descView);
        nextBtn = findViewById(R.id.nextBtn);
        prevBtn = findViewById(R.id.prevBtn);
        shareBtn = findViewById(R.id.shareBtn);
        favBtn = findViewById(R.id.favBtn);
        backBtn = findViewById(R.id.backBtn);
    }

    /**
     * Set the title bar to display the app name.
     */
    private void initAppBar() {
        String appLabel = getApplicationInfo().loadLabel(getPackageManager()).toString();
        titleBar.setText(appLabel);
    }

    /**
     * Set up all button click listeners.
     */
    private void initButtons() {
        backBtn.setOnClickListener(v -> finish());

        nextBtn.setOnClickListener(v -> {
            currentIndex = (currentIndex + 1) % itemList.size();
            showCurrentItem();
        });

        prevBtn.setOnClickListener(v -> {
            currentIndex = (currentIndex - 1 + itemList.size()) % itemList.size();
            showCurrentItem();
        });

        shareBtn.setOnClickListener(v -> shareScreenshot());

        favBtn.setOnClickListener(v -> {
            InfoItem item = itemList.get(currentIndex);
            if (FavoritesManager.isFavorite(this, item.title)) {
                FavoritesManager.removeFromFavorites(this, item.title);
            } else {
                FavoritesManager.addToFavorites(this, item);
            }
            updateFavoriteButton(item);
        });
    }

    /**
     * Display the current InfoItem (title, description, image).
     */
    private void showCurrentItem() {
        InfoItem item = itemList.get(currentIndex);

        titleView.setText(item.title);
        descView.setText(item.description);

        imageView.setAlpha(0f);
        Glide.with(this)
                .load(item.imageUrl)
                .transform(new RoundedCorners(90))
                .into(imageView);
        imageView.animate().alpha(1f).setDuration(1000).start();

        titleView.setAlpha(0f);
        titleView.animate().alpha(1f).setDuration(1000).start();

        descView.setAlpha(0f);
        descView.animate().alpha(1f).setDuration(1000).start();

        updateFavoriteButton(item);
    }

    /**
     * Update favorite button icon and color based on state.
     */
    private void updateFavoriteButton(InfoItem item) {
        boolean isFav = FavoritesManager.isFavorite(this, item.title);

        favBtn.setImageResource(isFav ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite_border);
        favBtn.setColorFilter(isFav ? 0xFFFF0000 : 0xFFFFFFFF); // Red if favorite, white if not
    }

    /**
     * Dynamically set background from drawable named "app_background".
     */
    private void applyDynamicBackground() {
        int bgResId = getResources().getIdentifier("app_background", "drawable", getPackageName());
        if (bgResId != 0) {
            LinearLayout rootLayout = findViewById(R.id.rootLayout);
            rootLayout.setBackgroundResource(bgResId);
        }
    }

    /**
     * Capture a screenshot of the screen and copy it to the clipboard.
     */
    private void shareScreenshot() {
        View rootView = getWindow().getDecorView().getRootView();
        rootView.setDrawingCacheEnabled(true);
        Bitmap screenshot = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);

        try {
            File imageFile = new File(getCacheDir(), "screenshot.png");
            FileOutputStream fos = new FileOutputStream(imageFile);
            screenshot.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            Uri imageUri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".fileprovider",
                    imageFile
            );

            ClipData clip = ClipData.newUri(getContentResolver(), "Screenshot", imageUri);
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setPrimaryClip(clip);

            Toast.makeText(this, "Screenshot copied to clipboard", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to copy screenshot", Toast.LENGTH_SHORT).show();
        }
    }
}
