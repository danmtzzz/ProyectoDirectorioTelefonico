package com.example.proyectodirectoriotelefonico.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyectodirectoriotelefonico.model.Datos

@Database(entities = [Datos::class], version = 1, exportSchema = false )
abstract class DatosDatabase:RoomDatabase() {
    abstract fun datosDao(): DatosDatabaseDao
}