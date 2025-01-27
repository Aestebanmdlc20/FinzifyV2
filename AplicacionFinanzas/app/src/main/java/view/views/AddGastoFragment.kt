package view.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import com.example.aplicacionfinanzas.R
import com.example.aplicacionfinanzas.databinding.FragmentAddGastoBinding
import model.Categoria
import model.Gasto
import model.Usuario
import utils.SessionManager
import viewmodel.CategoriaViewModel
import viewmodel.GastosViewModel
import viewmodel.UsuarioViewModel

class AddGastoFragment : Fragment() {

    private lateinit var binding: FragmentAddGastoBinding
    private lateinit var categoriaViewModel: CategoriaViewModel
    private lateinit var gastoViewModel: GastosViewModel
    private lateinit var usuarioViewModel: UsuarioViewModel  // Añadir un ViewModel para usuario
    private var usuario: Usuario? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddGastoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sm = SessionManager(requireContext())
        usuario = sm.getUser()
        // Inicializar los ViewModels
        categoriaViewModel = CategoriaViewModel(application = requireActivity().application)
        gastoViewModel = GastosViewModel(application = requireActivity().application)
        usuarioViewModel = UsuarioViewModel(application = requireActivity().application)

        // Cargar categorías desde el ViewModel
        val categorias = categoriaViewModel.obtenerCategorias()

        // Adaptador para el Spinner
        // Define fixed categories
        val fixedCategorias = listOf(
            Categoria(id = 1, nombre = "Ocio 🎉"),
            Categoria(id = 2, nombre = "Transporte 🚗"),
            Categoria(id = 3, nombre = "Salud 🏥"),
            Categoria(id = 4, nombre = "Otros 📝")
        )

        val adapter = object : ArrayAdapter<Categoria>(requireContext(), android.R.layout.simple_spinner_item, fixedCategorias) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.setTextColor(ContextCompat.getColor(requireContext(), R.color.barra))
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                return view
            }
        }

        binding.spinnerCategorias.adapter = adapter

        // Establecer el recurso para el dropdown
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.spinnerCategorias.adapter = adapter

        // Establecer la selección predeterminada
        binding.spinnerCategorias.setSelection(0)  // Esto selecciona la primera categoría por defecto

        // Manejo del botón Añadir Gasto
        binding.btnAddGasto.setOnClickListener {
            val selectedItem = binding.spinnerCategorias.selectedItem
            if (selectedItem == null) {
                Toast.makeText(requireContext(), "Please select a category", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val categoriaSeleccionada = selectedItem as Categoria
            if (categoriaSeleccionada.nombre == "Ocio 🎉") {
                categoriaSeleccionada.id = 1
            } else if (categoriaSeleccionada.nombre == "Transporte 🚗") {
                categoriaSeleccionada.id = 2
            } else if (categoriaSeleccionada.nombre == "Salud 🏥") {
                categoriaSeleccionada.id = 3
            } else if (categoriaSeleccionada.nombre == "Otros 📝") {
                categoriaSeleccionada.id = 4
            }
            val nombre = binding.inputLayoutNombre.editText?.text.toString()
            val descripcion = binding.inputLayoutDescripcion.editText?.text.toString()
            val montoStr = binding.inputLayoutMonto.editText?.text.toString()

            // Validación del campo de nombre
            if (nombre.isEmpty()) {
                binding.inputLayoutNombre.error = getString(R.string.gasto_name_error)
                return@setOnClickListener
            } else if (nombre.length > 20) {
                binding.inputLayoutNombre.error = getString(R.string.gasto_name_error_max_car)
                return@setOnClickListener
            } else {
                binding.inputLayoutNombre.error = null
            }

            // Validación del campo de monto
            if (montoStr.isEmpty()) {
                binding.inputLayoutMonto.error = getString(R.string.gasto_monto_error)
                return@setOnClickListener
            } else if (montoStr.length > 6) {
                binding.inputLayoutMonto.error = getString(R.string.gasto_monto_error_max)
                return@setOnClickListener
            } else {
                binding.inputLayoutMonto.error = null
            }

            // Validación si el monto es numérico
            val monto = try {
                montoStr.toInt()
            } catch (e: NumberFormatException) {
                binding.inputLayoutMonto.error = getString(R.string.gasto_monto_error_num)
                return@setOnClickListener
            }

            // Crear y agregar el nuevo gasto
            val gasto = categoriaSeleccionada.id?.let { it1 ->
                Gasto(
                    nombre = nombre,
                    descripcion = descripcion,
                    monto = monto.toDouble(),
                    correoUsuario = usuario?.correo ?: "",
                    idCategoria = it1
                )
            }
            if (gasto != null) {
                gastoViewModel.agregarGasto(gasto)
            }

            // Actualizar el saldo del usuario
            if (usuario != null) {
                usuario?.saldo = usuario?.saldo?.minus(monto.toDouble())
                usuarioViewModel.actualizarUsuario(usuario!!)
            }

            Toast.makeText(requireContext(), R.string.gasto_added, Toast.LENGTH_SHORT).show()

            // Volver a la pantalla anterior (Gestiones)
            requireActivity().onBackPressed()
        }
    }

    // Para guardar el estado de la vista
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("nombre", binding.inputLayoutNombre.editText?.text.toString())
        outState.putString("descripcion", binding.inputLayoutDescripcion.editText?.text.toString())
        outState.putString("monto", binding.inputLayoutMonto.editText?.text.toString())
        outState.putInt("categoria", binding.spinnerCategorias.selectedItemPosition)
    }

    // Para restaurar el estado de la vista
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            binding.inputLayoutNombre.editText?.setText(savedInstanceState.getString("nombre"))
            binding.inputLayoutDescripcion.editText?.setText(savedInstanceState.getString("descripcion"))
            binding.inputLayoutMonto.editText?.setText(savedInstanceState.getString("monto"))
            binding.spinnerCategorias.setSelection(savedInstanceState.getInt("categoria"))
        }
    }
}
