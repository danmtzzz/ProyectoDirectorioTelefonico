package com.example.proyectodirectoriotelefonico.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectodirectoriotelefonico.model.Datos
import com.example.proyectodirectoriotelefonico.repository.DatosRepository
import com.example.proyectodirectoriotelefonico.state.DatoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DirectorioViewModel @Inject constructor(private val repository: DatosRepository) : ViewModel() {
    var state by mutableStateOf(DatoState())
        private set

    // Lista observable de contactos
    var contactosList by mutableStateOf<List<Datos>>(emptyList())
        private set

    // Cargar todos los contactos
    fun loadContactos() {
        viewModelScope.launch {
            repository.getAllDatos().collect { contactos ->
                contactosList = contactos
            }
        }
    }

    // Obtener un contacto por ID
    fun getContactoById(id: Long) {
        viewModelScope.launch {
            repository.getDatosById(id).collect { contacto ->
                state = state.copy(
                    nombreContacto = contacto.nombreContacto,
                    apellidos = contacto.apellidos,
                    telefono = contacto.telefono,
                    correo = contacto.correo,
                    showSaveButton = true,
                    ShowTextField = true,
                    currentContactoId = contacto.id
                )
            }
        }
    }

    // Manejar cambios en los campos del formulario
    fun onNombreChange(value: String) {
        state = state.copy(nombreContacto = value)
    }

    fun onApellidosChange(value: String) {
        state = state.copy(apellidos = value)
    }

    fun onTelefonoChange(value: String) {
        try {
            val telefonoInt = value.toInt()
            state = state.copy(telefono = telefonoInt)
        } catch (e: NumberFormatException) {
            // Manejar error si el usuario ingresa un teléfono no numérico
            state = state.copy(telefono = 0)
        }
    }

    fun onCorreoChange(value: String) {
        state = state.copy(correo = value)
    }



    // Guardar un contacto (crear o actualizar)
    fun saveContacto() {
        viewModelScope.launch {
            val datos = Datos(
                id = if (state.showSaveButton) getCurrentContactoId() else 0,
                nombreContacto = state.nombreContacto,
                apellidos = state.apellidos,
                telefono = state.telefono,
                correo = state.correo
            )

            if (state.showSaveButton) {
                repository.updateDatos(datos)
            } else {
                repository.addDatos(datos)
            }
            resetState()
            loadContactos() // Recargar la lista después de guardar
        }
    }

    // Eliminar un contacto
    fun deleteContacto(datos: Datos) {
        viewModelScope.launch {
            repository.deleteDatos(datos)
            loadContactos() // Recargar la lista después de eliminar
        }
    }

    // Resetear el estado del formulario
    fun resetState() {
        state = DatoState()
    }

    // Mostrar formulario para nuevo contacto
    fun showNewContactForm() {
        state = DatoState(ShowTextField = true)
    }

    // Obtener el ID del contacto actual (si existe)
    private fun getCurrentContactoId(): Long {
        return contactosList.firstOrNull {
            it.nombreContacto == state.nombreContacto &&
                    it.apellidos == state.apellidos &&
                    it.telefono == state.telefono &&
                    it.correo == state.correo

        }?.id ?: 0
    }

    // Función para validar campos
    fun camposEstanCompletos(): Boolean {
        return state.nombreContacto.isNotBlank() &&
                state.apellidos.isNotBlank() &&
                state.telefono != 0 &&
                state.correo.isNotBlank() &&
                state.correo.contains("@") // Validación básica de email


    }

    // Función para mostrar errores
    fun validarCampos(): Map<String, Boolean> {
        return mapOf(
            "nombre" to state.nombreContacto.isNotBlank(),
            "apellidos" to state.apellidos.isNotBlank(),
            "telefono" to (state.telefono != 0),
            "correo" to (state.correo.isNotBlank() && state.correo.contains("@")),

        )
    }
}