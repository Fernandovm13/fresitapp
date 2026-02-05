package com.fervelez.fresitaapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.fervelez.fresitaapp.R

@Composable
fun RegisterScreen(
    onRegister: (
        nombre: String,
        correo: String,
        password: String,
        onDone: (Boolean, String?) -> Unit
    ) -> Unit,
    onBack: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.frutas),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.07f))
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(
                    top = 96.dp,
                    start = 20.dp,
                    end = 20.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(R.raw.strawberry)
            )
            val progress by animateLottieCompositionAsState(
                composition,
                iterations = LottieConstants.IterateForever
            )

            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                LottieAnimation(
                    composition,
                    progress,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("FresitaApp", style = MaterialTheme.typography.headlineMedium)
            Text("Crea tu cuenta", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(18.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre completo") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it },
                        label = { Text("Correo electrónico") },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null)
                        },
                        trailingIcon = {
                            val text = if (passwordVisible) "Ocultar" else "Mostrar"
                            TextButton(onClick = { passwordVisible = !passwordVisible }) {
                                Text(text)
                            }
                        },
                        visualTransformation =
                            if (passwordVisible)
                                androidx.compose.ui.text.input.VisualTransformation.None
                            else
                                androidx.compose.ui.text.input.PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (nombre.isBlank() || correo.isBlank() || password.isBlank()) {
                                message = "Completa los campos"
                            } else {
                                onRegister(
                                    nombre.trim(),
                                    correo.trim(),
                                    password.trim()
                                ) { ok, msg ->
                                    message = if (ok) {
                                        "Registrado correctamente"
                                    } else {
                                        msg ?: "Error"
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Registrarse")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = onBack,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Ya tengo una cuenta")
                    }

                    if (!message.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = message!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Al continuar, aceptas nuestros Términos y Privacidad",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
