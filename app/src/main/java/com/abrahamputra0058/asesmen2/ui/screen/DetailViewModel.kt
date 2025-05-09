package com.abrahamputra0058.asesmen2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrahamputra0058.asesmen2.database.AgendaDao
import com.abrahamputra0058.asesmen2.ui.model.Agenda
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(private val dao: AgendaDao) : ViewModel() {
//    private val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    fun insert(judul: String, tipe: String, tanggal: Long, waktu: String, deskripsi: String) {
        val agenda = Agenda(
            judul = judul,
            tipe = tipe,
            tanggal = tanggal,
            waktu = waktu,
            deskripsi = deskripsi
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(agenda)
        }
    }

    suspend fun getAgenda(id: Long): Agenda? {
        return dao.getAgendaById(id)
    }

    fun update(id: Long, judul: String, tipe: String, tanggal: Long , waktu: String, deskripsi: String) {
        val agenda = Agenda(
            id = id,
            judul = judul,
            tipe = tipe,
            tanggal = tanggal,
            waktu = waktu,
            deskripsi = deskripsi
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(agenda)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

    suspend fun moveToDeleted(agenda: Agenda) {
        val updatedAgenda = agenda.copy(isDeleted = true)
        dao.update(updatedAgenda)

    }

    suspend fun restoreFromDeleted(agenda: Agenda) {
        val updatedAgenda = agenda.copy(isDeleted = false)
        dao.update(updatedAgenda)
    }

}