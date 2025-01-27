package view.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionfinanzas.R
import com.example.aplicacionfinanzas.databinding.ActivityInicioBinding
import com.google.android.material.appbar.MaterialToolbar

// Clase Inicio que extiende AppCompatActivity
class Inicio : AppCompatActivity() {
    // Declaración de la variable binding para acceder a las vistas
    private lateinit var binding: ActivityInicioBinding

    // Método onCreate que se llama cuando se crea la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflar el layout usando View Binding
        binding = ActivityInicioBinding.inflate(layoutInflater)
        // Habilitar el modo Edge-to-Edge
        enableEdgeToEdge()
        // Establecer el contenido de la vista con el layout inflado
        setContentView(binding.root)

        // Configurar la barra de herramientas
        val toolbar: MaterialToolbar = binding.menuBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        // Configurar el WebView para cargar una URL
        val webView: WebView = binding.webView
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://www.eleconomista.es/?gad_source=1&gclid=Cj0KCQiA0MG5BhD1ARIsAEcZtwS0onsTlU3hsAqdu6587WslaTX8C5Y78SEbIy0pfcPvLW01UqsgFsAaAkPlEALw_wcB")

        // Configurar el botón para comenzar
        binding.btnComenzar.setOnClickListener {
            val gestiones = Intent(this, Gestiones::class.java)
            startActivity(gestiones)
        }

        binding.btnUserInfo?.setOnClickListener {
            // Reemplazar el contenido actual con el UserSettingsFragment
            val fragment = UserSettingsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
            // Esconder el botón btnComenzar y btnUserInfo
            binding.btnComenzar?.visibility = View.GONE
            binding.btnUserInfo?.visibility = View.GONE
        }

        // Listener para mostrar el botón al volver atrás
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                // Mostrar los botones nuevamente
                binding.btnComenzar?.visibility = View.VISIBLE
                binding.btnUserInfo?.visibility = View.VISIBLE
            }
        }

    }

    // Método onCreateOptionsMenu para inflar el menú
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    // Método onOptionsItemSelected para manejar los clics en los elementos del menú
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
}