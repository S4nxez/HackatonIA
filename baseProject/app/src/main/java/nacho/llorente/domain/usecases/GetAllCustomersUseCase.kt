package nacho.llorente.domain.usecases

import nacho.llorente.data.repositorios.CustomerRepository
import nacho.llorente.domain.modelo.Customer
import nacho.llorente.utils.NetworkResult
import javax.inject.Inject

class GetAllCustomersUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(): NetworkResult<List<Customer>> {
        return customerRepository.getCustomers()
    }
}