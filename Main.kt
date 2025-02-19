import java.util.Arrays
import java.io.File
import libGrafoR

val Routes = mutableMapOf<String, Int>(
    "USB" to 0,
    "McDonald’s_Trinidad" to 1,
    "Chacaíto" to 2
)

fun readRoute(route: String): GrafoNoDirigidoCosto {