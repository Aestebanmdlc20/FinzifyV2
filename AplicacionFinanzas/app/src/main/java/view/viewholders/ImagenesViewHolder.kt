package view.viewholders

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionfinanzas.databinding.ItemImagenesBinding

class ImagenesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemImagenesBinding.bind(itemView)
    val imagenView: ImageView = binding.idImagenView
}