package viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import model.Categoria
import persistence.AppRepositorio

class CategoriaViewModel(application: Application) : AndroidViewModel(application) {
    private val appRepositorio = AppRepositorio()
    private val _categorias = MutableLiveData<List<Categoria>>()
    private val _categoriaPorNombre = MutableLiveData<Categoria?>()
    val categorias: LiveData<List<Categoria>> get() = _categorias

    fun agregarCategoria(categoria: Categoria) {
        appRepositorio.agregarCategoria(categoria)
        actualizarCategorias()
    }

    fun obtenerCategorias() {
        appRepositorio.obtenerCategorias { categorias ->
            _categorias.postValue(categorias)
        }
    }

    val categoriaPorNombre: LiveData<Categoria?> get() = _categoriaPorNombre

    fun obtenerCategoriaPorNombre(nombre: String) : Categoria? {
        return appRepositorio.obtenerCategoriaPorNombre(nombre)
    }

    // Método para obtener una categoría por ID
    fun obtenerCategoriaPorId(id: Int): Categoria? {
        return _categorias.value?.find { it.id == id }
    }

    fun actualizarCategoria(categoria: Categoria) {
        appRepositorio.actualizarCategoria(categoria)
        actualizarCategorias()
    }

    fun eliminarCategoria(nombre: String) {
        appRepositorio.eliminarCategoria(nombre)
        actualizarCategorias()
    }

    private fun actualizarCategorias() {
        // Logic to update the _categorias LiveData from the repository
    }
}