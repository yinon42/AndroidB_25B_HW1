package com.example.sneakers;

import android.app.Application;

/**
 * Global Application class.
 * This class is initialized before any Activity or Service is created.
 * Use this to initialize global dependencies (e.g., Firebase, logging, libraries).
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Here you can add a future initialization if you need to
    }
}
