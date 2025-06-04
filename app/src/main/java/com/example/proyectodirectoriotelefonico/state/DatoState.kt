package com.example.proyectodirectoriotelefonico.state

data class DatoState (
    val showSaveButton: Boolean = false,
    val ShowTextField: Boolean = false,
    val id :Long = 0,
    val nombreContacto: String="",
    val apellidos: String="",
    val telefono: Int=0,
    val correo: String="",
    val currentContactoId: Long? = null
)