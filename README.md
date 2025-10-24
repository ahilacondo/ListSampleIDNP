Autores: 
* Andre Hilacondo Begazo
* Jorge Escobedo Ocaña

## SOLUCION

La solución fue re-implementar el manejo de la lista para usar las herramientas de estado nativas de Jetpack Compose.

Un primer cambio fue, Adopción de **SnapshotStateList (mutableStateListOf())**:

Se reemplazó la lista estándar por remember { mutableStateListOf<Curso>() }.

**Qué es:** Esto crea una SnapshotStateList, una lista especial que está integrada con el sistema de estado de Compose.

**Cómo funciona:** Esta lista notifica a Compose cada vez que sus elementos son añadidos, eliminados o (crucialmente) reemplazados.

* ANTES (No Observable)

var cursos = mutableListOf<Curso>()

* DESPUÉS (Observable)

val cursos = remember { mutableStateListOf<Curso>() }

Entonces para asegurar que la SnapshotStateList detecte el cambio, dejamos de mutar el objeto. Ahora, creamos una copia (.copy()) del objeto con los datos nuevos y reemplazamos el objeto antiguo en la lista.

* ANTES (Mutación directa)

curso.nombre = "NuevoNombre"

* DESPUÉS (Reemplazo con copia)

val cursoModificado = curso.copy(nombre = "NuevoNombre")

cursos[index] = cursoModificado


## 2. Conclusión

El principio fundamental de Compose es UI = f(state) (La UI es una función del estado).

Al usar mutableStateListOf, hacemos que la lista cursos sea un estado observable.

Al usar .copy() y el reemplazo (cursos[index] = ...), disparamos el notificador de la SnapshotStateList.

Esta notificación le dice a Compose que el estado ha cambiado, lo que dispara la recomposicion

El LazyColumn, que observa cursos, se recompone y redibuja solo el ítem modificado, reflejando el cambio al instante.