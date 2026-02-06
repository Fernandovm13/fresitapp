package com.fervelez.fresitaapp.ui.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fervelez.fresitaapp.R
import com.fervelez.fresitaapp.model.Fruit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFruitScreen(
    fruitToEdit: Fruit? = null,
    onPickImage: () -> Unit,
    selectedImage: Uri?,
    onAdd: (nombre: String, nombreC: String, temporada: String, clasificacion: String, onResult: (Boolean, String?) -> Unit) -> Unit,
    onCancel: () -> Unit
) {
    // Inicializar estados con datos de la fruta si estamos editando
    var nombre by remember { mutableStateOf(fruitToEdit?.nombre ?: "") }
    var nombreC by remember { mutableStateOf(fruitToEdit?.nombre_cientifico ?: "") }
    var temporada by remember { mutableStateOf(fruitToEdit?.temporada ?: "") }
    var clasificacion by remember { mutableStateOf(fruitToEdit?.clasificacion ?: "") }
    var message by remember { mutableStateOf<String?>(null) }

    val opcionesTemporada = listOf("Primavera", "Verano", "Otoño", "Invierno")
    val opcionesClasificacion = listOf("Ácida", "Semiacida", "Neutra", "Dulce")

    val baseUrl = stringResource(id = R.string.base_url).trimEnd('/')

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (fruitToEdit == null) "Agregar Fruta" else "Editar Fruta",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- SECCIÓN DE IMAGEN ---
            Text("Imagen", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF1F8F1))
                    .clickable { onPickImage() }
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (selectedImage != null) {
                    // Imagen recién seleccionada de la galería
                    AsyncImage(
                        model = selectedImage,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (fruitToEdit?.imagen_path != null) {
                    // Imagen que ya existe en el servidor
                    val fileName = fruitToEdit.imagen_path!!.substringAfterLast("/")
                    AsyncImage(
                        model = "$baseUrl/uploads/$fileName",
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Placeholder si no hay nada
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.FileUpload, null, modifier = Modifier.size(40.dp), tint = Color.Gray)
                        Text("Toca para seleccionar imagen", color = Color.Gray)
                    }
                }
            }

            // --- CAMPOS DE TEXTO ---
            CustomTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = "Nombre Común",
                placeholder = "Ej: Manzana"
            )

            CustomTextField(
                value = nombreC,
                onValueChange = { nombreC = it },
                label = "Nombre Científico",
                placeholder = "Ej: Malus domestica"
            )

            // --- DROPDOWNS ---
            Text("Temporada", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            DropdownMenuField(
                label = "Selecciona una temporada",
                options = opcionesTemporada,
                selectedOption = temporada,
                onOptionSelected = { temporada = it }
            )

            Text("Clasificación", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            DropdownMenuField(
                label = "Selecciona una clasificación",
                options = opcionesClasificacion,
                selectedOption = clasificacion,
                onOptionSelected = { clasificacion = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // --- BOTONES ACCIÓN ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = {
                        if (nombre.isBlank() || temporada.isBlank() || clasificacion.isBlank()) {
                            message = "Por favor completa los campos obligatorios"
                            return@Button
                        }
                        onAdd(nombre, nombreC, temporada, clasificacion) { ok, msg ->
                            if (!ok) message = msg
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text(if (fruitToEdit == null) "Agregar" else "Guardar Cambios")
                }
            }

            message?.let {
                Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}

@Composable
fun CustomTextField(value: String, onValueChange: (String) -> Unit, label: String, placeholder: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            placeholder = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}