package libGrafoR



fun kruskal(graph: List<AristaCosto>, numVertices: Int): List<AristaCosto> {
    val sortedEdges = graph.sortedBy { it.costo }
    val disjointSet = IntArray(numVertices) { -1 }
    val mst = mutableListOf<AristaCosto>()

    fun find(parents: IntArray, i: Int): Int {
        if (parents[i] < 0) return i
        return find(parents, parents[i]).also { parents[i] = it }
    }

    fun union(parents: IntArray, i: Int, j: Int) {
        val root1 = find(parents, i)
        val root2 = find(parents, j)
        if (root1 != root2) {
            if (parents[root1] < parents[root2]) {
                parents[root1] += parents[root2]
                parents[root2] = root1
            } else {
                parents[root2] += parents[root1]
                parents[root1] = root2
            }
        }
    }

    for (edge in sortedEdges) {
        if (mst.size >= numVertices - 1) break
        if (find(disjointSet, edge.x) != find(disjointSet, edge.y)) {
            union(disjointSet, edge.x, edge.y)
            mst.add(edge)
        }
    }

    return mst
}
