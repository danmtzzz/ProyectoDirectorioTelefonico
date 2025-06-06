package com.example.proyectodirectoriotelefonico.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectodirectoriotelefonico.components.*
import com.example.proyectodirectoriotelefonico.model.Datos
import com.example.proyectodirectoriotelefonico.viewModels.DatosViewModel
import com.example.proyectodirectoriotelefonico.viewModels.DirectorioViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import androidx.compose.ui.res.painterResource
import com.example.proyectodirectoriotelefonico.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navController: NavController,
    directorioViewModel: DirectorioViewModel,
    datosViewModel: DatosViewModel,
) {
    val searchText by datosViewModel.searchText.collectAsState()
    val contactosList by datosViewModel.filteredContactos.collectAsState()
    val tipoFiltro by datosViewModel.tipoFiltro.collectAsState()

    val opcionesTipo = listOf<String?>(null, "Amigo", "Familiar", "Compañero de trabajo")
    val expanded = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = { MainTitle(title = "Directorio Telefónico") },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
                // Filtro desplegable de tipo de contacto
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Button(
                        onClick = { expanded.value = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        Text(text = tipoFiltro ?: "Todos los tipos")
                    }
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        opcionesTipo.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(text = opcion ?: "Todos los tipos") },
                                onClick = {
                                    datosViewModel.onTipoFiltroChange(opcion)
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }
                // Barra de búsqueda
                CustomSearchBar(
                    query = searchText,
                    onQueryChange = { datosViewModel.onSearchTextChange(it) }
                )
            }
        },
        floatingActionButton = {
            FloatButton {
                directorioViewModel.resetState()
                navController.navigate("AddView")
            }
        }
    ) { paddingValues ->

        ContentHomeView(
            it = paddingValues,
            navController = navController,
            contactosList = contactosList,
            datosViewModel = datosViewModel
        )
    }
}

@Composable
fun ContentHomeView(
    it: PaddingValues,
    navController: NavController,
    contactosList: List<Datos>,
    datosViewModel: DatosViewModel
) {
    val searchText by datosViewModel.searchText.collectAsState()

    Column(modifier = Modifier.padding(it)) {

        when {
            contactosList.isEmpty() && searchText.isNotBlank() -> {
                // No hay resultados de búsqueda

                EstadoVacioView(
                    imagenId = R.drawable.empty_contacto,
                    mensaje = "No se encontraron contactos con ese nombre"
                )
            }

            contactosList.isEmpty() -> {
                // Lista vacía completamente
                EstadoVacioView(
                    imagenId = R.drawable.empty,
                    mensaje = "No hay contactos registrados"
                )

            }

            else -> {

                LazyColumn {
                    items(contactosList) { contacto ->
                        val deleteAction = SwipeAction(
                            icon = rememberVectorPainter(Icons.Default.Delete),
                            background = Color.Red,
                            onSwipe = { datosViewModel.deleteDato(contacto) }
                        )

                        val editAction = SwipeAction(
                            icon = rememberVectorPainter(Icons.Default.Edit),
                            background = Color.Blue,
                            onSwipe = { navController.navigate("EditView/${contacto.id}") }
                        )

                        SwipeableActionsBox(
                            startActions = listOf(editAction),
                            endActions = listOf(deleteAction),
                            swipeThreshold = 120.dp
                        ) {
                            ContactoCard(
                                nombre = "${contacto.nombreContacto} ${contacto.apellidos}",
                                telefono = contacto.telefono.toString(),
                                correo = contacto.correo,
                                tipoContacto = contacto.tipoContacto,
                                onClick = { navController.navigate("ContactView/${contacto.id}") }
                            )
                        }
                    }
                }
            }

        }
    }
}

    @Composable
    fun EstadoVacioView(
        imagenId: Int,
        mensaje: String
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imagenId),
                contentDescription = mensaje,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = mensaje,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
