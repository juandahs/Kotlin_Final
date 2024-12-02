package com.example.proyecto_final

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_final.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private lateinit var sqliteHelper : SqliteHelper
    private var puntaje:Int = 0
    private var multiplicador:Int = 1
    private var multiplicando: Int = 1
    private var resultado: Int = 0

    private var usuarioId: Int? = null
    private var usuarioNombre: String? = null
    private var usuarioPuntaje: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        usuarioNombre = intent.getStringExtra("USUARIO_NOMBRE")
        usuarioId = intent.getIntExtra("USUARIO_ID", -1)
        usuarioPuntaje = intent.getIntExtra("USUARIO_SCORE", 0)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            tvUsuarioNombre.text = "Bienvenido, $usuarioNombre"
            tvPuntaje.text = "Puntaje: $usuarioPuntaje"

            bntVerificar.setOnClickListener {
                verificarRespuesta()
            }
        }

        binding.bntRegresar.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
            finish()
        }
        sqliteHelper = SqliteHelper(this)
        iniciarJuego()

    }

    private fun iniciarJuego(){
        binding.etRespuesta.text.clear()

        multiplicando = (1..10).random()
        multiplicador = (1..10).random()
        resultado = multiplicando * multiplicador
        binding.tvOperacion.text = "$multiplicando x $multiplicador"
    }

    private fun verificarRespuesta() {
        val respuestaUsuario = binding.etRespuesta.text.toString().toIntOrNull()

        if (respuestaUsuario == null) {
            showToast("Por favor, ingresa un número válido")
            return
        }

        if (respuestaUsuario == resultado) {
            puntaje += 10
            showToast("¡Correcto! +10 puntos")
        } else {
            puntaje -= 10
            showToast("Incorrecto, la respuesta es $resultado")
        }

        // Actualizar puntaje en pantalla
        binding.tvPuntaje.text = "Puntaje: $puntaje"

        // Guardar puntaje en la base de datos
        var usuario = sqliteHelper.getById(usuarioId)

        usuario?.puntaje = puntaje
        sqliteHelper.update(usuario)

        // Reiniciar juego
        binding.etRespuesta.text.clear()
        iniciarJuego()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}