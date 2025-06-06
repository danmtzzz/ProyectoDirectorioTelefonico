package com.example.proyectodirectoriotelefonico.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectodirectoriotelefonico.R
import com.example.proyectodirectoriotelefonico.model.Datos
import com.example.proyectodirectoriotelefonico.state.DatoState

// Componentes existentes (los mantengo igual)
@Composable
fun MainTitle(title: String) {
    Text(
        text = title,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        isError = isError,
        keyboardOptions = keyboardOptions,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp),
        colors = TextFieldDefaults.colors(
            errorIndicatorColor = MaterialTheme.colorScheme.error,
            focusedLabelColor = if (isError) MaterialTheme.colorScheme.error
            else MaterialTheme.colorScheme.primary
        )
    )
}

// Nuevos componentes adaptados a tu estilo
@Composable
fun ContactoMainTitle(title: String) {
    MainTitle(title = title)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NombreTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    MainTextField(
        value = value,
        onValueChange = onValueChange,
        label = "Nombre"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApellidosTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    MainTextField(
        value = value,
        onValueChange = onValueChange,
        label = "Apellidos"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelefonoTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        label = { Text("Teléfono") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .padding(bottom = 15.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    MainTextField(
        value = value,
        onValueChange = onValueChange,
        label = "Correo electrónico"
    )
}

/*
@Composable
fun ContactoCard(
    contacto: Datos,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${contacto.nombreContacto} ${contacto.apellidos}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                IconButton(onClick = onDelete) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "Eliminar contacto",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_phone),
                    contentDescription = "Teléfono",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = contacto.telefono.toString(), color = Color.White)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_email),
                    contentDescription = "Correo",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = contacto.correo, color = Color.White)
            }
        }
    }
}
*/
@Composable
fun ContactoCard(
    nombre: String,
    telefono: String,
    correo: String,
    tipoContacto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = nombre,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Icon(
                    painter = painterResource(R.drawable.ic_phone),
                    contentDescription = "Teléfono",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = telefono,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Icon(
                    painter = painterResource(R.drawable.ic_email),
                    contentDescription = "Correo",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = correo,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // ✅ Nueva fila: Tipo de contacto
            Row {
                Icon(
                    painter = painterResource(R.drawable.ic_typecontact), // Usa un ícono adecuado (o genérico)
                    contentDescription = "Tipo de contacto",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = tipoContacto,
                    color = Color.White
                )
            }
        }
    }
}
@Composable
fun ContactoForm(
    state: DatoState,
    onNombreChange: (String) -> Unit,
    onApellidosChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,
    onCorreoChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (state.ShowTextField) {
            NombreTextField(
                value = state.nombreContacto,
                onValueChange = onNombreChange
            )

            ApellidosTextField(
                value = state.apellidos,
                onValueChange = onApellidosChange
            )

            TelefonoTextField(
                value = if (state.telefono != 0) state.telefono.toString() else "",
                onValueChange = { onTelefonoChange(it) }
            )

            EmailTextField(
                value = state.correo,
                onValueChange = onCorreoChange
            )

            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                Text(text = if (state.showSaveButton) "Actualizar" else "Guardar")
            }
        }
    }
}

/*
@Composable
fun ContactosList(
    contactos: List<Datos>,
    onContactoClick: (Datos) -> Unit,
    onDeleteContacto: (Datos) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(contactos) { contacto ->
            ContactoCard(
                contacto = contacto,
                onClick = { onContactoClick(contacto) },
                onDelete = { onDeleteContacto(contacto) }
            )
        }
    }
}
*/


@Composable
fun AddContactoFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.padding(16.dp),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "Añadir contacto",
            tint = Color.White
        )
    }
}

@Composable
fun CustomSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        placeholder = {
            Text("Buscar contacto...")
        },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        ),
        shape = MaterialTheme.shapes.large
    )
}
