package com.abrahamputra0058.asesmen2.ui.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//import androidx.annotation.DrawableRes

@Entity(tableName = "agenda")
data class Agenda(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val judul: String,
    val tipe: String,
    val tanggal: Long = System.currentTimeMillis(),
    val waktu: String = "00:00",
    val deskripsi: String,
    val isDeleted: Boolean = false

////    Untuk menambahkan gambar pada tiap kategori agenda
//    @DrawableRes val imageResId: Int
)
