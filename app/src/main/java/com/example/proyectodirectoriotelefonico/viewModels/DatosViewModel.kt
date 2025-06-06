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

    // Nuevo estado para filtro por tipo de contacto (null = sin filtro)
    private val _tipoFiltro = MutableStateFlow<String?>(null)
    val tipoFiltro = _tipoFiltro.asStateFlow()

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onTipoFiltroChange(tipo: String?) {
        _tipoFiltro.value = tipo
    }

    // Combina la lista de contactos, el texto de búsqueda y el filtro por tipo
    val filteredContactos: StateFlow<List<Datos>> = combine(
        datosList,
        searchText,
        tipoFiltro
    ) { contactos, query, tipo ->
        contactos.filter { contacto ->
            val matchesSearch = query.isBlank() ||
                    "${contacto.nombreContacto} ${contacto.apellidos}".contains(query, ignoreCase = true) ||
                    contacto.telefono.toString().contains(query) ||
                    contacto.nombreContacto.contains(query, ignoreCase = true) ||
                    contacto.apellidos.contains(query, ignoreCase = true) ||
                    contacto.correo.contains(query)

            val matchesTipo = tipo == null || contacto.tipoContacto == tipo

            matchesSearch && matchesTipo
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