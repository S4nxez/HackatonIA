package nacho.llorente.framework.pantalladetallada

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nacho.llorente.domain.usecases.AddOrderUseCase
import nacho.llorente.domain.usecases.GetAllFilteredOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import nacho.llorente.domain.modelo.Order
import nacho.llorente.domain.usecases.DeleteOrderUseCase
import nacho.llorente.domain.usecases.GetCustomerFromIdUseCase
import nacho.llorente.utils.NetworkResult
import kotlinx.coroutines.launch
import java.time.LocalDate


@HiltViewModel
class DetailedViewModel @Inject constructor(
    private val getAllFilteredOrdersUseCase: GetAllFilteredOrdersUseCase,
    private val deleteOrderUseCase: DeleteOrderUseCase,
    private val addorderusecase: AddOrderUseCase,
    private val getCustomerFromIdUseCase: GetCustomerFromIdUseCase
) : ViewModel() {
    private val listaOrders = mutableListOf<Order>()
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    private val _uiState = MutableLiveData(DetailedState())
    val uiState: LiveData<DetailedState> get() = _uiState

    init {
        _uiState.value = DetailedState(
            orders = emptyList(),
            error = this.error.value
        )
    }

    fun handleEvent(event: DetailedEvent) {
        when (event) {
            is DetailedEvent.GetOrders -> {
                getOrders(event.id)
            }

            is DetailedEvent.DeleteOrder -> {
                deleteOrder(event.pedido)
            }

            is DetailedEvent.GetCustomer -> getCustomer(event.id)
            is DetailedEvent.AddOrder -> addOrder(event.tableid)
        }
    }

    private fun addOrder(tableid: Int) {
        viewModelScope.launch {
            val order = Order(0, _uiState.value?.customer?.id?: 0, LocalDate.now(), tableid)

            if (addorderusecase.invoke(order) is NetworkResult.Error<*> || order.customerId == 0) {
                _error.value = "Error al añadir"
            } else {
                listaOrders.add(order)
                _uiState.value = _uiState.value?.copy(orders = listaOrders)
            }
        }
    }

    private fun getCustomer(id: Int) {
        viewModelScope.launch {
            val result = getCustomerFromIdUseCase.invoke(id)
            _uiState.value= _uiState.value?.copy(customer = result)
        }
    }

    private fun getOrders(id: Int) {
        viewModelScope.launch {
            val result = getAllFilteredOrdersUseCase.invoke(id)
            listaOrders.clear()
            listaOrders.addAll(result)
            _uiState.value = _uiState.value?.copy(orders = listaOrders)
        }
    }

    private fun deleteOrder(order: Order) {
        viewModelScope.launch {

            if (deleteOrderUseCase.invoke(order) is NetworkResult.Error<*>) {
                _error.value = "Error al borrar"
            } else {
                listaOrders.remove(order)
                _uiState.value =
                    _uiState.value?.copy(orders = listaOrders)
            }
        }
    }
}