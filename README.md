# Internship Project: Katalog Buku Mobile App for Dinas Perpustakaan dan Arsip Kota Sukabumi

Welcome to the GitHub repository for the **Mobile Library Application**, developed as part of an internship project with **Dinas Perpustakaan dan Arsip Kota Sukabumi**. This mobile application is designed to help users browse, manage, and interact with the library's catalog of books. The app is built using **Android Studio** and **Kotlin** with a focus on clean architecture and modern Android development practices.

## Application Features

- **User-Friendly Interface**: Designed with Material Design principles to ensure a consistent and responsive user experience.
- **Catalog Browsing**: Users can browse the available books in the library catalog, view details, and search by title or author.
- **Admin Functionality**: Admin users have additional functionality to add, edit, and delete books from the catalog.
- **Book Details**: Each book entry contains detailed information including the title, author, publisher, and cover image.
- **Social Media Integration**: Direct links to the Dinas Perpustakaan dan Arsip Kota Sukabumi Instagram page.

## Application Layout

<p align="center">
  <img src="https://github.com/user-attachments/assets/443e2ca9-d2a7-4ac8-aa73-08a025e5c298" width="200" alt="Splash">
  <img src="https://github.com/user-attachments/assets/da60b1a9-75d3-4982-a50b-ccfba3fb8406" width="200" alt="User">
  <img src="https://github.com/user-attachments/assets/931fe756-850a-4418-b5d3-42e4f1ab54ea" width="200" alt="Main User">
  <img src="https://github.com/user-attachments/assets/5aea9d42-977c-4932-b1fb-c8610968a366" width="200" alt="Detail">
  <img src="https://github.com/user-attachments/assets/107c4c69-a4dc-412d-82f4-e7fae2dcedd0" width="200" alt="Login">
  <img src="https://github.com/user-attachments/assets/2ba2ed06-dc3f-4cd8-9a33-8f77dbaff448" width="200" alt="Main Admin">
  <img src="https://github.com/user-attachments/assets/9c73087c-f4de-404c-9bd9-9d5052ee61c7" width="200" alt="Add Book">
  <img src="https://github.com/user-attachments/assets/cdb837c3-1f20-495e-9a37-6a020b5ef430" width="200" alt="Update Book">
</p>

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
