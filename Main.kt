import java.util.Arrays
import java.io.File

// Definición de las clases necesarias (ArcoCosto, GrafoDirigidoCosto, GrafoNoDirigidoCosto)

data class ArcoCosto(val x: Int, val y: Int, val costo: Double)

class GrafoDirigidoCosto(val numVertices: Int) {
  private val adjList = Array(numVertices) { mutableListOf<ArcoCosto>() }

  fun agregarArcoCosto(arco: ArcoCosto) {
    adjList[arco.x].add(arco)
  }

  fun obtenerArcos(): List<ArcoCosto> {
    val arcos = mutableListOf<ArcoCosto>()
    for (i in 0 until numVertices) {
      for (arco in adjList[i]) {
        arcos.add(arco)
      }
    }
    return arcos
  }
}

class GrafoNoDirigidoCosto(val numDeVertices: Int) {
  private val adjList = Array(numDeVertices) { mutableListOf<ArcoCosto>() }

  fun addAristaCosto(arista: ArcoCosto) {
    adjList[arista.x].add(arista)
    adjList[arista.y].add(ArcoCosto(arista.y, arista.x, arista.costo)) // Para no dirigido, añadir en ambos sentidos
  }

  fun iterator(): Iterator<ArcoCosto> {
    val aristas = mutableListOf<ArcoCosto>()
    for (i in 0 until numDeVertices) {
      for (arista in adjList[i]) {
        aristas.add(arista)
      }
    }
    return aristas.iterator()
  }
}

// Función Kruskal (adaptada para recibir una lista de aristas)

fun kruskal(aristas: List<ArcoCosto>): List<ArcoCosto> {
  val numVertices = aristas.flatMap { listOf(it.x, it.y) }.distinct().size
  val aristasOrdenadas = aristas.sortedBy { it.costo }
  val conjuntos = DisjointSetUnion(numVertices)
  val mst = mutableListOf<ArcoCosto>()

  for (arista in aristasOrdenadas) {
    if (conjuntos.find(arista.x) != conjuntos.find(arista.y)) {
      mst.add(arista)
      conjuntos.union(arista.x, arista.y)
    }
  }

  return mst
}

class DisjointSetUnion(size: Int) {
  private val parent = IntArray(size) { it }

  fun find(i: Int): Int {
    if (parent[i] == i) return i
    return find(parent[i])
  }

  fun union(i: Int, j: Int) {
    val rootI = find(i)
    val rootJ = find(j)
    parent[rootI] = rootJ
  }
}

// Función para leer el archivo y crear el grafo (adaptada para GrafoNoDirigidoCosto)

fun readRoute(route: String): GrafoNoDirigidoCosto {
  val file = File(route)
  val lines = file.readLines()

  if (lines.isEmpty()) throw IllegalArgumentException("El archivo está vacío")

  val numParadas = lines[0].toInt()
  val numRutas = lines[1].toInt()

  if (numParadas != 2 && numParadas != 3) {
    throw Exception("El número de paradas no coincide")
  }

  val graphNoD = GrafoNoDirigidoCosto(numParadas)
  val Routes = mutableMapOf<String, Int>()

  // Leer información de las paradas y asignarles un índice
  var paradaIndex = 0
  for (i in 2 until 2 + numParadas) {
    val partes = lines[i].split(" ")
    val nombreParada = partes[0]
    Routes[nombreParada] = paradaIndex++
  }

  // Leer información de las rutas
  for (i in 2 + numParadas until lines.size) {
    val partes = lines[i].split(" ")
    val paradaInicio = partes[1] // Obtener el nombre de la parada de inicio
    val paradaFin = partes[2]   // Obtener el nombre de la parada de fin
    val distancia = partes[3].toDouble()

    val u = Routes[paradaInicio] ?: throw IllegalArgumentException("Parada no encontrada: $paradaInicio")
    val v = Routes[paradaFin] ?: throw IllegalArgumentException("Parada no encontrada: $paradaFin")

    graphNoD.addAristaCosto(ArcoCosto(u, v, distancia))
  }

  return graphNoD
}

fun main() {
  val graphNoD = readRoute("rutaOptUSB.txt") // Leer el grafo desde el archivo

  // Obtener las aristas del grafo
  val aristas = mutableListOf<ArcoCosto>()
  for (arista in graphNoD.iterator()) {
    aristas.add(arista)
  }

  val mst = kruskal(aristas) // Calcular el MST

  // Imprimir el MST
  for (edge in mst) {
    val stopStart = when (edge.x) {
      0 -> "USB"
      1 -> "McDonald’s_Trinidad"
      2 -> "Chacaíto"
      else -> "Desconocido"
    }
    val stopEnd = when (edge.y) {
      0 -> "USB"
      1 -> "McDonald’s_Trinidad"
      2 -> "Chacaíto"
      else -> "Desconocido"
    }
    println("$stopStart --(${edge.costo})-- $stopEnd")
  }
}