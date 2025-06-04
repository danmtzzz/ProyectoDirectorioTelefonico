package com.example.proyectodirectoriotelefonico.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.proyectodirectoriotelefonico.components.FloatButton
import com.example.proyectodirectoriotelefonico.components.MainTitle
import com.example.proyectodirectoriotelefonico.viewModels.DatosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, datosVM: DatosViewModel){
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = "Datos") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatButton {
                navController.navigate("AddView")
            }
        }
    ){
        ContentHomeView(it, navController, datosVM)
    }
}

@Composable
fun ContentHomeView(it: PaddingValues, navController: NavController, datosVM: DatosViewModel){
    Column(
        modifier = Modifier.padding(it)
    ){

    }
}