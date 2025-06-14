package com.abrahamputra0058.asesmen2.ui.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agenda")
data class Agenda(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val judul: String,
    val tipe: String,
    val tanggal: Long?,
    val waktu: String,
    val deskripsi: String,
    val isDeleted: Boolean = false
)
