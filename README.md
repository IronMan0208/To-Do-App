

# ✅ ToDo Pro App

A modern Android ToDo Application built using **Kotlin**, **Jetpack Compose**, **Room Database**, and **MVVM Architecture**.

The application helps users manage daily tasks efficiently with persistent local storage, a clean user interface, and modern Android development practices.

---

## 🚀 Key Highlights

* Built with Modern Android Development Stack
* Offline-First Architecture
* Persistent Data Storage using Room Database
* MVVM + Repository Pattern
* Reactive UI with Jetpack Compose
* Clean and Scalable Project Structure

---

## ✨ Features

* ➕ Add New Tasks
* 🗑️ Delete Tasks
* 📋 View All Tasks
* 💾 Local Data Persistence using Room Database
* ⚡ Fast and Responsive UI
* 🎨 Material 3 Design
* 🏗️ MVVM Architecture
* 📦 Repository Pattern
* 🔄 Automatic UI Updates with State Management

---

## 🛠️ Tech Stack

* Kotlin
* Jetpack Compose
* Material 3
* Room Database
* MVVM Architecture
* Repository Pattern
* Kotlin Coroutines
* ViewModel
* State Management

---

## 🏛️ Architecture

The project follows the **MVVM (Model-View-ViewModel)** architecture along with the **Repository Pattern**.

```text
UI (Compose)
      │
      ▼
ViewModel
      │
      ▼
Repository
      │
      ▼
Room Database
```

Benefits:

* Separation of Concerns
* Better Maintainability
* Easier Testing
* Scalable Code Structure

---

## 📂 Project Structure

```text
com.chotu.todo

├── data
│   ├── dao
│   ├── database
│   └── entity
│
├── repository
│
├── viewmodel
│
└── ui
```

---

## 🗄️ Database Components

### TaskEntity

Stores task information.

```kotlin
TaskEntity(
    id: Int,
    title: String,
    isCompleted: Boolean
)
```

### TaskDao

Provides database operations:

* Insert Task
* Delete Task
* Update Task
* Fetch All Tasks

### TaskDatabase

Main Room Database configuration.

### DatabaseProvider

Provides a single database instance across the application.

---

## 📸 Screenshots

<p align="start">
  <img src="https://github.com/user-attachments/assets/b9926a9a-ce4e-41df-890f-4d4c42f4b2a2" width="250">
</p>

---

## 📥 Download APK

Download the latest APK from the **Releases** section.

---

## 🚀 Installation

### Clone Repository

```bash
git clone https://github.com/IronMan0208/ToDo-Pro.git
```

### Open Project

Open the project using the latest version of Android Studio.

### Run Application

Build and run on an Android Emulator or Physical Device.

---

## 🧠 Learning Outcomes

Through this project, I gained hands-on experience with:

* Jetpack Compose UI Development
* Room Database Integration
* MVVM Architecture
* Repository Pattern
* ViewModel & State Management
* Kotlin Coroutines
* Local Data Persistence
* CRUD Operations
* Clean Architecture Principles

---

## 🔮 Future Improvements

* ☑️ Complete / Incomplete Tasks
* ✏️ Edit Tasks
* 🔍 Search Tasks
* 🌙 Dark Mode
* 📅 Due Date Support
* 🏷️ Task Categories
* 📊 Task Statistics & Analytics
* ☁️ Cloud Synchronization

---

## 👨‍💻 Author

### Ajay Kumar

Android Developer passionate about building modern Android applications using:

* Kotlin
* Jetpack Compose
* MVVM Architecture
* Room Database
* Retrofit
* Clean Architecture

🔗 GitHub: https://github.com/IronMan0208

---

## ⭐ Support

If you found this project helpful, consider giving it a ⭐ on GitHub.
