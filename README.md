# Music List App in Java 🎵

A Java desktop application built with JavaFX and SQLite for managing a music catalog and automatically generating smart playlists based on specific duration and variety constraints.

## 🚀 Features

* **View Music Catalog:** Displays all available songs in a responsive table (ID, Band, Title, Genre, Duration).
* **Add New Songs:** Easily add new tracks to the database through the UI.
* **Filter by Genre:** Instantly filter the displayed songs by selecting a musical genre from a dropdown.
* **Smart Playlist Generation:** Automatically generates a playlist that strictly follows these rules:
  * Minimum of 3 songs.
  * Total duration strictly greater than 15 minutes (900 seconds).
  * Two consecutive songs **cannot** be from the same band.
  * Two consecutive songs **cannot** belong to the same genre.
* **Save & Load Playlists:** Save your generated playlists with custom names directly to the database and load them anytime via the UI.
* **Embedded Database:** Uses SQLite to store the song catalog and playlists, requiring no external server setup.

## 🛠️ Tech Stack

* **Language:** Java 21
* **UI Framework:** JavaFX 21.0.6 (with FXML)
* **Database:** SQLite (JDBC)
* **Build Tool:** Maven
* **Testing:** JUnit 5.12.1

## 📂 Project Structure

The project follows a standard layered architecture:
* `domain`: Contains the `Song` entity model.
* `repository`: `SongRepository` handles all SQLite database interactions (CRUD operations, creating tables, saving playlists).
* `service`: `MusicService` handles business logic, including the algorithm for smart playlist generation.
* `com.example.musicappinjava`: Contains the JavaFX application entry points (`Launcher`, `HelloApplication`) and the UI Controller (`HelloController`).
* `resources`: Contains the `hello-view.fxml` layout file.

## ⚙️ Prerequisites

Before you begin, ensure you have met the following requirements:
* **Java Development Kit (JDK) 21** or higher installed.
* **Apache Maven** installed (or you can use the included `mvnw` / `mvnw.cmd` wrapper).

## 🏃‍♂️ How to Run

1. **Clone the repository** (if applicable) or navigate to the project root directory.
2. **Compile and Run** using the Maven JavaFX plugin:
   ```bash
   # On Windows
   mvnw clean javafx:run

   # On macOS/Linux
   ./mvnw clean javafx:run
