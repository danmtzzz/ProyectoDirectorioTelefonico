import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
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
    val context = LocalContext.current
    val camposValidos = directorioViewModel.validarCampos()
    val mostrarErrores = remember { mutableStateOf(false) }

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

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (directorioViewModel.camposEstanCompletos()) {
                    val nuevoContacto = Datos(
                        id = state.currentContactoId ?: 0,
                        nombreContacto = state.nombreContacto,
                        apellidos = state.apellidos,
                        telefono = state.telefono,
                        correo = state.correo
                    )

                    if (state.showSaveButton) {
                        datosViewModel.updateDato(nuevoContacto)
                    } else {
                        datosViewModel.addDato(nuevoContacto)
                    }

                    directorioViewModel.resetState()
                    navController.popBackStack()
                } else {
                    mostrarErrores.value = true
                    Toast.makeText(
                        context,
                        "Complete todos los campos obligatorios",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (state.showSaveButton) "ACTUALIZAR" else "GUARDAR")
        }
    }
}