package com.example.store_mvvm

import androidx.room.*


@Dao
interface StoreDao {
    @Query("SELECT * FROM StoreEntity")
    fun getAllStores(): MutableList<StoreEntity>

    @Insert
    fun addStores(storeEntity: StoreEntity)

    @Update
    fun updateStores(storeEntity: StoreEntity)

    @Delete
    fun deleteStores(storeEntity: StoreEntity)
}