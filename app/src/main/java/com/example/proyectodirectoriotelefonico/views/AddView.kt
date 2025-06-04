package com.example.proyectodirectoriotelefonico.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.proyectodirectoriotelefonico.components.MainIconButton
import com.example.proyectodirectoriotelefonico.components.MainTitle
import com.example.proyectodirectoriotelefonico.viewModels.DatosViewModel
import com.example.proyectodirectoriotelefonico.viewModels.DirectorioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddView(navController: NavController, directorioVM: DirectorioViewModel, datosVM: DatosViewModel
){
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = "Add Contacto") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    MainIconButton(icon = Icons.Default.ArrowBack) {
                        navController.popBackStack()
                    }
                }
            )
    }){
        ContentAddView(it, navController)
    }



}

@Composable
fun ContentAddView(it:PaddingValues, navController: NavController){
    Column(
        modifier  = Modifier.padding(it)
    ){

    }
}
