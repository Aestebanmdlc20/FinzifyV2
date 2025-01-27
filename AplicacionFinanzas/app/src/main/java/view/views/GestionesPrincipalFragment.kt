package view.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.aplicacionfinanzas.R
import com.example.aplicacionfinanzas.databinding.FragmentGestionesPrincipalBinding
import model.ImagenesProvider
import view.adapters.AdaptadorImagenes

class GestionesPrincipalFragment : Fragment() {
    private lateinit var binding: FragmentGestionesPrincipalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGestionesPrincipalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddGasto.setOnClickListener {
            val fragment = AddGastoFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.btnVerGastos.setOnClickListener {
            val verGastos = Intent(requireContext(), VerGastos::class.java)
            startActivity(verGastos)
        }

        binding.btnGestionarSaldo.setOnClickListener {
            val fragment = GestionarSaldoFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvImages.layoutManager = manager
        // Para el efecto de "snap"
        // efecto de carrusel donde los elementos se centran automáticamente al visualizarlos
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvImages)

        binding.rvImages.adapter = AdaptadorImagenes(ImagenesProvider.imagenesList)

        // Crear un DividerItemDecoration y agregarlo al RecyclerView
        val decoration = DividerItemDecoration(context, manager.orientation)
        binding.rvImages.addItemDecoration(decoration)


    }

}