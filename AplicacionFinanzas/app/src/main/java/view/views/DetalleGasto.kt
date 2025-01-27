package view.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionfinanzas.databinding.ActivityDetalleGastoBinding

// Clase DetalleGasto que extiende AppCompatActivity
class DetalleGasto : AppCompatActivity() {
    // Declaración de la variable binding para acceder a las vistas
    private lateinit var binding: ActivityDetalleGastoBinding

    // Método onCreate que se llama cuando se crea la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflar el layout usando View Binding
        binding = ActivityDetalleGastoBinding.inflate(layoutInflater)
        // Establecer el contenido de la vista con el layout inflado
        setContentView(binding.root)

        // Obtener los datos del gasto de la actividad anterior
        val bundle = intent.extras
        if (bundle != null) {
            val nombre = bundle.getString("nombre")
            val descripcion = bundle.getString("descripcion")
            val monto = bundle.getDouble("monto")
            val categoria = bundle.getString("categoria")

            // Establecer los datos del gasto en las vistas correspondientes
            binding.nombre.text = nombre
            binding.descripcion.text = descripcion
            binding.monto.text = monto.toString()

        }
    }
}