package com.example.proyectodirectoriotelefonico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.proyectodirectoriotelefonico.navigation.NavManager
import com.example.proyectodirectoriotelefonico.ui.theme.ProyectoDirectorioTelefonicoTheme
import com.example.proyectodirectoriotelefonico.viewModels.DatosViewModel
import com.example.proyectodirectoriotelefonico.viewModels.DirectorioViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        val directorioVM: DirectorioViewModel by viewModels()
        val datosVM: DatosViewModel by viewModels()
        setContent {
            ProyectoDirectorioTelefonicoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   NavManager(directorioVM, datosVM)

                }
            }
        }
    }
}

