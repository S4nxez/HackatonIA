package nacho.llorente.domain.usecases

import nacho.llorente.data.repositorios.CustomerRepository
import nacho.llorente.domain.modelo.Customer
import java.time.LocalDate
import javax.inject.Inject

class GetCustomerFromIdUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(customerid: Int): Customer {
        return customerRepository.getCustomerFromId(customerid).data?:
            Customer(0,"","","",0,LocalDate.of(0,0,0),false)
    }
}