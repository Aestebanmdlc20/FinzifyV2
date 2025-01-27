package viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import model.Usuario
import persistence.AppRepositorio

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val appRepositorio = AppRepositorio()
    private val _usuarios = MutableLiveData<List<Usuario>>()
    val usuarios: LiveData<List<Usuario>> get() = _usuarios
    private val databaseReference = FirebaseDatabase.getInstance().reference

    // Función para agregar usuario
    fun agregarUsuario(usuario: Usuario) {
        appRepositorio.agregarUsuario(usuario)
        actualizarUsuarios()
    }

    // Función corregida para obtener usuario por nombre desde Firebase
    fun obtenerUsuarioPorNombre(nombre: String, callback: (Usuario?) -> Unit) {
        // Llamamos a Firebase para obtener los usuarios
        databaseReference.child("usuarios").get()
            .addOnSuccessListener { snapshot ->
                var usuarioEncontrado: Usuario? = null

                // Recorrer los usuarios para encontrar el que coincida con el nombre
                for (usuarioSnapshot in snapshot.children) {
                    val usuario = usuarioSnapshot.getValue(Usuario::class.java)
                    if (usuario?.nombre == nombre) {
                        usuarioEncontrado = usuario
                        break
                    }
                }

                // Llamamos al callback con el usuario encontrado
                callback(usuarioEncontrado)
            }
            .addOnFailureListener {
                // Si ocurre un error, pasamos null al callback
                callback(null)
            }
    }

    // Función para actualizar el usuario
    fun actualizarUsuario(usuario: Usuario) {
        appRepositorio.actualizarUsuario(usuario)
        actualizarUsuarios()
    }

    // Función para eliminar el usuario
    fun eliminarUsuario(correo: String) {
        appRepositorio.eliminarUsuario(correo)
        actualizarUsuarios()
    }

    // Función para actualizar la lista de usuarios (si es necesario)
    private fun actualizarUsuarios() {
        appRepositorio.obtenerUsuarios { usuarios ->
            _usuarios.postValue(usuarios)
        }
    }
}
