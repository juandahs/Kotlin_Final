package com.example.proyecto_final

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_final.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity() {

    private lateinit var sqliteHelper: SqliteHelper
    private lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar ViewBinding
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sqliteHelper = SqliteHelper(this)

        setupRecyclerView()
        setupRegisterButton()
    }


    private fun setupRegisterButton() {
        binding.bntRegistrar.setOnClickListener {
            val nombre = binding.etNombre.text.toString().trim() // Validación de entrada
            if (nombre.isEmpty()) {
                showToast("Por favor ingrese un nombre")
                return@setOnClickListener
            }

            val usuario = Usuario(0, nombre, 0)
            val usuarioId = sqliteHelper.insert(usuario)

            if (usuarioId > 0) {
                showToast("Usuario registrado exitosamente")
                navigateToMainActivity(usuario.id, usuario.nombre, usuario.puntaje)
            } else {
                showToast("Error al registrar el usuario")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun navigateToMainActivity(id:Int?, nombre:String?, score:Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("USUARIO_ID", id)
        intent.putExtra("USUARIO_NOMBRE", nombre)
        intent.putExtra("USUARIO_SCORE", score)
        startActivity(intent)
        finish()
    }

    private fun setupRecyclerView() {
        val adapter = UsuarioAdapter()
        adapter.refreshUsuario(sqliteHelper.getUsuarios())
        adapter.setOnClickItem {
            navigateToMainActivity(it.id, it.nombre, it.puntaje)
        }

        adapter.setOnClickDeleteItem {
            sqliteHelper.delete(it.id)
            adapter.refreshUsuario(sqliteHelper.getUsuarios())
        }

        binding.rvUsuarios.layoutManager = LinearLayoutManager(this)
        binding.rvUsuarios.adapter = adapter
    }
}
