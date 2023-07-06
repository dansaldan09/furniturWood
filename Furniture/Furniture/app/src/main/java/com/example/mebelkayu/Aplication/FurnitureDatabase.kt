package com.example.mebelkayu.Aplication

import android.content.Context
import android.icu.util.VersionInfo
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mebelkayu.dao.FurnitureDao
import com.example.mebelkayu.model.Furniture

@Database(entities = [Furniture::class], version = 2, exportSchema = false)
abstract class FurnitureDatabase :RoomDatabase(){
    abstract fun furniturreDao(): FurnitureDao

    companion object{
        private var INSTANCE: FurnitureDatabase? = null

        private val migrationTo2:Migration = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Furniture_table ADD COLUMN latitude Double DEFAULT 0.0")
                database.execSQL("ALTER TABLE Furniture_table ADD COLUMN longitude Double DEFAULT 0.0")
            }
        }

        fun getDatabase(context: Context): FurnitureDatabase{
            return INSTANCE ?: synchronized(this){
                val instance= Room.databaseBuilder(
                context.applicationContext,
                FurnitureDatabase::class.java,
                    "furniture_database3"
                )
                    .addMigrations(migrationTo2)
                    .allowMainThreadQueries()
                    .build()

                INSTANCE= instance
                instance
            }
        }
    }
}