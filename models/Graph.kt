//Clase que representa el grafo y contiene la lógica de rutas
class Graph {
  private val stops = mutableListOf<Stop>()
  private val routes = mutableListOf<Route>()

  //Añade una parada al grafo
  fun addStop(stop: Stop) {
    stops.add(stop)
  }

  //Añade una ruta al grafo
  fun addRoute(route: Route) {
    routes.add(route)
  }

  //Implementación del algoritmo de Dijkstra para encontrar la ruta óptima
  fun findOptimalRoute(origin: Stop, destination: Stop): List<Stop> {
    val distances = mutableMapOf<Stop, Double>()
    val previous = mutableMapOf<Stop, Stop?>()
    val unvisited = mutableSetOf<Stop>()

    stops.forEach { stop -> 
      distances[stop] = Double.MAX_VALUE
      previous[stop] = null
      unvisited.add(stop)
    }

    distaance[origin] = 0.0

    while (unvisited.isNotEmpty()) {
      val current = unvisited.minByOrNull { distances[it] ?: Double.MAX_VALUE }!!
      unvisited.remove(current)

      routes.filter { it.origin == current }.forEach {route ->
        val alt = distances[current]!! + route.distance
        if (alt < distances[route.destination]!!) {
          distances[route.destination] = alt
          previous[route.destination] = current
        }
      } 
    }

    val path = mutableListOf<Stop>()
    var current : Stop? = destination
    while (current != origin) {
      path.add(current!!)
      current = previous[current]
    }
    path.add(origin)
    return path.reversed()
}