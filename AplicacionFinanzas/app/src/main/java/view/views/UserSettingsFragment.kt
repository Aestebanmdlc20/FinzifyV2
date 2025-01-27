package view.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionfinanzas.R
import com.example.aplicacionfinanzas.databinding.FragmentUserSettingsBinding
import utils.SessionManager

class UserSettingsFragment : Fragment() {

    private lateinit var binding: FragmentUserSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sm = SessionManager(requireContext())
        val usuario = sm.getUser()
        binding.txtNombre.text = usuario?.nombre
        binding.txtCorreo.text = usuario?.correo
        binding.txtSaldo.text = usuario?.saldo.toString()

        binding.btnAtras.setOnClickListener {
            val intent = Intent(requireContext(), Inicio::class.java)
            startActivity(intent)
        }
    }
}