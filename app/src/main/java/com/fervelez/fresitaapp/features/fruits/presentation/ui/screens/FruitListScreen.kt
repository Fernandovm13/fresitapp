package com.fervelez.fresitaapp.features.fruits.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fervelez.fresitaapp.features.fruits.domain.model.Fruit
import com.fervelez.fresitaapp.features.fruits.presentation.ui.components.FruitCard
import com.fervelez.fresitaapp.features.fruits.presentation.viewmodel.FruitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FruitListScreen(
    fruits: List<Fruit>,
    loading: Boolean,
    error: String?,
    onAddClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onEditClick: (Fruit) -> Unit,
    onDeleteConfirm: (Fruit) -> Unit
) {
    var fruitToDelete by remember { mutableStateOf<Fruit?>(null) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Frutas") },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar Sesión")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Fruta")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (fruitToDelete != null) {
                AlertDialog(
                    onDismissRequest = { fruitToDelete = null },
                    title = { Text("Eliminar Fruta") },
                    text = { Text("¿Estás seguro de que deseas eliminar esta fruta?") },
                    confirmButton = {
                        TextButton(onClick = {
                            onDeleteConfirm(fruitToDelete!!)
                            fruitToDelete = null
                        }) {
                            Text("Eliminar", color = MaterialTheme.colorScheme.error)
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
                error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: $error", color = MaterialTheme.colorScheme.error)
                    }
                }
                fruits.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay frutas registradas")
                    }
                }
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
                                onDeleteClick = { fruitToDelete = it }
                            )
                        }
                    }
                }
            }
        }
    }
}
