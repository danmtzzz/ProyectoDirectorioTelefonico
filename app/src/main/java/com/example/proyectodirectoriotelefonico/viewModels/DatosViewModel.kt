package com.example.proyectodirectoriotelefonico.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectodirectoriotelefonico.model.Datos
import com.example.proyectodirectoriotelefonico.repository.DatosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatosViewModel @Inject constructor(private val repository: DatosRepository): ViewModel() {
    private val _datosList = MutableStateFlow<List<Datos>>(emptyList())
    val datosList = _datosList.asStateFlow()

    init {
        viewModelScope.launch (Dispatchers.IO){
            repository.getAllDatos().collect{item->
                if(item.isNullOrEmpty()){
                    _datosList.value = emptyList()
                }else{
                    _datosList.value = item
                }

            }
        }

    }
    fun addDato(dato:Datos) = viewModelScope.launch { repository.addDatos(dato) }
    fun updateDato(dato: Datos) = viewModelScope.launch { repository.updateDatos(dato) }
    fun deleteDato(dato: Datos) = viewModelScope.launch { repository.deleteDatos(dato) }
}