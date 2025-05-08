package com.abrahamputra0058.asesmen2.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abrahamputra0058.asesmen2.database.AgendaDb
import com.abrahamputra0058.asesmen2.ui.model.DetailViewModel
import com.abrahamputra0058.asesmen2.ui.model.MainViewModel

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = AgendaDb.getInstance(context).dao
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if(modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}