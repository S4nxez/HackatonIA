package nacho.llorente.framework.pantalladetallada

import nacho.llorente.domain.modelo.Order

sealed class DetailedEvent {
    class DeleteOrder(val pedido: Order) : DetailedEvent()
    class GetOrders(val id :Int) : DetailedEvent()
    class GetCustomer(val id :Int) : DetailedEvent()
    class AddOrder(val tableid: Int) : DetailedEvent()
}