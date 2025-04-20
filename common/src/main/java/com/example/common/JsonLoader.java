package com.example.common;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to load InfoItem data from a JSON file in the assets folder.
 */
public class JsonLoader {

    /**
     * Loads a list of InfoItem objects from a JSON file in assets.
     *
     * @param context  Application context
     * @param filename The name of the JSON file (e.g., "dogs.json")
     * @return A list of InfoItem objects, or an empty list if an error occurs
     */
    public static List<InfoItem> loadFromAssets(Context context, String filename) {
        try {
            // Open the file from the assets folder
            InputStream is = context.getAssets().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            // Define the type of the list for Gson
            Type listType = new TypeToken<ArrayList<InfoItem>>() {}.getType();

            // Parse the JSON file using Gson
            return new Gson().fromJson(reader, listType);

        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            return new ArrayList<>(); // Return empty list on failure
        }
    }
}
