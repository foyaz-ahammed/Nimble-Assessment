package com.nimble.assessment.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_table")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val pharmacyId: String,
    val medication: String,
    val date: String,
)
