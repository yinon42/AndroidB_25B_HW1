package com.example.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Utility class for managing favorite items using SharedPreferences.
 */
public class FavoritesManager {

    private static final String PREFS_NAME = "favorites";           // SharedPreferences file name
    private static final String KEY_LIST = "favorites_list";        // Key for storing the list

    /**
     * Add an item to the favorites list if it's not already present.
     *
     * @param context Application context
     * @param item    The item to add
     */
    public static void addToFavorites(Context context, InfoItem item) {
        List<InfoItem> favorites = getFavorites(context);

        // Avoid duplicates based on title
        for (InfoItem i : favorites) {
            if (i.title.equals(item.title)) return;
        }

        favorites.add(item);
        saveFavorites(context, favorites);
    }

    /**
     * Remove an item from the favorites list based on its title.
     *
     * @param context Application context
     * @param title   The title of the item to remove
     */
    public static void removeFromFavorites(Context context, String title) {
        List<InfoItem> favorites = getFavorites(context);

        favorites.removeIf(item -> item.title.equals(title));

        saveFavorites(context, favorites);
    }

    /**
     * Check if an item exists in the favorites list based on title.
     *
     * @param context Application context
     * @param title   The title of the item
     * @return true if exists, false otherwise
     */
    public static boolean isFavorite(Context context, String title) {
        for (InfoItem item : getFavorites(context)) {
            if (item.title.equals(title)) return true;
        }
        return false;
    }

    /**
     * Retrieve the current list of favorites.
     *
     * @param context Application context
     * @return List of InfoItem objects
     */
    public static List<InfoItem> getFavorites(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_LIST, "[]");

        Type type = new TypeToken<List<InfoItem>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    /**
     * Save the list of favorites back to SharedPreferences.
     *
     * @param context   Application context
     * @param favorites List of InfoItem objects to save
     */
    private static void saveFavorites(Context context, List<InfoItem> favorites) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = new Gson().toJson(favorites);
        prefs.edit().putString(KEY_LIST, json).apply();
    }
}
