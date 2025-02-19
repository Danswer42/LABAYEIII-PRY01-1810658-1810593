package libGrafoR
import java.util.*

class prim(private val vertices: Int) {
    private val adjacencyMatrix = Array(vertices) { IntArray(vertices) }

    fun addEdge(u: Int, v: Int, costo: Int) {
        adjacencyMatrix[u][v] = costo
        adjacencyMatrix[v][u] = costo
    }

    fun primMST() {
        val key = IntArray(vertices) { Int.MAX_VALUE }
        val parent = IntArray(vertices) { -1 }
        val visited = BooleanArray(vertices) { false }

        key[0] = 0
        val pq: PriorityQueue<AristaCosto> = PriorityQueue(vertices)

        pq.offer(AristaCosto(0, 0, adjacencyMatrix[0][0].toDouble() ))

        while (!pq.isEmpty()) {
            val u = pq.poll().y
            visited[u] = true

            for (v in 0 until vertices) {
                if (adjacencyMatrix[u][v] != 0 && !visited[v] && adjacencyMatrix[u][v] < key[v]) {
                    parent[v] = u
                    key[v] = adjacencyMatrix[u][v]
                    pq.offer(AristaCosto(u, v, key[v].toDouble()))
                }
            }
        }

        println("Edges in MST:")
        for (i in 1 until vertices) {
            println(parent[i].toString() + " - " + i.toString() + "  (" + adjacencyMatrix[i][parent[i]].toString() + ")")
        }
    }
}