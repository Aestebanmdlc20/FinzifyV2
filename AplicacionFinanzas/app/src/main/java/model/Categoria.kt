package model

data class Categoria(
 var id: Int? = null,
 var nombre: String? = null
) {
 // No-argument constructor required for Firebase
 constructor() : this(null, null)

 override fun toString(): String {
  return nombre ?: ""
 }
}
