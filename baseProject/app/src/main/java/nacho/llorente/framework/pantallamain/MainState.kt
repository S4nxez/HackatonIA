package nacho.llorente.framework.pantallamain

import nacho.llorente.domain.modelo.Customer

data class MainState (
    val customers: List<Customer> = emptyList(),
    val customersSeleccionadas: List<Customer> = emptyList(),
    val selectMode: Boolean = false,
    val error: String? = null
)