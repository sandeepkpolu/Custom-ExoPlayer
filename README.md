Overview
The Video Player App is an advanced Android application designed to provide a seamless video playback experience. It features a highly customizable video player built using AndroidX Media3 ExoPlayer and showcases modern Android development practices with Jetpack Compose, Room, Hilt (Dagger2), and more. This app supports various video formats and streaming technologies, offering smooth playback, advanced controls, and additional features like favorites management.
Features
Video Playback

Formats Supported:
Local video files
Remote video streams
DASH (Dynamic Adaptive Streaming over HTTP)
HLS (HTTP Live Streaming)
Smooth Streaming
DRM-protected videos
Custom Controls:
Play/Pause button
Volume control slider
Brightness control slider
Skip forward/backward by 10 seconds
Add to favorites and share icons
Full-Screen Support:
Optimized for both portrait and landscape orientations
Prevents restart on configuration changes
Favorites Management

Add videos to a favorites list for easy access
Favorites stored persistently using Room Database
Favorites displayed in a tab with a modern UI powered by Jetpack Compose
Modern UI

Built entirely with Jetpack Compose for a clean and responsive user interface
LazyColumn used for efficient list rendering
Material Design components for a polished look and feel
Notifications

Persistent notification while video is playing
Play/Pause controls directly from the notification
Data Persistence

Room Database for storing and managing the favorites list
Seamless UI updates with Flow and LiveData
Dependency Injection

Built with Hilt (Dagger2) for scalable and modular architecture
Simplifies dependency management

Technologies and Tools Used
Jetpack Compose

Used for building the UI components like video player controls, favorites list, and navigation
Room Database

Stores and retrieves favorite videos
Observes database changes using Flow for real-time UI updates
AndroidX Media3 ExoPlayer

Core video playback library
Supports a wide range of formats (DASH, HLS, Smooth Streaming, DRM)
Dagger2

Simplifies dependency injection
Provides a robust, scalable architecture
LiveData and Flow

Manages and observes data changes between the database and UI
Kotlin Coroutines

Handles asynchronous operations like video loading and database queries
Material Design

Provides a clean and modern look with consistent theming
