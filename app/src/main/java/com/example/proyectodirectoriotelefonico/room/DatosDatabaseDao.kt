package com.example.proyectodirectoriotelefonico.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.proyectodirectoriotelefonico.model.Datos
import kotlinx.coroutines.flow.Flow

@Dao
interface DatosDatabaseDao {
    //Crud
    @Query("Select * from datos")
    fun getDatos(): Flow<List<Datos>>

    @Query("Select * from datos where id=:id")
    fun getDatosById(id: Long):Flow<Datos>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(datos: Datos)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(datos:Datos)

    @Delete
    suspend fun delete(datos:Datos)
}