package com.example.proyectodirectoriotelefonico.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navController: NavController,
    directorioViewModel: DirectorioViewModel,
    datosViewModel: DatosViewModel,
) {
    val searchText by datosViewModel.searchText.collectAsState()
    val contactosList by datosViewModel.filteredContactos.collectAsState()

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = { MainTitle(title = "Directorio Telefónico") },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
                // Barra de búsqueda
                SearchBar(
                    query = searchText,
                    onQueryChange = { datosViewModel.onSearchTextChange(it) },
                    onSearch = {},
                    active = false,
                    onActiveChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    colors = SearchBarDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {}
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
    Column(modifier = Modifier.padding(it)) {
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
                        onClick = { navController.navigate("ContactView/${contacto.id}") }
                    )
                }
            }
        }
    }
}