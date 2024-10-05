package nacho.llorente.data.repositorios
import nacho.llorente.data.common.Constants
import nacho.llorente.data.sources.service.CustomerService
import nacho.llorente.domain.modelo.Customer
import nacho.llorente.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
@ActivityRetainedScoped
class CustomerRepository @Inject constructor(private val customerService: CustomerService) {
    //ESTA FUNCIÓN DEVUELVE UNA LISTA DE CUSTOMERS DEL DOMINIO
    /*
    suspend fun getCustomers(): NetworkResult<List<Customer>> {
        var l: List<Customer> = emptyList()
        customerService.getCustomers().body()?.let {
            l = it.map { customerResponse ->
                customerResponse.toCustomer()
            }
        }
        return NetworkResult.Success(l)
    }
    */

/*

    suspend fun deleteCustomer(id: Int): NetworkResult<String> {
        return try {
            //ESTA FUNCIÓN DEVUELVE UN STRING CON EL RESULTADO DE LA OPERACIÓN
            val response = customerService.deleteCustomer(id)
            if (response.isSuccessful) {
                val responseBodyString = response.body()?.string() ?: Constants.DELETEOK
                // CAMBIA EL CUERPO A LA CONSTANTE DELETEOK SI EL RESPONSE ES EXITOSO
                NetworkResult.Success(responseBodyString)
            } else {
                val errorBodyString = response.errorBody()?.string() ?: Constants.UNKNOWNERROR
                NetworkResult.Error(errorBodyString)
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: Constants.UNKNOWNERROR)
        }
    }
*/
/*

    suspend fun getCustomerFromId(customerId: Int): NetworkResult<Customer> {
        return try {
            val response = customerService.getCustomer(customerId)
            if (response.isSuccessful) {
                response.body()?.let { customerResponse ->
                    NetworkResult.Success(customerResponse.toCustomer())
                } ?: NetworkResult.Error(Constants.CUSTOMERNOTFOUND)
            } else {
                val errorBodyString = response.errorBody()?.string() ?: Constants.UNKNOWNERROR
                NetworkResult.Error(errorBodyString)
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: Constants.UNKNOWNERROR)
        }
    }

*/

}