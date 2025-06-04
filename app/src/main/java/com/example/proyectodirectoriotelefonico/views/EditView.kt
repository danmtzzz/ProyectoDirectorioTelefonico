package com.example.proyectodirectoriotelefonico.views

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.proyectodirectoriotelefonico.viewModels.DatosViewModel
import com.example.proyectodirectoriotelefonico.viewModels.DirectorioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditView(navController: NavController, directorioVM: DirectorioViewModel, datosVM: DatosViewModel, id:Long){
    Text("Edit View")
}