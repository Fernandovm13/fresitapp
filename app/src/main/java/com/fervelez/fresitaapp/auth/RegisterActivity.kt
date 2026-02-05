package com.fervelez.fresitaapp.ui.auth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.fervelez.fresitaapp.R
import com.fervelez.fresitaapp.viewmodel.AuthViewModel
import com.fervelez.fresitaapp.viewmodel.Result

class RegisterActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val pass = etPassword.text.toString().trim()
            if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty()) { Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show(); return@setOnClickListener }

            viewModel.register(nombre, correo, pass).observe(this, Observer { result ->
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success<*> -> {
                        Toast.makeText(this, "Registrado correctamente", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    is Result.Error -> Toast.makeText(this, "Error: ${result.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
