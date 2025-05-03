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
    val tanggal: String,
    val waktu: String,
    val deskripsi: String,

//    Untuk menambahkan gambar pada tiap kategori agenda
//    @DrawableRes val imageResId: Int
)
