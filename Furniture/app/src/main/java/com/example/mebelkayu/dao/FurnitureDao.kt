package com.example.mebelkayu.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mebelkayu.model.Furniture
import kotlin.collections.List as List


@Dao
interface FurnitureDao {
    @Query("SELECT * FROM furniture_table ORDER BY name ASC")
    fun  getAllFurniture():kotlinx.coroutines.flow.Flow<List<Furniture>>

    @Insert
    suspend fun  insertFurniture(furniture: Furniture)

    @Delete
    suspend fun deleteFurniture(furniture: Furniture)

    @Update fun updateFurniture(furniture: Furniture)
}
