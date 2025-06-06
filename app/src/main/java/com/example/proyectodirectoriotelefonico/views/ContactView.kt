package com.example.proyectodirectoriotelefonico.views

import android.content.ActivityNotFoundException
import androidx.compose.runtime.remember



import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.proyectodirectoriotelefonico.R
import com.example.proyectodirectoriotelefonico.components.MainTitle
import com.example.proyectodirectoriotelefonico.model.Datos
import com.example.proyectodirectoriotelefonico.viewModels.DatosViewModel
import com.example.proyectodirectoriotelefonico.viewModels.DirectorioViewModel
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactView(
    navController: NavController,
    directorioViewModel: DirectorioViewModel,
    datosViewModel: DatosViewModel,
    id: Long
) {
    val context = LocalContext.current
    val contacto by datosViewModel.getDatosByIdFlow(id).collectAsState(initial = null)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = "Detalle del Contacto") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        directorioViewModel.getContactoById(id)
                        navController.navigate("EditView/$id")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            contacto?.let { contact ->
                // Tarjeta de información
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Nombre completo
                        Text(
                            text = "${contact.nombreContacto} ${contact.apellidos}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Teléfono
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_phone),
                                contentDescription = "Teléfono",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "Teléfono",
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = contact.telefono.toString(),
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Correo
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_email),
                                contentDescription = "Correo",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "Correo electrónico",
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = contact.correo,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            }
                        }

                        // Direccion
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_direccion),
                                contentDescription = "Dirección",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "Dirección",
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = contact.direccion,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            }
                        }

                        // Tipo Contacto
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_typecontact),
                                contentDescription = "Tipo de Contacto",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "Tipo de Contacto",
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = contact.tipoContacto,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Botón de llamada
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${contact.telefono}")
                        }
                        ContextCompat.startActivity(context, intent, null)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Llamar",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "LLAMAR A ${contact.nombreContacto.uppercase()}",
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
// Nuevo Botón de Correo Electrónico
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:${contact.correo}")
                            putExtra(Intent.EXTRA_EMAIL, arrayOf(contact.correo))
                            putExtra(Intent.EXTRA_SUBJECT, "Contacto desde Directorio App")
                        }
                        try {
                            ContextCompat.startActivity(context, intent, null)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(
                                context,
                                "No hay aplicaciones de correo instaladas",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_email), // Asegúrate de tener este icono
                        contentDescription = "Enviar correo",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "ENVIAR CORREO A ${contact.nombreContacto.uppercase()}")
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Botón para abrir la dirección en Google Maps
                Button(
                    onClick = {
                        val uri = Uri.encode(contact.direccion)
                        val gmmIntentUri = Uri.parse("geo:0,0?q=$uri")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                            setPackage("com.google.android.apps.maps")
                        }
                        try {
                            ContextCompat.startActivity(context, mapIntent, null)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(
                                context,
                                "No se encontró una aplicación de mapas instalada",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_direccion), // Usa el mismo ícono de dirección
                        contentDescription = "Abrir en Maps",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "VER DIRECCIÓN EN MAPS")
                }



            } ?: run {
                Text("Contacto no encontrado", color = Color.White)
            }
        }
    }
}