package com.abrahamputra0058.asesmen2.ui.model

import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {
    val data = listOf(
        Agenda(
            id = 1,
            judul = "Android Development Class",
            tipe = "Course",
            tanggal = "2025-04-12",
            waktu = "09:00",
            deskripsi = "Learn about advanced Jetpack Compose techniques.",
        ),
        Agenda(
            id = 2,
            judul = "Submit Project Report",
            tipe = "Task",
            tanggal = "2025-04-13",
            waktu = "23:59",
            deskripsi = "Finalize and submit the project report via the portal.",
        ),
        Agenda(
            id = 3,
            judul = "Team Meeting",
            tipe = "Other",
            tanggal = "2025-04-14",
            waktu = "14:00",
            deskripsi = "Discuss progress and align on deliverables.",
        ),
        Agenda(
            id = 4,
            judul = "Software Engineering Class",
            tipe = "Course",
            tanggal = "2025-04-15",
            waktu = "10:30",
            deskripsi = "Focus on system design principles.",
        ),
        Agenda(
            id = 5,
            judul = "Complete Homework",
            tipe = "Task",
            tanggal = "2025-04-16",
            waktu = "20:00",
            deskripsi = "Finish assignments for the upcoming lecture.",
        ),
        Agenda(
            id = 6,
            judul = "Photography Workshop",
            tipe = "Other",
            tanggal = "2025-04-17",
            waktu = "10:00",
            deskripsi = "Learn techniques for outdoor photography.",
        ),
        Agenda(
            id = 7,
            judul = "Database Management Class",
            tipe = "Course",
            tanggal = "2025-04-18",
            waktu = "08:00",
            deskripsi = "Dive into database normalization and optimization.",
        ),
        Agenda(
            id = 8,
            judul = "Prepare Presentation Slides",
            tipe = "Task",
            tanggal = "2025-04-19",
            waktu = "18:00",
            deskripsi = "Create slides for the final presentation.",
        ),
        Agenda(
            id = 9,
            judul = "Volunteering Event",
            tipe = "Other",
            tanggal = "2025-04-20",
            waktu = "11:00",
            deskripsi = "Participate in a tree-planting activity.",
        ),
        Agenda(
            id = 10,
            judul = "Physics Class",
            tipe = "Course",
            tanggal = "2025-04-21",
            waktu = "09:30",
            deskripsi = "Learn about quantum mechanics.",
        )
    )
}


