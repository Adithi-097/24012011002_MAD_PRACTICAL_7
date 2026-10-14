# Android Application – JSON API and SQLite Database

## Aim
Develop an Android application that retrieves person data in JSON format from an internet API and stores the retrieved data in an SQLite database.

## Description
This practical demonstrates how to fetch person details from an internet API, parse JSON data, store records in an SQLite database, and display them using a RecyclerView. Users can refresh the data from the API and delete individual records from the list.

## Features
- Fetch person data from an internet API
- Parse JSON responses using `JSONArray` and `JSONObject`
- Store person details in an SQLite database
- Display records using RecyclerView
- Refresh data using a Floating Action Button
- Insert new records and update existing records
- Delete individual records from the database
- Display name, phone number, email, and address
- Handle network operations using Kotlin Coroutines

## Technologies Used
- **Language:** Kotlin
- **IDE:** Android Studio
- **UI:** XML, ConstraintLayout, Material Components
- **Database:** SQLite
- **Data Format:** JSON
- **Networking:** `HttpURLConnection`
- **List Display:** RecyclerView
- **Asynchronous Operations:** Kotlin Coroutines

## Project Structure

```text
app/src/main/java/com/example/a24012011002_mad_practical_7/
├── MainActivity.kt
├── Person.kt
├── DatabaseHelper.kt
├── HttpRequest.kt
├── PersonAdapter.kt
└── PersonDbTableData.kt

app/src/main/res/layout/
├── activity_main.xml
└── single_item.xml
```

## Key Components

- **MainActivity.kt:** Initializes the UI, loads saved records, and manages API requests.
- **Person.kt:** Represents person details and extracts data from JSON objects.
- **DatabaseHelper.kt:** Creates and manages the SQLite database, including insert, update, retrieve, and delete operations.
- **HttpRequest.kt:** Sends HTTP requests and reads the API response.
- **PersonAdapter.kt:** Displays person details in RecyclerView and handles record deletion.
- **PersonDbTableData.kt:** Defines constants for a person database table.

## Database Details
- **Database Name:** `ContactDatabase.db`
- **Table Name:** `contacts`
- **Fields:** ID, Name, Phone Number, Email, Address, Latitude, Longitude

## Working
1. The application loads previously saved person records from SQLite.
2. The user clicks the refresh button to fetch data from the API.
3. The JSON response is parsed into `Person` objects.
4. New records are inserted into the database, while existing records are updated.
5. The RecyclerView displays the latest stored records.
6. Users can delete individual records using the delete button.
7. The list refreshes to reflect database changes.

## Android Concepts Studied
- REST API communication
- JSON parsing
- SQLite database operations
- `SQLiteOpenHelper` and `ContentValues`
- RecyclerView and Adapter
- Kotlin Coroutines
- HTTP requests and response handling
- CRUD operations (Create, Read, Update, Delete)

  |<img width="300" height="700" alt="image" src="https://github.com/user-attachments/assets/a10a37ec-5c2d-4932-b238-ce7ba7fb4c86" />|<img width="300" height="700" alt="image" src="https://github.com/user-attachments/assets/ff7a80be-e4ea-4299-8b0a-1a78a7976521" />|




## Conclusion
This practical demonstrates how to integrate **API communication, JSON parsing, SQLite database storage, and RecyclerView** in an Android application using Kotlin. It provides practical experience in retrieving, storing, displaying, updating, and deleting data.
