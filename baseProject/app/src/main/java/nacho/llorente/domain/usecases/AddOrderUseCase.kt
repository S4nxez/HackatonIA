package nacho.llorente.domain.usecases

import nacho.llorente.data.repositorios.OrderRepository
import nacho.llorente.domain.modelo.Order
import nacho.llorente.utils.NetworkResult
import javax.inject.Inject

class AddOrderUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend operator fun invoke(order: Order): NetworkResult<Order> {
        return repository.createOrder(order)
    }
}