package com.example.proyectodirectoriotelefonico.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectodirectoriotelefonico.model.Datos
import com.example.proyectodirectoriotelefonico.repository.DatosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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
    fun getDatosByIdFlow(id: Long): Flow<Datos?> {
        return repository.getDatosById(id)
    }

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    val filteredContactos: StateFlow<List<Datos>> = datosList
        .combine(searchText) { contactos, query ->
            if (query.isBlank()) {
                contactos // Si no hay query, mostrar todos los contactos
            } else {
                contactos.filter { contacto ->
                    // Búsqueda por nombre (incluyendo nombre + apellido)
                    "${contacto.nombreContacto} ${contacto.apellidos}".contains(query, ignoreCase = true) ||
                            // Búsqueda por teléfono (convertido a String)
                            contacto.telefono.toString().contains(query) ||
                            // Búsqueda sólo por nombre
                            contacto.nombreContacto.contains(query, ignoreCase = true) ||
                            // Búsqueda sólo por apellido
                            contacto.apellidos.contains(query, ignoreCase = true)||
                            //Busqueda por correo
                            contacto.correo.contains(query)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        loadAllContactos()
    }

    private fun loadAllContactos() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllDatos().collect { items ->
                _datosList.value = items ?: emptyList()
            }
        }
    }

}