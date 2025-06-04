package com.example.proyectodirectoriotelefonico.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectodirectoriotelefonico.components.MainTextField
import com.example.proyectodirectoriotelefonico.components.MainTitle
import com.example.proyectodirectoriotelefonico.model.Datos
import com.example.proyectodirectoriotelefonico.viewModels.DatosViewModel
import com.example.proyectodirectoriotelefonico.viewModels.DirectorioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddView(
    navController: NavController,
    directorioViewModel: DirectorioViewModel,
    datosViewModel: DatosViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = if (directorioViewModel.state.showSaveButton) "Editar Contacto" else "Agregar Contacto") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        directorioViewModel.resetState()
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        ContentAddView(
            it = paddingValues,
            navController = navController,
            datosViewModel = datosViewModel,
            directorioViewModel = directorioViewModel
        )
    }
}

@Composable
fun ContentAddView(
    it: PaddingValues,
    navController: NavController,
    datosViewModel: DatosViewModel,
    directorioViewModel: DirectorioViewModel
) {
    val state = directorioViewModel.state

    Column(
        modifier = Modifier
            .padding(it)
            .padding(horizontal = 30.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ... (campos del formulario iguales)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    val contacto = Datos(
                        id = if (state.showSaveButton && state.currentContactoId != null) {
                            state.currentContactoId!!
                        } else {
                            0 // ID 0 para nuevos contactos (Room auto-generará uno nuevo)
                        },
                        nombreContacto = state.nombreContacto,
                        apellidos = state.apellidos,
                        telefono = state.telefono,
                        correo = state.correo
                    )

                    if (state.showSaveButton) {
                        datosViewModel.updateDato(contacto)
                    } else {
                        datosViewModel.addDato(contacto)
                    }

                    directorioViewModel.resetState()
                    navController.popBackStack()
                }
            ) {
                Text(text = if (state.showSaveButton) "ACTUALIZAR" else "GUARDAR")
            }
            // ... (resto del código igual)
        }
    }
}