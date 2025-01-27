package view.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.aplicacionfinanzas.R
import com.example.aplicacionfinanzas.databinding.FragmentTransporteBinding
import utils.SessionManager
import viewmodel.CategoriaViewModel
import viewmodel.GastosViewModel

class TransporteFragment : Fragment() {

    private lateinit var binding: FragmentTransporteBinding
    private lateinit var categoriaViewModel: CategoriaViewModel
    private lateinit var gastoViewModel: GastosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransporteBinding.inflate(inflater, container, false)

        // Inicializar los ViewModels
        categoriaViewModel = ViewModelProvider(this)[CategoriaViewModel::class.java]
        gastoViewModel = ViewModelProvider(this)[GastosViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sm: SessionManager = SessionManager(requireContext())
        val correoUsuario = sm.getUser()?.correo

        if (correoUsuario == null) {
            Toast.makeText(requireContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show()
            return
        }

        // Cargar categorías y observar cambios
        categoriaViewModel.obtenerCategorias()
        categoriaViewModel.categorias.observe(viewLifecycleOwner) { categorias ->
            val categoriaTransporte = categorias.find { it.nombre == "Transporte" }

            if (categoriaTransporte != null) {
                categoriaTransporte.id?.let { cargarGastosTransporte(it, correoUsuario) }
            } else {
                Toast.makeText(requireContext(), "Categoría 'Transporte' no encontrada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cargarGastosTransporte(categoriaId: Int, correoUsuario: String) {
        // Cargar los gastos de la categoría "Ocio"
        gastoViewModel.obtenerGastosPorCategoriaDeUnUsuario(categoriaId, correoUsuario)

        // Observar los datos cargados
        gastoViewModel.gastos.observe(viewLifecycleOwner) { gastosTransporte ->
            if (gastosTransporte.isEmpty()) {
                Toast.makeText(requireContext(), "No hay gastos en 'Transporte'", Toast.LENGTH_SHORT).show()
            }

            // Configurar el adaptador de la lista
            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.list_item,
                gastosTransporte.map { it.nombre }
            )
            binding.listViewGastos.adapter = adapter

            // Configurar el listener de los items
            binding.listViewGastos.setOnItemClickListener { _, _, position, _ ->
                val gasto = gastosTransporte[position]
                val intent = Intent(requireContext(), DetalleGasto::class.java)
                val bundle = Bundle()
                bundle.putString("nombre", gasto.nombre)
                bundle.putString("descripcion", gasto.descripcion)
                bundle.putDouble("monto", gasto.monto)
                bundle.putString("categoria", "Transporte")
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }
}