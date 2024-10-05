package nacho.llorente.domain.usecases

import nacho.llorente.data.repositorios.CustomerRepository
import nacho.llorente.domain.modelo.Customer
import nacho.llorente.utils.NetworkResult
import javax.inject.Inject

class DeleteCustomerUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
)   {
    suspend operator fun invoke(customer: Customer):NetworkResult<String> {
        return customerRepository.deleteCustomer(customer.id)
    }
}