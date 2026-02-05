package com.fervelez.fresitaapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.fervelez.fresitaapp.R
import com.fervelez.fresitaapp.model.Fruit

@Composable
fun FruitCard(fruit: Fruit) {
    val context = LocalContext.current
    val baseUrl = stringResource(id = R.string.base_url).trimEnd('/')
    val rawPath = fruit.imagen_path ?: ""
    val fileName = rawPath.substringAfterLast("/")
    val imageUrl = if (fileName.isNotEmpty()) "$baseUrl/uploads/$fileName" else null

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                if (!imageUrl.isNullOrEmpty()) {
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context)
                            .data(imageUrl)
                            .crossfade(true)
                            .build()
                    )

                    Image(
                        painter = painter,
                        contentDescription = fruit.nombre,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    if (painter.state is AsyncImagePainter.State.Loading) {
                        CircularProgressIndicator(strokeWidth = 2.dp)
                    }
                } else {
                    Icon(Icons.Default.BrokenImage, null, tint = Color.Gray)
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = fruit.nombre ?: "Fruta",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                )

                Text(
                    text = fruit.nombre_cientifico ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                InfoRow(label = "Temporada: ", value = fruit.temporada ?: "-", colorLabel = Color(0xFFE91E63))

                Spacer(modifier = Modifier.height(4.dp))

                InfoRow(label = "Clasificación: ", value = fruit.clasificacion ?: "-", colorLabel = Color(0xFF4CAF50))
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String, colorLabel: Color) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = colorLabel, fontWeight = FontWeight.SemiBold)) {
                append(label)
            }
            append(value)
        },
        style = MaterialTheme.typography.bodyMedium
    )
}