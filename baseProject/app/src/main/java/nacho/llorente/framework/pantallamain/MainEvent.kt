package nacho.llorente.framework.pantallamain
import nacho.llorente.domain.modelo.Customer

sealed class MainEvent {
    class DeleteCustomersSeleccionados() : MainEvent()
    class DeleteCustomer(val customer:Customer) : MainEvent()
    class SeleccionaCustomer(val customer: Customer) : MainEvent()
    class GetCustomersFiltrados(val filtro: String) : MainEvent()
    object GetCustomers : MainEvent()
    object StartSelectMode: MainEvent()
    object ResetSelectMode: MainEvent()
}
