package com.example.mebelkayu.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mebelkayu.model.Furniture
import com.example.mebelkayu.repository.FurnitureRepository
import kotlinx.coroutines.launch

class FurnitureViewModel(private val repository: FurnitureRepository): ViewModel() {
    val allFurnitures: LiveData<List<Furniture>> = repository.allfurnitures.asLiveData()

    fun insert(furniture: Furniture) = viewModelScope.launch {
        repository.insertFurniture(furniture)
    }

    fun delete(furniture: Furniture) = viewModelScope.launch {
        repository.deleteFurniture(furniture)
    }

    fun update(furniture: Furniture) = viewModelScope.launch {
        repository.updateFurniture(furniture)
    }
}

class FurnitureViewModelFactory(private val repository: FurnitureRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FurnitureViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FurnitureViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
