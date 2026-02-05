package com.fervelez.fresitaapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.fervelez.fresitaapp.R
import com.fervelez.fresitaapp.model.AuthResponse
import com.fervelez.fresitaapp.MainActivity
import com.fervelez.fresitaapp.util.PreferenceHelper
import com.fervelez.fresitaapp.viewmodel.AuthViewModel
import com.fervelez.fresitaapp.viewmodel.Result

class LoginActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var prefs: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefs = PreferenceHelper(this)

        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        btnLogin.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val pass = etPassword.text.toString().trim()
            if (correo.isEmpty() || pass.isEmpty()) { Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show(); return@setOnClickListener }

            viewModel.login(correo, pass).observe(this, Observer { result ->
                when (result) {
                    is Result.Loading -> { /* mostrar progreso si quieres */ }
                    is Result.Success<*> -> {
                        val auth = result.data as AuthResponse
                        prefs.saveToken(auth.token)
                        prefs.saveUserId(auth.usuario.id)
                        prefs.saveUserName(auth.usuario.nombre)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    is Result.Error -> Toast.makeText(this, "Error: ${result.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

        tvRegister.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }
    }
}
