package com.abrahamputra0058.asesmen2.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(timestamp: Long?): String {
    return timestamp?.let {
        val date = Date(it)
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        formatter.format(date)
    } ?: "Tanggal tidak tersedia"
}

