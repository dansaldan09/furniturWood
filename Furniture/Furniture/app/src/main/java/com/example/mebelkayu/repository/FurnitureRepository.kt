package com.example.mebelkayu.repository

import com.example.mebelkayu.dao.FurnitureDao
import com.example.mebelkayu.model.Furniture
import kotlinx.coroutines.flow.Flow

class FurnitureRepository(private val furnitureDao: FurnitureDao) {
    val allfurnitures: Flow<List<Furniture>> =furnitureDao.getAllFurniture()
    suspend fun insertFurniture(furniture: Furniture){
        furnitureDao.insertFurniture(furniture)
    }
    suspend fun deleteFurniture(furniture: Furniture){
        furnitureDao.deleteFurniture(furniture)
    }

    suspend fun updateFurniture(furniture: Furniture){
        furnitureDao.updateFurniture(furniture)
    }
}
