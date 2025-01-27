package view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionfinanzas.R
import model.Gasto
import viewmodel.GastosViewModel

class AdaptadorGastos(private val gastos: List<Gasto>, private val viewModel: GastosViewModel): RecyclerView.Adapter<AdaptadorGastos.GastoViewHolder>() {

    class GastoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.nombre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GastoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gasto, parent, false)
        return GastoViewHolder(view)
    }

    override fun onBindViewHolder(holder: GastoViewHolder, position: Int) {
        val gasto = gastos[position]
        holder.nombre.text = gasto.nombre
        holder.itemView.setOnClickListener {
            //viewModel.seleccionarGasto(gasto)
        }
        holder.itemView.setOnLongClickListener{
            gasto.id?.let { id -> viewModel.eliminarGasto(id) }
            true
        }
    }

    override fun getItemCount(): Int {
        return gastos.size
    }

}