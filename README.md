# EU Unemployment Statistics Viewer

This is a Java desktop application that visualizes EU statistics for the
**activation of registered unemployed** over time.

Data is fetched from the EU open API and displayed as a chart showing yearly
values, with a comparison between men and women.

---

## Features

- Fetches data from the EU open statistics API
- Custom-built chart rendering using Java Swing
- Comparison between men and women over time
- Supports country selection via GUI input or command-line argument

---

## Project Structure

The application follows the MVC design pattern architecture
- `api` – communication with the API
- `controllers` – coordination between user input, data retrieval, and views
- `models` – domain models and dataset generation
- `models.records` – immutable data carriers used within the application
- `models.exceptions` – domain-specific exceptions
- `utilities` – shared configuration and constants
- `views` – top-level UI composition
- `views.panels` – reusable Swing panels
- `views.menus` – application menu bar and menu items
---

## Requirements

- Java **17** or higher

---

## Running the Application

### Run from source
Clone the repository and open it in your preferred IDE (e.g. IntelliJ IDEA).

Run the main class.

### Run with command-line argument

You can optionally provide a two-letter ISO country code at startup:
```bash
java -jar app.jar SE
```
If an ISO code is provided, the application automatically loads data for that country.
Otherwise, the country can be selected via the graphical user interface.

Note: Use the fat JAR (with dependencies) when running the application outside the IDE.

### Pre-built JAR and launcher

For convenience, a pre-built JAR and a Windows launcher script are 
available [here](https://drive.google.com/drive/folders/18or6vOqRJZOXPAHYiK0SKiqn8wot_Tln?usp=drive_link).



