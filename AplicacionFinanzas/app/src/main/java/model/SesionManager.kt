package utils

import android.content.Context
import android.content.SharedPreferences
import model.Usuario
import com.google.gson.Gson

class SessionManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private const val PREF_NAME = "user_session"
        private const val KEY_USER = "user"
    }

    // Guardar el usuario
    fun saveUser(usuario: Usuario) {
        val gson = Gson()
        val userJson = gson.toJson(usuario)
        editor.putString(KEY_USER, userJson)
        editor.apply()
    }

    // Obtener el usuario
    fun getUser(): Usuario? {
        val gson = Gson()
        val userJson = sharedPreferences.getString(KEY_USER, null)
        return if (userJson != null) {
            gson.fromJson(userJson, Usuario::class.java)
        } else {
            null
        }
    }

    // Verificar si el usuario está autenticado
    fun isUserLoggedIn(): Boolean {
        return getUser() != null
    }

    // Cerrar sesión (eliminar usuario)
    fun logout() {
        editor.remove(KEY_USER)
        editor.apply()
    }
}
