import java.util.Arrays
import java.io.File
import libGrafoR

val Routes = mutableMapOf<String, Int>(
    "USB" to 0,
    "McDonald’s_Trinidad" to 1,
    "Chacaíto" to 2
)

fun readRoute(route: String): GrafoNoDirigidoCosto {
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

