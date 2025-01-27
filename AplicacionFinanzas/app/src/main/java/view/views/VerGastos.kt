package view.views

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.aplicacionfinanzas.R
import com.example.aplicacionfinanzas.databinding.ActivityVerGastosBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import view.adapters.PagerAdapter

// Clase VerGastos que extiende AppCompatActivity
class VerGastos : AppCompatActivity() {
    // Declaración de la variable binding para acceder a las vistas
    private lateinit var binding: ActivityVerGastosBinding
    // Declaración de la variable tabLayout para las pestañas
    private lateinit var tabLayout: TabLayout
    // Declaración de la variable viewPager para el ViewPager2
    private lateinit var viewPager: ViewPager2

    // Método onCreate que se llama cuando se crea la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflar el layout usando View Binding
        binding = ActivityVerGastosBinding.inflate(layoutInflater)
        // Establecer el contenido de la vista con el layout inflado
        setContentView(binding.root)

        // Configurar la barra de herramientas
        val toolbar: MaterialToolbar = binding.menuBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        // Inicializar tabLayout y viewPager
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        // Establecer el adaptador para el viewPager
        viewPager.adapter = PagerAdapter(this)

        // Configurar TabLayoutMediator para conectar tabLayout y viewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, index ->
            // Establecer el texto de las pestañas según el índice
            tab.text = when (index) {
                0 -> "Ocio 🎉"
                1 -> "Transporte 🚗"
                2 -> "Salud 🏥"
                3 -> "Otros 📝"
                else -> throw Resources.NotFoundException("Position not found")
            }
        }.attach()
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