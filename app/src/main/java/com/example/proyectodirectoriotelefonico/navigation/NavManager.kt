package com.example.proyectodirectoriotelefonico.navigation

import AddView
import ContentAddView
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectodirectoriotelefonico.viewModels.DatosViewModel
import com.example.proyectodirectoriotelefonico.viewModels.DirectorioViewModel
import com.example.proyectodirectoriotelefonico.views.ContactView
import com.example.proyectodirectoriotelefonico.views.EditView
import com.example.proyectodirectoriotelefonico.views.HomeView

@Composable
fun NavManager(directorioVM: DirectorioViewModel, datosVM: DatosViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home") {
        composable("Home"){
            HomeView(navController, directorioVM, datosVM)
        }
        composable("AddView"){
            AddView(navController, directorioVM, datosVM)
        }
        composable("EditView/{id}", arguments = listOf(navArgument("id"){
            type = NavType.LongType})){
            val id = it.arguments?.getLong("id")?:0
            EditView(navController, directorioVM, datosVM, id)
        }
        composable(
            "ContactView/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) {
            val id = it.arguments?.getLong("id") ?: 0
            ContactView(navController,directorioVM,datosVM, id  )
        }
    }
}