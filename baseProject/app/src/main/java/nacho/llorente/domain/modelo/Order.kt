package nacho.llorente.domain.modelo

import nacho.llorente.data.model.OrderResponse
import java.time.LocalDate

data class Order (
    val id: Int,
    val customerId: Int,
    val orderDate: LocalDate,
    val tableId: Int,
) {
    fun toOrderResponse(): OrderResponse {
        return OrderResponse(
            customerId = customerId,
            orderDate = orderDate.toString(),
            orderId = id,
            tableId = tableId
        )
    }
}