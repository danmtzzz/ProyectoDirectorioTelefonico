package com.example.proyectodirectoriotelefonico.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.proyectodirectoriotelefonico.repository.DatosRepository
import com.example.proyectodirectoriotelefonico.state.DatoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class DirectorioViewModel @Inject constructor(private val repository: DatosRepository):ViewModel() {
    var state by mutableStateOf(DatoState())
        private set
    var datoState by mutableStateOf<Job?>(null)

}