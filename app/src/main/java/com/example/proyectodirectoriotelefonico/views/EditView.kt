package com.example.proyectodirectoriotelefonico.views

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditView(
    navController: NavController,
    directorioViewModel: DirectorioViewModel,
    datosViewModel: DatosViewModel,
    id: Long
) {
    val contactosList by datosViewModel.filteredContactos.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = "Editar Contacto") },
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
        ContentEditView(
            it = paddingValues,
            navController = navController,
            datosViewModel = datosViewModel,
            directorioViewModel = directorioViewModel,
            id = id
        )
    }
}

@Composable
fun ContentEditView(
    it: PaddingValues,
    navController: NavController,
    datosViewModel: DatosViewModel,
    directorioViewModel: DirectorioViewModel,
    id: Long
)  {// Estado y validación
    val context = LocalContext.current
    val state = directorioViewModel.state
    val mostrarErrores = remember { mutableStateOf(false) }
    val camposValidos = directorioViewModel.validarCampos()

    // Cargar contacto al iniciar
    LaunchedEffect(Unit) {
        directorioViewModel.getContactoById(id)}

Column(
modifier = Modifier
.padding(it)
.padding(horizontal = 16.dp)
.fillMaxSize(),
verticalArrangement = Arrangement.spacedBy(8.dp)
) {
    // Campo Nombre
    MainTextField(
        value = state.nombreContacto,
        onValueChange = { directorioViewModel.onNombreChange(it) },
        label = "Nombre *",
        isError = mostrarErrores.value && !camposValidos["nombre"]!!
    )
    if (mostrarErrores.value && !camposValidos["nombre"]!!) {
        Text(
            text = "El nombre es obligatorio",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = 16.dp)
        )
    }

    // Campo Apellidos
    MainTextField(
        value = state.apellidos,
        onValueChange = { directorioViewModel.onApellidosChange(it) },
        label = "Apellidos *",
        isError = mostrarErrores.value && !camposValidos["apellidos"]!!
    )
    // ... (mensaje error similar para apellidos)
    if (mostrarErrores.value && !camposValidos["apellidos"]!!) {
        Text(
            text = "El apellido es obligatorio",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = 16.dp)
        )
    }

    // Campo Teléfono
    MainTextField(
        value = if (state.telefono != 0) state.telefono.toString() else "",
        onValueChange = {
            if (it.all { char -> char.isDigit() }) {
                directorioViewModel.onTelefonoChange(it)
            }
        },
        label = "Teléfono *",
        isError = mostrarErrores.value && !camposValidos["telefono"]!!,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )
    // ... (mensaje error similar para teléfono)
    if (mostrarErrores.value && !camposValidos["telefono"]!!) {
        Text(
            text = "El telefono es obligatorio",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = 16.dp)
        )
    }

    // Campo Correo
    MainTextField(
        value = state.correo,
        onValueChange = { directorioViewModel.onCorreoChange(it) },
        label = "Correo electrónico *",
        isError = mostrarErrores.value && !camposValidos["correo"]!!,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
    if (mostrarErrores.value && !camposValidos["correo"]!!) {
        Text(
            text = if (state.correo.isEmpty()) "El correo es obligatorio"
            else "Ingrese un correo válido",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = 16.dp)
        )
    }

    // Campo Direccion
    MainTextField(
        value = state.direccion,
        onValueChange = { directorioViewModel.onDireccionChange(it) },
        label = "Dirección *",
        isError = mostrarErrores.value && !camposValidos["direccion"]!!
    )
    // ... (mensaje error similar para apellidos)
    if (mostrarErrores.value && !camposValidos["direccion"]!!) {
        Text(
            text = "La direccion es obligatoria",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = 16.dp)
        )
    }

    Spacer(modifier = Modifier.weight(1f))

    // Menú desplegable para Tipo de contacto
    val opciones = listOf("Amigo", "Familiar", "Compañero de trabajo")
    val expanded = remember { mutableStateOf(false) }

    Text(text = "Tipo de contacto *", style = MaterialTheme.typography.labelLarge)

    Box {
        Button(
            onClick = { expanded.value = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = state.tipoContacto)
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        directorioViewModel.onTipoContactoChange(opcion)
                        expanded.value = false
                    }
                )
            }
        }
    }
    Spacer(modifier = Modifier.weight(1f))


    Button(
        onClick = {
            if (directorioViewModel.camposEstanCompletos()) {
                val contactoActualizado = Datos(
                    id = id, // Mantenemos el ID existente
                    nombreContacto = state.nombreContacto,
                    apellidos = state.apellidos,
                    telefono = state.telefono,
                    correo = state.correo,
                    direccion = state.direccion,
                    tipoContacto = state.tipoContacto

                )
                datosViewModel.updateDato(contactoActualizado)
                directorioViewModel.resetState()
                navController.popBackStack()
            } else {
                mostrarErrores.value = true
                Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "ACTUALIZAR CONTACTO")
    }
}
}