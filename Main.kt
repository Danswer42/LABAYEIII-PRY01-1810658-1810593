//Punto de entrada del programa

fun main () {
  //Lee el archivo de entrada y construye el grafo
  val graph = FileUtils.readInputFile("rutaOptUSB.txt")

  //Define las paradas de inicio y fin
  val origin = Stop("USB")
  val destination = Stop("Chacaito")

  //Calcula la ruta óptima
  val optimalRoute = graph.findOptimalRoute(origin, destination)
  println("Optimal route: ${optimalRoute.joinToString(" -> ") { it.name }}")
}