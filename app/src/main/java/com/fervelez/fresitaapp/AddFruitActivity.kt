package com.fervelez.fresitaapp.

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fervelez.fresitaapp.R
import com.fervelez.fresitaapp.util.PreferenceHelper
import com.fervelez.fresitaapp.viewmodel.FruitViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class AddFruitActivity : AppCompatActivity() {
    private val viewModel: FruitViewModel by viewModels()
    private lateinit var prefs: PreferenceHelper
    private var selectedImageUri: Uri? = null

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            val iv = findViewById<ImageView>(R.id.ivPreview)
            Glide.with(this).load(it).into(iv)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_fruit)

        prefs = PreferenceHelper(this)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etNombreC = findViewById<EditText>(R.id.etNombreCientifico)
        val etTemporada = findViewById<EditText>(R.id.etTemporada)
        val etClas = findViewById<EditText>(R.id.etClasificacion)
        val btnPick = findViewById<Button>(R.id.btnPick)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        btnPick.setOnClickListener { pickImage.launch("image/*") }

        btnAdd.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            if (nombre.isEmpty()) { Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show(); return@setOnClickListener }

            val usuarioId = prefs.getUserId()
            if (usuarioId == -1) { Toast.makeText(this, "Necesitas iniciar sesión", Toast.LENGTH_SHORT).show(); return@setOnClickListener }

            val file = selectedImageUri?.let { uriToFile(it) }

            viewModel.addFruit(nombre, etNombreC.text.toString(), etTemporada.text.toString(), etClas.text.toString(), file, usuarioId) { ok, msg ->
                runOnUiThread {
                    if (ok) {
                        Toast.makeText(this, "Fruta agregada", Toast.LENGTH_SHORT).show()
                        finish()
                    } else Toast.makeText(this, "Error: $msg", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun uriToFile(uri: Uri): File? {
        try {
            val returnCursor = contentResolver.query(uri, null, null, null, null)
            val nameIndex = returnCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME) ?: -1
            var fileName = "temp_image"
            if (nameIndex >= 0 && returnCursor != null) {
                returnCursor.moveToFirst()
                fileName = returnCursor.getString(nameIndex)
            }
            returnCursor?.close()

            val file = File(cacheDir, fileName)
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int
            if (inputStream != null) {
                while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
            }
            outputStream.close()
            inputStream?.close()
            return file
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
