package com.fervelez.fresitaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fervelez.fresitaapp.model.Fruit
import com.fervelez.fresitaapp.ui.components.FruitCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FruitListScreen(
    fruits: List<Fruit>,
    loading: Boolean = false,
    error: String? = null,
    onAddClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onEditClick: (Fruit) -> Unit,    // Nueva función
    onDeleteConfirm: (Fruit) -> Unit // Nueva función
) {
    // Estado para controlar el diálogo de eliminación
    var fruitToDelete by remember { mutableStateOf<Fruit?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Mis Frutas",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Cerrar Sesión",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Fruta")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // --- MOSTRAR DIÁLOGO DE CONFIRMACIÓN ---
            fruitToDelete?.let { fruit ->
                AlertDialog(
                    onDismissRequest = { fruitToDelete = null },
                    title = { Text("Confirmar eliminación") },
                    text = { Text("¿Estás seguro de que deseas eliminar la fruta '${fruit.nombre}'?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onDeleteConfirm(fruit)
                                fruitToDelete = null
                            }
                        ) {
                            Text("Eliminar", color = Color.Red)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { fruitToDelete = null }) {
                            Text("Cancelar")
                        }
                    }
                )
            }

            when {
                loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Cargando frutas...", style = MaterialTheme.typography.bodyMedium)
                    }
                }
                // ... (Mantener resto de condiciones error y empty igual que antes)
                error != null -> { /* Tu código existente */ }
                fruits.isEmpty() -> { /* Tu código existente */ }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 16.dp, end = 16.dp, top = 8.dp, bottom = 80.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(fruits) { fruit ->
                            FruitCard(
                                fruit = fruit,
                                onEditClick = { onEditClick(it) },
                                onDeleteClick = { fruitToDelete = it } // Abrir diálogo
                            )
                        }
                    }
                }
            }
        }
    }
}