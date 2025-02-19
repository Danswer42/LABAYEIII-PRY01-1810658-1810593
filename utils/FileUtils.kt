import java.io.File

//Utilidades para leer archivos de entrada
object FileUtils {
  //Lee el archivo de entrada y construye el grafo
  fun readInputfile(filename: String): Graph {
    val graph = Graph()
    val fullPath = "resourses/$filename"
    val lines = File(fullPath).readlines

    lines.forEach { line -> 
      val parts = line.split("")
      when (parts.size){
        2 -> { 
          val StopName = parts[0]
          graph.addStop(Stop(StopName))
        }
        4 -> {
          val origin = Stop(parts[1])
          val destination = Stop(parts[2])
          val distance = parts[3].toDouble()
          val fuel = parts[4].toDouble()
          graph.addRoute(Route(origin, destination, distance, fuel))
        }
      }
    }
    return graph
  }
}