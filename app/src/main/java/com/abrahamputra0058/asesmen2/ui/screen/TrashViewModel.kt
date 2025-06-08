package com.abrahamputra0058.asesmen2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrahamputra0058.asesmen2.database.AgendaDao
import com.abrahamputra0058.asesmen2.ui.model.Agenda
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TrashViewModel(private val dao: AgendaDao) : ViewModel() {
//    Mengambil data yg soft-delete
    val recycleBinData: StateFlow<List<Agenda>> = dao.getDeletedAgenda().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun restoreData(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.restoreAgenda(id)
        }
    }

    fun deletePermanentData(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }

    }
}