package com.fervelez.fresitaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fervelez.fresitaapp.model.Fruit
import com.fervelez.fresitaapp.ui.components.FruitCard

@Composable
fun MainScreen(
    fruits: List<Fruit>,
    loading: Boolean,
    error: String?,
    onAdd: () -> Unit,
    onLogout: () -> Unit,
    onEditClick: (Fruit) -> Unit,    // ✅ Agregado
    onDeleteConfirm: (Fruit) -> Unit // ✅ Agregado
) {
    // Estado para el diálogo de confirmación
    var fruitToDelete by remember { mutableStateOf<Fruit?>(null) }

    // --- DIÁLOGO DE CONFIRMACIÓN ---
    fruitToDelete?.let { fruit ->
        AlertDialog(
            onDismissRequest = { fruitToDelete = null },
            title = { Text("¿Eliminar fruta?") },
            text = { Text("¿Estás seguro de que quieres eliminar la fruta ${fruit.nombre}?") },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteConfirm(fruit)
                    fruitToDelete = null
                }) {
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

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd, containerColor = MaterialTheme.colorScheme.primaryContainer) {
                Text("+", style = MaterialTheme.typography.headlineSmall)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(12.dp)) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Mis Frutas", style = MaterialTheme.typography.headlineMedium)
                IconButton(onClick = onLogout) {
                    Text("Cerrar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (loading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (!error.isNullOrEmpty()) {
                Text(text = error, color = MaterialTheme.colorScheme.error)
            } else if (fruits.isEmpty()) {
                Text("No hay frutas registradas", modifier = Modifier.padding(8.dp))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(fruits) { fruit ->
                        // ✅ Aquí pasamos las lambdas que FruitCard espera
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