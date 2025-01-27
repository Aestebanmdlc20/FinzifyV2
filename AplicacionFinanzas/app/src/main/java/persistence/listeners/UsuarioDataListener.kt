package persistence.listeners

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import model.Usuario

class UsuarioDataListener(var dataList: MutableLiveData<List<Usuario>>) : ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        val listaUsuarios = mutableListOf<Usuario>()
        for (childSnapshot in snapshot.children) {
            val usuario = childSnapshot.getValue(Usuario::class.java)
            if (usuario != null) {
                listaUsuarios.add(usuario)
            }
        }
        dataList.postValue(listaUsuarios)
    }

    override fun onCancelled(error: DatabaseError) {
        // Handle possible errors.
    }
}