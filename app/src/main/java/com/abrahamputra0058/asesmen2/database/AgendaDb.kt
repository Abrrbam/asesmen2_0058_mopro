package com.abrahamputra0058.asesmen2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abrahamputra0058.asesmen2.ui.model.Agenda

@Database(entities = [Agenda::class], version = 1, exportSchema = false)
abstract class AgendaDb : RoomDatabase() {
    abstract  val dao : AgendaDao

    companion object {
        @Volatile
        private var INSTANCE: AgendaDb? = null

        fun getInstance(context: Context): AgendaDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AgendaDb::class.java,
                        "agenda.db"
                    ).build()
                    INSTANCE = instance
            }
                return instance
        }
    }
}
}