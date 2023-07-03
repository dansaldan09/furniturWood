package com.example.mebelkayu.model

import android.hardware.camera2.params.MandatoryStreamCombination.MandatoryStreamInformation
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Furniture_table")
data class Furniture (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val address:String,
    val information: String
) : Parcelable


