package persistence.listeners

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import model.Gasto

class GastoDataListener(var dataList: MutableLiveData<List<Gasto>>) : ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        val listaGastos = mutableListOf<Gasto>()
        for (childSnapshot in snapshot.children) {
            val gasto = childSnapshot.getValue(Gasto::class.java)
            if (gasto != null) {
                listaGastos.add(gasto)
            }
        }
        dataList.postValue(listaGastos)
    }

    override fun onCancelled(error: DatabaseError) {
        // Handle possible errors.
    }
}