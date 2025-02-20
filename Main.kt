import java.io.File

// Definición de las clases necesarias

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

// Función Kruskal

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

// Función para leer el archivo y crear el grafo

fun readRoute(route: String): GrafoNoDirigidoCosto {
  val file = File(route)
  val lines = file.readLines()

  if (lines.isEmpty()) throw IllegalArgumentException("El archivo está vacío")

  var currentLine = 0
  var graphNoD: GrafoNoDirigidoCosto? = null

  while (currentLine < lines.size) {
    val line = lines[currentLine]

    if (line.startsWith("#")) {
      currentLine++
      continue
    }

    // Saltar líneas en blanco o con solo espacios
    if (line.isBlank()) {
      currentLine++
      continue
    }

    val numRecorridosStr = lines[currentLine++]
    val numParadasStr = lines[currentLine++]

    val numRecorridos = numRecorridosStr.toIntOrNull()
    val numParadas = numParadasStr.toIntOrNull()

    if (numRecorridos == null) {
      println("Warning: Invalid number of recorridos: '$numRecorridosStr' en línea: ${currentLine - 2}. Se omite la ruta.")
      // Saltar el resto de la definición de esta ruta
      while (currentLine < lines.size && !lines[currentLine].startsWith("#")) {
        currentLine++
      }
      continue // Pasar a la siguiente ruta
    }

    if (numParadas == null) {
      println("Warning: Invalid number of paradas: '$numParadasStr' en línea: ${currentLine - 2}. Se omite la ruta.")
      // Saltar el resto de la definición de esta ruta
      while (currentLine < lines.size && !lines[currentLine].startsWith("#")) {
        currentLine++
      }
      continue // Pasar a la siguiente ruta
    }

    if (numParadas != 2 && numParadas != 3) {
      throw Exception("El número de paradas no coincide")
    }

    graphNoD = GrafoNoDirigidoCosto(numParadas)
    val Routes = mutableMapOf<String, Int>()

    // Leer información de las paradas
    for (i in currentLine until currentLine + numParadas) {
      val parts = lines[i].split(" ")
      if (parts.size >= 1) {
        val paradaName = parts[0]
        Routes[paradaName] = i - currentLine
      } else {
        println("Warning: Invalid parada line: ${lines[i]}")
      }
    }

    currentLine += numParadas

    // Leer información de las rutas
    for (i in currentLine until currentLine + numRecorridos) {
      val parts = lines[i].split(" ")
      if (parts.size >= 3) {
        val inicio = parts[0]
        val fin = parts[1]
        val distancia = parts[2].toDoubleOrNull()

        if (distancia != null) {
          val u = Routes[inicio] ?: throw IllegalArgumentException("Parada no encontrada: $inicio")
          val v = Routes[fin] ?: throw IllegalArgumentException("Parada no encontrada: $fin")
          graphNoD.addAristaCosto(ArcoCosto(u, v, distancia))
        } else {
          println("Warning: Invalid distance value: ${parts[2]} in line: ${lines[i]}")
        }
      } else {
        println("Warning: Invalid recorrido line: ${lines[i]}")
      }
    }

    currentLine += numRecorridos
  }

  return graphNoD ?: GrafoNoDirigidoCosto(0)
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