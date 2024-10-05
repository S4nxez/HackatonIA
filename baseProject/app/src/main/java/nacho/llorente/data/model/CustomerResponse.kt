package nacho.llorente.data.model

import nacho.llorente.data.common.Constants
import nacho.llorente.domain.modelo.Customer
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class CustomerResponse(
    //ESTE ES EL OBJETO QUE RECIBES DEL API, EL SERIALIZED INDICA EL NOMBRE DEL CAMPO EN EL JSON
    @SerializedName("id")
    val id: Int,
    /*

    @SerializedName(Constants.NAME)
    val name: String,

    @SerializedName(Constants.SURNAME)
    val surname: String,

    @SerializedName(Constants.EMAIL)
    val email: String,

    @SerializedName(Constants.PHONE)
    val phone: Int,

    @SerializedName(Constants.BIRTHDATE)
    val birthdate: String
    */
)

//ESTA FUNCIÓN CONVIERTE EL OBJETO QUE RECIBES DEL API A UN OBJETO DE DOMINIO
/*
fun CustomerResponse.toCustomer(): Customer {
    return Customer(
        id = id,
        name = name,
        surname = surname,
        email = email,
        phone = phone,
        birthdate = LocalDate.parse(birthdate)
    )
}
*/
