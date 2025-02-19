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
