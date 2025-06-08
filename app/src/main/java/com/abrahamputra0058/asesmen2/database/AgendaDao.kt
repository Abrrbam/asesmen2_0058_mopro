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

//  Mengambil agenda yang belum dihapus
    @Query("SELECT * FROM agenda WHERE isDeleted = 0 ORDER BY tanggal DESC")
    fun getAgenda(): Flow<List<Agenda>>

    //    ???
    @Query("SELECT * FROM agenda WHERE id = :id")
    suspend fun getAgendaById(id: Long): Agenda?


//    Bagian Soft Delete
//  Mengambil agenda yang soft-delete
    @Query("SELECT * FROM agenda WHERE isDeleted = 1 ORDER BY tanggal DESC")
    fun getDeletedAgenda(): Flow<List<Agenda>>

    //    Soft delete
    @Query("UPDATE agenda SET isDeleted = 1 WHERE id = :id")
    suspend fun moveToDeleted(id: Long)

//    RESTORE
//  Memulihkan data yang ada di soft-delete
    @Query("UPDATE agenda SET isDeleted = 0 WHERE id = :id")
    suspend fun restoreAgenda(id: Long)


//    Delete permanent
    @Query("DELETE FROM agenda WHERE id = :id")
    suspend fun deleteById(id: Long)

}