package model

data class Usuario(
 var correo: String? = null,
 var nombre: String? = null,
 var password: String? = null,
 var saldo: Double? = null
) {
 // No-argument constructor required for Firebase
 constructor() : this(null, null, null, null)
}
