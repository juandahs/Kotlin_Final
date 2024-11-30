package com.example.proyecto_final

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_final.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity() {

    private lateinit var sqliteHelper: SqliteHelper
    private lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sqliteHelper = SqliteHelper(this)

        binding.bntRegistrar.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese un nombre", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val usuario = Usuario(0, nombre, 0)
            val usuarioId = sqliteHelper.insert(usuario)

            if (usuarioId > 0) {
                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                //finish()
            }
            else {
                Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_LONG).show()
            }
        }
    }
}