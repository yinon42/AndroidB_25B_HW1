# Common UI Module

This module provides a shared user interface and reusable functionality for apps that display visual information pages, such as dog breeds, sneakers, or any other themed encyclopedia-style content.

## ✅ Features

### 1. Info Viewer Activity
A beautifully designed screen that displays:
- An image
- A title (in large elegant font)
- A description (in styled box)
- Navigation controls:
  - **NEXT / BACK** buttons
  - **Favorites toggle (heart icon)** stored using SharedPreferences
  - **Share** button to copy a screenshot to clipboard
- Smooth fade-in animations for image and text

### 2. Favorites Screen
- Lists all favorited items
- Clicking an item opens it in the viewer
- Uses the same design as InfoViewerActivity
- Automatically restores favorites using local storage

### 3. Dynamic Branding Support (per-app customization)
To allow reuse across different apps (e.g., Dogs, Sneakers), each app must supply:

#### 🎨 App Background
Add the following resource to your app’s `res/drawable/` folder:
```
app_background.png
```
This background will be applied to all shared screens (home, viewer, favorites).

#### 🖼 App Logo
Add your app logo as:
```
app_logo.png
```
Shown automatically on the home screen.

#### 📋 Content Description (optional for accessibility)
Add this line to your app's `res/values/strings.xml`:
```xml
<string name="app_logo">Your App Logo</string>
```

## 📁 JSON Data Structure

Each app must include a `.json` file under the `assets/` directory, for example:

```
assets/dogs.json
assets/sneakers.json
```

### JSON Format:
```json
[
  {
    "title": "Item Title",
    "description": "Item Description",
    "imageUrl": "https://example.com/image.jpg"
  }
]
```

The module reads this file via:
```java
public static final String JSON_FILE_NAME = "dogs.json"; // or "sneakers.json"
```

## 🧩 Integration Guide

1. Import this module as a dependency in your Android Studio project.
2. Provide your own:
   - `logo_app.png`
   - `app_background.png`
   - JSON file in `assets/`
3. Set the appropriate `JSON_FILE_NAME` value in your app.
4. You're ready to use the shared UI!

## 💡 Suggestions for Future Improvements
- Support for Dark Mode
- Remote data from Firebase or API
- Search or filter within favorites
