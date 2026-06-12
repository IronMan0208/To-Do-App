package com.chotu.to_doapp.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    fun getDatabase(context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task_database"
        ).build()
    }
}