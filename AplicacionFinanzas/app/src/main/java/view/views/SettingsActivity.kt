package view.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionfinanzas.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_settings) // Layout básico con un contenedor de fragmento

        // Cargar el fragmento de preferencias
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment())
            .commit()
    }
}