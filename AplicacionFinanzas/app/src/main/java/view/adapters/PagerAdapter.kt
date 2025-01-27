package view.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import view.views.OcioFragment
import view.views.OtrosFragment
import view.views.SaludFragment
import view.views.TransporteFragment

// Clase PagerAdapter que extiende FragmentStateAdapter para manejar los fragmentos en ViewPager2
class PagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    // Método que retorna la cantidad de fragmentos
    override fun getItemCount(): Int {
        return 4 // Retorna el número de fragmentos
    }

    // Método que crea y retorna el fragmento correspondiente a la posición dada
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> OcioFragment() // Retorna el fragmento OcioFragment para la posición 0
            1 -> TransporteFragment() // Retorna el fragmento TransporteFragment para la posición 1
            2 -> SaludFragment() // Retorna el fragmento SaludFragment para la posición 2
            3 -> OtrosFragment() // Retorna el fragmento OtrosFragment para la posición 3
            else -> throw Resources.NotFoundException("Position not found") // Lanza una excepción si la posición no es válida
        }
    }
}