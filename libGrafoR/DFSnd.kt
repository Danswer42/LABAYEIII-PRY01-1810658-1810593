package libGrafoR

 

// DFS
class DFSnd(private val vertices: Int) {
    private val adjacencyList = mutableListOf<MutableList<Int>>()

    init {
        for (i in 0 until vertices) {
            adjacencyList.add(mutableListOf())
        }
    }
    fun addEdge(u: Int, v: Int) {
        adjacencyList[u].add(v)
        adjacencyList[v].add(u)
    }

    fun dfs(start: Int, visited: BooleanArray) {
        visited[start] = true
        print("$start ")

        for (v in adjacencyList[start]) {
            if (!visited[v]) {
                dfs(v, visited)
            }
        }
    }
}