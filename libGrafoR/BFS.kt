package libGrafoR



// Funcion BFS. (1)
fun bfs(graph: Map<Int, List<Int>>, start: Int): Set<Int> {
    val visited = mutableSetOf<Int>()
    val queue = ArrayDeque<Int>()
    
    println ("1: $queue")
    queue.add(start) // NODO de inicio. Agregando a la cola.

    while (queue.isNotEmpty()) { // Validacion mientras la cola no este vacia.
        val vertex = queue.removeFirst() //valor Nro de vertice removido de la cola.
        if (vertex !in visited) { // Validacion vertice NO esta la cola, lo agrega a la lista de RECORRIDOS. 
            visited.add(vertex)
            graph[vertex]?.let { neighbors -> queue.addAll(neighbors.filterNot { it in visited }) 
            }
        
        }
    }
    return visited // Retorno de NODOS recorridos.
}