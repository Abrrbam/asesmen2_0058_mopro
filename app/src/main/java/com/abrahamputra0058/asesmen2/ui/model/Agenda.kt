package com.abrahamputra0058.asesmen2.ui.model

//import androidx.annotation.DrawableRes

data class Agenda(
    val id: Long,
    val judul: String,
    val tipe: String,
    val tanggal: String,
    val waktu: String,
    val deskripsi: String,

//    Untuk menambahkan gambar pada tiap kategori agenda
//    @DrawableRes val imageResId: Int
)
