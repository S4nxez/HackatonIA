package nacho.llorente.framework.pantalladetallada

import nacho.llorente.domain.modelo.Customer
import nacho.llorente.domain.modelo.Order

data class DetailedState (
    val orders: List<Order> = emptyList(),
    val customer: Customer? = null,
    val error: String? = null
)