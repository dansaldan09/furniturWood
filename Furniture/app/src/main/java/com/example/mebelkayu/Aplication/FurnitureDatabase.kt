package com.example.mebelkayu.Aplication

import android.content.Context
import android.icu.util.VersionInfo
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mebelkayu.dao.FurnitureDao
import com.example.mebelkayu.model.Furniture

@Database(entities = [Furniture::class], version = 1, exportSchema = false)
abstract class FurnitureDatabase :RoomDatabase(){
    abstract fun furniturreDao(): FurnitureDao

    companion object{
        private var INSTANCE: FurnitureDatabase? = null

        fun getDatabase(context: Context): FurnitureDatabase{
            return INSTANCE ?: synchronized(this){
                val instance= Room.databaseBuilder(
                context.applicationContext,
                FurnitureDatabase::class.java,
                    "furniture_database3"
                )
                    .allowMainThreadQueries()
                    .build()

                INSTANCE= instance
                instance
            }
        }
    }
}