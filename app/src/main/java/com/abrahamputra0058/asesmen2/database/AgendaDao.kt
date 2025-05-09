package com.abrahamputra0058.asesmen2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.abrahamputra0058.asesmen2.ui.model.Agenda
import kotlinx.coroutines.flow.Flow

@Dao
interface AgendaDao {
    @Insert
    suspend fun insert(agenda: Agenda)

    @Update
    suspend fun update(agenda: Agenda)

//    isDeleted = 0 artinya data masih active atau belum masuk Recycle Bin
    @Query("SELECT * FROM agenda WHERE isDeleted = 0 ORDER BY tanggal DESC")
    fun getAgenda(): Flow<List<Agenda>>

//    Ini sebaliknya dari atas
    @Query("SELECT * FROM agenda WHERE isDeleted = 1 ORDER BY tanggal DESC")
    fun getDeletedAgenda(): Flow<List<Agenda>>

    @Query("SELECT * FROM agenda WHERE id = :id")
    suspend fun getAgendaById(id: Long): Agenda?

    @Query("DELETE FROM agenda WHERE id = :id")
    suspend fun deleteById(id: Long)

}