import java.util.Arrays
import java.io.File
import libGrafoR
/*
val Routes = mutableMapOf<String, Int>(
    "USB" to 0,
    "McDonald’s_Trinidad" to 1,
    "Chacaíto" to 2
)

fun readRoute(route: String): GrafoNoDirigidoCosto {
*/

fun main() {
    // Crear un grafo dirigido con costo
    val grafo = GrafoDirigidoCosto(3) // 3 vértices: USB (0), McDonald's_Trinidad (1), Chacaito (2)

    // Agregar aristas con costos (distancia y combustible)
    grafo.agregarArcoCosto(ArcoCosto(0, 1, 10.0)) // USB - McDonald's_Trinidad (10 km)
    grafo.agregarArcoCosto(ArcoCosto(1, 0, 10.0)) // McDonald's_Trinidad - USB (10 km)
    grafo.agregarArcoCosto(ArcoCosto(0, 2, 15.0)) // USB - Chacaíto (15 km)
    grafo.agregarArcoCosto(ArcoCosto(2, 1, 5.0)) // Chacaíto - McDonald's_Trinidad (5 km)
    grafo.agregarArcoCosto(ArcoCosto(2, 0, 15.0)) // Chacaíto - USB (15 km)

    // Aplicar Kruskal para la distancia
    val mstDistancia = kruskal(grafo)
    println("MST basado en distancia:")
    for (arista in mstDistancia) {
        println("${arista.x} - ${arista.y} (${arista.costo()} km)")
    }

    // Crear otro grafo para combustible
    val grafoCombustible = GrafoDirigidoCosto(3)
    grafoCombustible.agregarArcoCosto(ArcoCosto(0, 1, 8.0))
    grafoCombustible.agregarArcoCosto(ArcoCosto(1, 0, 12.0))
    grafoCombustible.agregarArcoCosto(ArcoCosto(0, 2, 15.0))
    grafoCombustible.agregarArcoCosto(ArcoCosto(2, 1, 6.0))
    grafoCombustible.agregarArcoCosto(ArcoCosto(2, 0, 15.0))

    // Aplicar Kruskal para combustible
    val mstCombustible = kruskal(grafoCombustible)
    println("\nMST basado en combustible:")
    for (arista in mstCombustible) {
        println("${arista.x} - ${arista.y} (${arista.costo()} litros)")
    }
}
/* 
Función para leer un archivo de texto con entradas que pueden ser de tipo string,
el cual retornara un grafo no dirigido ponderado con la informacion proporcionada
en el archivo.txt
*/
  val file = File(route)
  val lines = file.readLines()

  if (lines.isEmpty()) throw IllegalArgumentException("El archivo está vacío")

  // La primera línea es el número de vértices
  val vertices = lines[0].toInt()
  if ( vertices != 2 && vertices != 3){
      throw Exception("El número de paradas no coincide")
  }
  var graphNoD = GrafoNoDirigidoCosto(vertices)

  // Las siguientes líneas consisten en la creacion de las aristas ponderadas
  for (i in 2 until lineas.size) {
      var (start, end, costStr) = lines[i].split(" ")
      var u = Routes[start] ?: throw IllegalArgumentException("Parada no encontrada: $start")
      var v = Routes[end] ?: throw IllegalArgumentException("Parada no encontrada: $end")
      try{
          var cost = costStr.toDouble()
          graphNoD.addAristaCosto(AristaCosto(u, v, cost))
      } catch (e: Exception){
          throw Exception("El costo no tiene el formato adecuado")
        }
  }
  return graphNoD
}

fun main(args: Array<String>) {
/* Funcion que se encargara de calcular la ruta menos costosa del autobús */

  val graphNoD = readRoute("grafos/rutaOptUSB.txt")
  var edges = graphNoD.iterator()
  var listEdges = mutableListOf<AristaCosto>()

  for (edge in edges){
      list_edges.add(edge)
  }

  //EL numero de vertices debe ser 3, si no, se lanzara una excepcion
  if (graphNoD.numDeVertices != 3){
    throw Exception("El número de vertices no coincide")
  }

  var mst = kruskal(listEdges, 3)

  for edge in mst{
    var stopStart = String()
    var stopEnd = String()

    for (stop, value in Routes){
      if (value == edge.a){
        stopStart = stop
      }
      if (value == edge.b){
        stopEnd = stop
      }
    }
    println("$stopStart --(${edge.cost})-- $stopEnd")
  }
}

