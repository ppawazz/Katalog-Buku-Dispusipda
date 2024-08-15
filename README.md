# Internship Project: Mobile Library App for Dinas Perpustakaan dan Arsip Kota Sukabumi

Welcome to the GitHub repository for the **Mobile Library Application**, developed as part of an internship project with **Dinas Perpustakaan dan Arsip Kota Sukabumi**. This mobile application is designed to help users browse, manage, and interact with the library's catalog of books. The app is built using **Android Studio** and **Kotlin** with a focus on clean architecture and modern Android development practices.

## Application Features

- **User-Friendly Interface**: Designed with Material Design principles to ensure a consistent and responsive user experience.
- **Catalog Browsing**: Users can browse the available books in the library catalog, view details, and search by title or author.
- **Admin Functionality**: Admin users have additional functionality to add, edit, and delete books from the catalog.
- **Book Details**: Each book entry contains detailed information including the title, author, publisher, and cover image.
- **Offline Support**: Basic functionalities are available offline, with data being synced when a connection is restored.
- **Social Media Integration**: Direct links to the Dinas Perpustakaan dan Arsip Kota Sukabumi Instagram page.

## Application Layout

> **_Note:_** Please insert screenshots or design mockups of the application interface below to illustrate the UI components and flow.

![Splash Screen](screenshots/splash_screen.png)
![Main Activity](screenshots/main_activity.png)
![Book Detail](screenshots/book_detail.png)

## Libraries and Technologies Used

The application leverages a variety of libraries and technologies to enhance development and functionality:

- **Android Jetpack Components**:
  - `activity-ktx`: Simplifies common tasks with Activity API.
  - `appcompat`: Provides backward-compatible versions of Android components.
  - `lifecycle-livedata-ktx`: Used to handle data that needs to survive configuration changes.
  - `lifecycle-viewmodel-ktx`: Manages UI-related data in a lifecycle-conscious way.

- **Networking**:
  - `Retrofit`: A type-safe HTTP client for Android and Java.
  - `Gson Converter`: Converts JSON to Java objects (and vice versa) for use with Retrofit.
  - `OkHttp Logging Interceptor`: Logs HTTP request and response data.

- **Image Loading**:
  - `Glide`: Efficient image loading and caching.

- **Design**:
  - `Material Design`: Implements material design principles for a modern and consistent UI/UX.

## Architecture

The application is built using the **Model-View-ViewModel (MVVM)** architecture, which helps in maintaining a clean separation of concerns, making the codebase more maintainable and testable.

### Key Components

- **View**: Activities and Fragments that display data and handle user interactions.
- **ViewModel**: Manages the UI-related data in a lifecycle-conscious way.
- **Model**: Represents the application's data layer, including repositories for data management.

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or later.
- Minimum SDK level 21 (Android 5.0 Lollipop).
- Internet connection for data retrieval.

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/username/mobile-library-app.git
