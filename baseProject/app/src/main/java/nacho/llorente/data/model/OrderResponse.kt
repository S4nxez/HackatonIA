package nacho.llorente.data.model

import nacho.llorente.data.common.Constants
import nacho.llorente.domain.modelo.Order
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class OrderResponse(
    //ESTE ES EL OBJETO QUE RECIBES DEL API, EL SERIALIZED INDICA EL NOMBRE DEL CAMPO EN EL JSON
    @SerializedName("id")
    val customerId: Int,
)
//ESTA FUNCIÓN CONVIERTE EL OBJETO QUE RECIBES DEL API A UN OBJETO DE DOMINIO
/*
fun OrderResponse.toOrder(): Order {
    return Order(
        customerId = customerId,
        orderDate = LocalDate.parse(orderDate),
        id = orderId,
        tableId = tableId
    )
}
*/