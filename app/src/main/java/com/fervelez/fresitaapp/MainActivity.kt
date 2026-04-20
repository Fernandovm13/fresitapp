package com.fervelez.fresitaapp

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.fervelez.fresitaapp.domain.model.AuthResponse
import com.fervelez.fresitaapp.domain.model.Fruit
import com.fervelez.fresitaapp.presentation.ui.screens.*
import com.fervelez.fresitaapp.presentation.ui.theme.FresitaAppTheme
import com.fervelez.fresitaapp.core.util.PreferenceHelper
import com.fervelez.fresitaapp.presentation.viewmodel.AuthViewModel
import com.fervelez.fresitaapp.presentation.viewmodel.FruitViewModel
import com.fervelez.fresitaapp.presentation.viewmodel.Result
import java.io.File
import java.io.FileOutputStream

enum class Screen { LOGIN, REGISTER, MAIN, ADD }

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val fruitViewModel: FruitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = PreferenceHelper(this)

        if (prefs.getUserId() != -1) {
            fruitViewModel.loadFruits()
        }

        setContent {
            FresitaAppTheme {
                var currentScreen by remember {
                    mutableStateOf(if (prefs.getUserId() == -1) Screen.LOGIN else Screen.MAIN)
                }


                val fruits by fruitViewModel.fruits.observeAsState(emptyList())
                val loading by fruitViewModel.loading.observeAsState(false)
                val error by fruitViewModel.error.observeAsState(null)


                var fruitToEdit by remember { mutableStateOf<Fruit?>(null) }
                var selectedImage by remember { mutableStateOf<Uri?>(null) }

                val imagePicker = rememberLauncherForActivityResult(
                    ActivityResultContracts.GetContent()
                ) { uri -> selectedImage = uri }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (currentScreen) {

                        Screen.LOGIN -> LoginScreen(
                            onLogin = { correo, pass ->
                                authViewModel.login(correo, pass)
                                    .observe(this@MainActivity) { result ->
                                        if (result is Result.Success<*>) {
                                            val auth = result.data as AuthResponse
                                            prefs.saveToken(auth.token)
                                            prefs.saveUserId(auth.usuario.id)
                                            prefs.saveUserName(auth.usuario.nombre)
                                            fruitViewModel.loadFruits()
                                            currentScreen = Screen.MAIN
                                        }
                                    }
                            },
                            onRegisterClick = { currentScreen = Screen.REGISTER }
                        )

                        Screen.REGISTER -> RegisterScreen(
                            onRegister = { nombre, correo, pass, onDone ->
                                authViewModel.register(nombre, correo, pass)
                                    .observe(this@MainActivity) { res ->
                                        if (res is Result.Success<*>) {
                                            onDone(true, null)
                                            currentScreen = Screen.LOGIN
                                        } else if (res is Result.Error) {
                                            onDone(false, res.message)
                                        }
                                    }
                            },
                            onBack = { currentScreen = Screen.LOGIN }
                        )


                        Screen.MAIN -> FruitListScreen(
                            fruits = fruits,
                            loading = loading,
                            error = error,
                            onAddClick = {
                                fruitToEdit = null
                                selectedImage = null
                                currentScreen = Screen.ADD
                            },
                            onLogoutClick = {
                                prefs.clear()
                                currentScreen = Screen.LOGIN
                            },
                            onEditClick = { fruit ->
                                fruitToEdit = fruit
                                selectedImage = null
                                currentScreen = Screen.ADD
                            },
                            onDeleteConfirm = { fruit ->
                                fruitViewModel.deleteFruit(fruit.id) { success, msg ->
                                    if (success) {
                                        Toast.makeText(this@MainActivity, "Eliminado con éxito", Toast.LENGTH_SHORT).show()
                                        fruitViewModel.loadFruits()
                                    } else {
                                        Toast.makeText(this@MainActivity, "Error: $msg", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        )

                        Screen.ADD -> AddFruitScreen(
                            fruitToEdit = fruitToEdit,
                            onPickImage = { imagePicker.launch("image/*") },
                            selectedImage = selectedImage,
                            onAdd = { nombre, nc, temp, clas, onResult ->
                                val userId = prefs.getUserId()
                                val file = selectedImage?.let { uriToFile(it) }

                                if (fruitToEdit == null) {

                                    fruitViewModel.addFruit(nombre, nc, temp, clas, file, userId) { ok, msg ->
                                        onResult(ok, msg)
                                        if (ok) {
                                            currentScreen = Screen.MAIN
                                            fruitViewModel.loadFruits()
                                        }
                                    }
                                } else {
                                    fruitViewModel.updateFruit(fruitToEdit!!.id, nombre, nc, temp, clas, file) { ok, msg ->
                                        onResult(ok, msg)
                                        if (ok) {
                                            currentScreen = Screen.MAIN
                                            fruitViewModel.loadFruits()
                                        }
                                    }
                                }
                            },
                            onCancel = {
                                fruitToEdit = null
                                currentScreen = Screen.MAIN
                            }
                        )
                    }
                }
            }
        }
    }

    private fun uriToFile(uri: Uri): File {
        val input = contentResolver.openInputStream(uri)!!
        val file = File(cacheDir, "temp_image_${System.currentTimeMillis()}.png")
        val output = FileOutputStream(file)
        input.copyTo(output)
        input.close()
        output.close()
        return file
    }
}