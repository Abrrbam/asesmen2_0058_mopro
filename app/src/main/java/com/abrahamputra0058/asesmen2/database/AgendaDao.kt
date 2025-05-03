package com.abrahamputra0058.asesmen2.database

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.abrahamputra0058.asesmen2.ui.model.Agenda
import kotlinx.coroutines.flow.Flow

interface AgendaDao {
    @Insert
    suspend fun insert(agenda: Agenda)

    @Update
    suspend fun update(agenda: Agenda)

    @Query("SELECT * FROM agenda ORDER BY tanggal DESC")
    fun getAgenda(): Flow<List<Agenda>>
}