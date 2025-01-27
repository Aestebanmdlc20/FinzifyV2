package persistence.listeners

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import model.Categoria

class CategoriaDataListener (var dataList: MutableLiveData<List<Categoria>>) : ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        val listaCategorias = mutableListOf<Categoria>()
        for (childSnapshot in snapshot.children) {
            val categoria = childSnapshot.getValue(Categoria::class.java)
            if (categoria != null) {
                listaCategorias.add(categoria)
            }
        }
        dataList.postValue(listaCategorias)
    }

    override fun onCancelled(error: DatabaseError) {
        // Handle possible errors.
    }
}
