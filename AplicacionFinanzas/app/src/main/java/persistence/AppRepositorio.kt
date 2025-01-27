package persistence

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await
import model.Categoria
import model.Gasto
import model.Usuario
import persistence.listeners.CategoriaDataListener
import persistence.listeners.GastoDataListener
import persistence.listeners.UsuarioDataListener

class AppRepositorio {
    private val urlDatabase = "https://finzify-47a9f-default-rtdb.europe-west1.firebasedatabase.app/"
    private var databaseReference: DatabaseReference

    init {
        databaseReference = FirebaseDatabase.getInstance(urlDatabase).reference
    }

    // Gasto methods

    fun agregarGasto(gasto: Gasto) {
        databaseReference.child("gastos").child(gasto.id.toString()).setValue(gasto)
    }

    fun obtenerGasto(id: Int) : Gasto? {
        val gastos = MutableLiveData<List<Gasto>>()
        var fireBaseDataListener = GastoDataListener(gastos)
        databaseReference.child("gastos").child(id.toString()).get()
        return gastos.value?.get(0)
    }

    fun actualizarGasto(gasto: Gasto) {
        databaseReference.child("gastos").child(gasto.id.toString()).setValue(gasto)
    }

    fun eliminarGasto(id: Int) {
        databaseReference.child("gastos").child(id.toString()).removeValue()
    }

    fun obtenerGastosPorUsuarioYCategoria(
        correoUsuario: String,
        idCategoria: Int,
        dataList: MutableLiveData<List<Gasto>>
    ) {
        val database = Firebase.database
        val gastosRef = database.getReference("gastos")

        // Crear un ValueEventListener personalizado para filtrar los datos
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaGastos = mutableListOf<Gasto>()
                for (childSnapshot in snapshot.children) {
                    val gasto = childSnapshot.getValue(Gasto::class.java)
                    if (gasto != null && gasto.correoUsuario == correoUsuario && gasto.idCategoria == idCategoria) {
                        listaGastos.add(gasto)
                    }
                }
                // Actualizar el LiveData con la lista filtrada
                dataList.postValue(listaGastos)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error al obtener los gastos: ${error.message}")
            }
        }
        // Asignar el listener a la referencia de la base de datos
        gastosRef.addListenerForSingleValueEvent(listener)
    }


    // Categoria methods
    fun agregarCategoria(categoria: Categoria) {
        categoria.nombre?.let { databaseReference.child("categorias").child(it).setValue(categoria) }
    }

    fun obtenerCategoriaPorNombre(nombre: String) : Categoria? {
        val categorias = MutableLiveData<List<Categoria>>()
        var fireBaseDataListener = CategoriaDataListener(categorias)
        databaseReference.child("categorias").child(nombre).get()
        return categorias.value?.get(0)
    }

    fun obtenerCategorias(callback: (List<Categoria>) -> Unit) {
        val categorias = mutableListOf<Categoria>()
        databaseReference.child("categorias").get().addOnSuccessListener { dataSnapshot ->
            for (childSnapshot in dataSnapshot.children) {
                val categoria = childSnapshot.getValue(Categoria::class.java)
                if (categoria != null) {
                    categorias.add(categoria)
                }
            }
            callback(categorias)
        }.addOnFailureListener {
            Log.e("Firebase", "Error al obtener los usuarios: ${it.message}")
            callback(emptyList())
        }
    }


    fun actualizarCategoria(categoria: Categoria) {
        categoria.nombre?.let { databaseReference.child("categorias").child(it).setValue(categoria) }
    }

    fun eliminarCategoria(nombre: String) {
        databaseReference.child("categorias").child(nombre).removeValue()
    }

    // Usuario methods

    fun obtenerUsuarios(callback: (List<Usuario>) -> Unit) {
        val usuarios = mutableListOf<Usuario>()
        databaseReference.child("usuarios").get().addOnSuccessListener { dataSnapshot ->
            for (childSnapshot in dataSnapshot.children) {
                val usuario = childSnapshot.getValue(Usuario::class.java)
                if (usuario != null) {
                    usuarios.add(usuario)
                }
            }
            callback(usuarios)
        }.addOnFailureListener {
            Log.e("Firebase", "Error al obtener los usuarios: ${it.message}")
            callback(emptyList())
        }
    }

    fun agregarUsuario(usuario: Usuario) {
        usuario.correo?.let { databaseReference.child("usuarios").child(it).setValue(usuario) }
    }

    fun obtenerUsuarioPorNombre(nombre: String, callback: (Usuario?) -> Unit) {
        val databaseReference = FirebaseDatabase.getInstance().reference
        // Accede al nodo "usuarios"
        databaseReference.child("usuarios").get()
            .addOnSuccessListener { snapshot ->
                var usuarioEncontrado: Usuario? = null
                // Recorrer todos los usuarios en el nodo "usuarios"
                for (usuarioSnapshot in snapshot.children) {
                    val usuario = usuarioSnapshot.getValue(Usuario::class.java)
                    // Verificar si el nombre coincide
                    if (usuario?.nombre == nombre) {
                        usuarioEncontrado = usuario
                        break
                    }
                }
                // Llamar al callback con el usuario encontrado o null
                callback(usuarioEncontrado)
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    fun actualizarUsuario(usuario: Usuario) {
        // Buscamos el usuario por su correo en lugar de su ID
        val usuarioKey = usuario.correo?.replace("@", "_")?.replace(".", "_") // Usamos una convención para tratar los caracteres especiales

        // Ahora actualizamos la información del usuario en la base de datos usando su "key" (correo modificado)
        if (usuarioKey != null) {
            databaseReference.child("usuarios").child(usuarioKey).setValue(usuario)
        }
    }

    fun eliminarUsuario(correo: String) {
        databaseReference.child("usuarios").child(correo).removeValue()
    }
}