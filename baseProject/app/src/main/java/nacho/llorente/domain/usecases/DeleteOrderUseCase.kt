package nacho.llorente.domain.usecases

import nacho.llorente.data.repositorios.OrderRepository
import nacho.llorente.domain.modelo.Order
import nacho.llorente.utils.NetworkResult
import javax.inject.Inject

class DeleteOrderUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend operator fun invoke(order: Order): NetworkResult<String> {
        return repository.deleteOrder(order.id)
    }
}