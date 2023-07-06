package com.example.mebelkayu.Aplication

import android.app.Application
import com.example.mebelkayu.repository.FurnitureRepository

class FurnitureApp:Application() {
    val database by lazy { FurnitureDatabase.getDatabase(this) }
    val repository by  lazy { FurnitureRepository(database.furniturreDao()) }
}