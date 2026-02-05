package com.fervelez.fresitaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fervelez.fresitaapp.model.Fruit
import com.fervelez.fresitaapp.ui.components.FruitCard

@Composable
fun MainScreen(
    fruits: List<Fruit>,
    loading: Boolean,
    error: String?,
    onRefresh: () -> Unit,
    onAdd: () -> Unit,
    onLogout: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Frutas", style = MaterialTheme.typography.titleLarge)
            Button(onClick = onLogout) { Text("Cerrar sesión") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (loading) {
            Text("Cargando...", modifier = Modifier.padding(8.dp))
        } else if (!error.isNullOrEmpty()) {
            Text(text = error, color = MaterialTheme.colorScheme.error)
        } else if (fruits.isEmpty()) {
            Text("No hay frutas registradas", modifier = Modifier.padding(8.dp))
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(fruits) { fruit ->
                    FruitCard(fruit = fruit)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        FloatingActionButton(onClick = onAdd) {
            Text("+")
        }
    }
}
