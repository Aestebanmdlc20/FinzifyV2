package view.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.aplicacionfinanzas.R
import com.example.aplicacionfinanzas.databinding.ActivityLoginBinding
import model.Usuario
import utils.SessionManager
import viewmodel.UsuarioViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var usuarioViewModel: UsuarioViewModel  // ViewModel para gestionar la autenticación

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sesionManager : SessionManager = SessionManager(this)
        // Inicializar el ViewModel
        usuarioViewModel = ViewModelProvider(this).get(UsuarioViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            val nombreUsuario = binding.txtUsuario.text.toString()
            val password = binding.txtPassword.text.toString()

            // Validación de campos de login
            if (nombreUsuario.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese su nombre de usuario y contraseña.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar usuario
            usuarioViewModel.obtenerUsuarioPorNombre(nombreUsuario) { usuario ->
                if (usuario != null && usuario.password == password) {
                    // Si el usuario es válido, ir a la pantalla de inicio
                    sesionManager.saveUser(usuario)
                    alerta()
                } else {
                    // Si las credenciales no coinciden
                    Toast.makeText(this, "Credenciales incorrectas. Intente de nuevo.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun alerta() {
        val builder = AlertDialog.Builder(this, R.style.alertDialogPropio)
        builder.setTitle("Inicio de sesion")
        builder.setMessage("Has iniciado sesion correctamente!")

        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
            val intent = Intent(this, Inicio::class.java)
            startActivity(intent)
            finish()
        }

        builder.show()
    }
}
