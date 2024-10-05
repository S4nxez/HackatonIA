package nacho.llorente.domain.usecases

import nacho.llorente.data.repositorios.OrderRepository
import nacho.llorente.domain.modelo.Order
import javax.inject.Inject

class GetAllFilteredOrdersUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(customerid:Int) :List<Order>{
        return orderRepository.filterOrders(customerid)
    }
}