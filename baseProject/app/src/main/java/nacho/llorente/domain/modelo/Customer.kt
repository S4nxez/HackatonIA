package nacho.llorente.domain.modelo

import java.time.LocalDate
data class Customer(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val phone: Int,
    val birthdate: LocalDate,
    var isSelected: Boolean = false,
)
