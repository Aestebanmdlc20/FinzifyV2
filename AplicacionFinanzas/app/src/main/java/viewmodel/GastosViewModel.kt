package viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import model.Gasto
import persistence.AppRepositorio

class GastosViewModel(application: Application) : AndroidViewModel(application) {
    private val appRepositorio = AppRepositorio()
    private val _gastos = MutableLiveData<List<Gasto>>()
    val gastos: LiveData<List<Gasto>> get() = _gastos

    fun agregarGasto(gasto: Gasto) {
        // Obtiene una referencia a la base de datos de Firebase
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.getReference("gastos") // Refiere a la ruta 'gastos'

        // Usa `push()` para generar un ID único para cada nuevo gasto
        val nuevoGastoRef = databaseReference.push() // Genera una referencia única

        // Asocia el gasto a esa nueva referencia generada
        nuevoGastoRef.setValue(gasto) // Inserta el gasto en la base de datos con un ID único generado automáticamente
    }


    fun obtenerGasto(id: Int): Gasto? {
        return appRepositorio.obtenerGasto(id)
    }

    fun actualizarGasto(gasto: Gasto) {
        appRepositorio.actualizarGasto(gasto)
    }

    fun eliminarGasto(id: Int) {
        appRepositorio.eliminarGasto(id)
    }

    fun obtenerGastosPorCategoriaDeUnUsuario(idCategoria: Int, idUsuario: String) {
        appRepositorio.obtenerGastosPorUsuarioYCategoria(idUsuario, idCategoria, _gastos)
    }

}
