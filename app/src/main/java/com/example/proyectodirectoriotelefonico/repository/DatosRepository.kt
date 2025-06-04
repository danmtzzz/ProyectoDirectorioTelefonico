package com.example.proyectodirectoriotelefonico.repository

import com.example.proyectodirectoriotelefonico.model.Datos
import com.example.proyectodirectoriotelefonico.room.DatosDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DatosRepository @Inject constructor(private val datosDatabaseDao: DatosDatabaseDao) {
    suspend fun addDatos(dato: Datos) = datosDatabaseDao.insert(dato)
    suspend fun updateDatos(dato: Datos) = datosDatabaseDao.update(dato)
    suspend fun deleteDatos(dato: Datos) = datosDatabaseDao.delete(dato)
    fun getAllDatos(): Flow<List<Datos>> = datosDatabaseDao.getDatos().flowOn(Dispatchers.IO).conflate()
    fun getDatosById(id:Long): Flow<Datos> = datosDatabaseDao.getDatosById(id).flowOn(Dispatchers.IO).conflate()
}