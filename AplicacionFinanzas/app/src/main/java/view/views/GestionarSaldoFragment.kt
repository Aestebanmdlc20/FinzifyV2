package view.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.aplicacionfinanzas.R
import com.example.aplicacionfinanzas.databinding.FragmentGestionarSaldoBinding
import utils.SessionManager
import viewmodel.UsuarioViewModel

class GestionarSaldoFragment : Fragment() {

    private lateinit var binding: FragmentGestionarSaldoBinding
    private lateinit var usuarioViewModel: UsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGestionarSaldoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sessionManager = SessionManager(requireContext())
        usuarioViewModel = UsuarioViewModel(requireActivity().application)
        val usuario = sessionManager.getUser()
        binding.txtSaldoActual.text = getString(R.string.saldo_actual) + usuario?.saldo + "€"

        binding.btnAddSaldo.setOnClickListener {
            val saldo = binding.edtSaldo.text.toString().toDoubleOrNull()
            if (saldo != null) {
                // Creo un alert dialog
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Confirmar ingreso")
                builder.setMessage("¿Está seguro que quiere añadir $saldo€ a su saldo?")

                // Botonones del alert dialog
                builder.setPositiveButton("Accept") { dialog, which ->
                    // Añado el saldo
                    usuario?.saldo = usuario?.saldo?.plus(saldo)
                    // Actualizo el textview
                    binding.txtSaldoActual.text = getString(R.string.saldo_actual) + usuario?.saldo + "€"
                    if (usuario != null) {
                        usuarioViewModel.actualizarUsuario(usuario)
                    }
                }
                builder.setNegativeButton("Cancel") { dialog, which ->

                    dialog.dismiss()
                }

                builder.show()
            }
        }

        binding.btnRetirarSaldo.setOnClickListener {
            val saldo = binding.edtSaldo.text.toString().toDoubleOrNull()
            if (saldo != null) {
                // Creo un alerdialog
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Confirmar retirada")
                builder.setMessage("¿Está seguro que quiere retirar $saldo€ de su saldo?")

                builder.setPositiveButton("Accept") { dialog, which ->
                    // Resto el saldo
                    usuario?.saldo = usuario?.saldo?.minus(saldo)
                    // Actualizo el textview con el nuevo saldo
                    binding.txtSaldoActual.text = getString(R.string.saldo_actual) + usuario?.saldo + "€"

                    if (usuario != null) {
                        usuarioViewModel.actualizarUsuario(usuario)
                    }
                }
                builder.setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }

                builder.show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("saldoIngresado", binding.edtSaldo.text.toString())
        // outState.putDouble("saldoActual", usuario.saldo)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            binding.edtSaldo.setText(savedInstanceState.getString("saldoIngresado"))
            // usuario.saldo = savedInstanceState.getDouble("saldoActual")
            // binding.txtSaldoActual.text = getString(R.string.saldo_actual) + usuario.saldo + "€"
        }
    }
}