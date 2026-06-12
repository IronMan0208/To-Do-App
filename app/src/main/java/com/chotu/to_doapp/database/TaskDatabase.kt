package com.chotu.to_doapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chotu.to_doapp.data.dao.TaskDao
import com.chotu.to_doapp.data.entity.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1
)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}