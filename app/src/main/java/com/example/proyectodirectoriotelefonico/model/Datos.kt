package com.example.proyectodirectoriotelefonico.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "datos")
data class Datos(
    @PrimaryKey(autoGenerate = true)
    val id: Long=0,
    @ColumnInfo(name="nombre")
    val nombreContacto: String,
    @ColumnInfo(name="apellidos")
    val apellidos: String,
    @ColumnInfo(name="telefono")
    val telefono: Int,
    @ColumnInfo(name= "correo")
    val correo: String
)