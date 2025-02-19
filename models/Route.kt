//Clase que representaa una ruta
data class Route (
  val origin: Stop,
  val destination: Stop,
  val distance: Double,
  val fuel: Double
)