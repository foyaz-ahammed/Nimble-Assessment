package com.nimble.assessment.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nimble.assessment.database.dao.OrderDao
import com.nimble.assessment.database.entities.Order

@Database(
    entities = [Order::class],
    version = 1
)
abstract class NimbleDatabase: RoomDatabase() {
    abstract fun orderDao(): OrderDao
}