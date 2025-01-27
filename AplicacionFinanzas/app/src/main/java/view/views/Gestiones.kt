package view.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionfinanzas.R
import com.example.aplicacionfinanzas.databinding.ActivityGestionesBinding
import com.google.android.material.appbar.MaterialToolbar

// Clase Gestiones que extiende AppCompatActivity
class Gestiones : AppCompatActivity() {
    // Declaración de la variable binding para acceder a las vistas
    private lateinit var binding: ActivityGestionesBinding

    // Método onCreate que se llama cuando se crea la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //abrir fragment GestionesPrincipalFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, GestionesPrincipalFragment())
                .commit()
        }
        // Inflar el layout usando View Binding
        binding = ActivityGestionesBinding.inflate(layoutInflater)
        // Habilitar el modo Edge-to-Edge
        enableEdgeToEdge()
        // Establecer el contenido de la vista con el layout inflado
        setContentView(binding.root)

        // Configurar la barra de herramientas
        val toolbar: MaterialToolbar = binding.menuBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

    }

    // Método onCreateOptionsMenu para inflar el menú
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    // Método onOptionsItemSelected para manejar la selección de elementos del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemInicio -> {
                if (this::class == Inicio::class) {
                    true
                } else {
                    val inicio = Intent(this, Inicio::class.java)
                    startActivity(inicio)
                    true
                }
            }

            R.id.itemGestiones -> {
                if (this::class == Gestiones::class) {
                    true
                } else {
                    val gestiones = Intent(this, Gestiones::class.java)
                    startActivity(gestiones)
                    true
                }
            }

            R.id.itemPreferencias -> {
                val preferencias = Intent(this, SettingsActivity::class.java)
                startActivity(preferencias)
                true
            }

            R.id.itemAcercaDe -> {
                val acercaDe = Intent(this, AcercaDe::class.java)
                startActivity(acercaDe)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    // Método onSaveInstanceState para guardar el estado de la actividad
    @Override
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    // Método onRestoreInstanceState para restaurar el estado de la actividad
    @Override
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {

        }
    }

}