package model

data class Gasto(
 var id: Int,
 val nombre: String,
 val descripcion: String,
 val monto: Double,
 var correoUsuario: String,
 val idCategoria: Int
) {
 constructor(nombre: String, descripcion: String, monto: Double, correoUsuario: String, idCategoria: Int) : this(
  id = 0, // Default value for id
  nombre = nombre,
  descripcion = descripcion,
  monto = monto,
  correoUsuario = correoUsuario,
  idCategoria = idCategoria
 )
 constructor() : this(0, "", "", 0.0, "", 0)
}