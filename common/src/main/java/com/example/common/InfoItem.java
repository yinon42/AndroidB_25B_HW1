package com.example.common;

import java.io.Serializable;

/**
 * Represents a single item of information (e.g., dog or sneaker).
 * Used for displaying title, description, and image.
 */
public class InfoItem implements Serializable {

    // === Fields ===
    public String title;       // Item title (e.g., breed name)
    public String description; // Description of the item
    public String imageUrl;    // URL of the image to display

    /**
     * Constructor to create an InfoItem.
     *
     * @param title       Item title
     * @param description Item description
     * @param imageUrl    Item image URL
     */
    public InfoItem(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
