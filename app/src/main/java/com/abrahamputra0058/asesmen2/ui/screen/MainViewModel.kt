package com.abrahamputra0058.asesmen2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrahamputra0058.asesmen2.database.AgendaDao
import com.abrahamputra0058.asesmen2.ui.model.Agenda
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao : AgendaDao) : ViewModel() {
    val data: StateFlow<List<Agenda>> = dao.getAgenda().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )
}


