package com.nimble.assessment.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nimble.assessment.database.entities.Order

@Dao
interface OrderDao {
    @Insert
    suspend fun addOrder(order: Order)

    @Insert
    suspend fun addOrderList(order: List<Order>)

    @Query("select * from order_table")
    fun getOrderList(): LiveData<List<Order>>
}