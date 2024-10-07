package nacho.llorente.framework.pantallamain

import nacho.llorente.domain.modelo.Class

data class MainState (
    val classes: List<Class> = emptyList(),
    val classesSeleccionadas: List<Class> = emptyList(),
    val selectMode: Boolean = false,
    val error: String? = null
)