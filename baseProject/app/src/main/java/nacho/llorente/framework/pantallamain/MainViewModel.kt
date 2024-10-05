package nacho.llorente.framework.pantallamain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import nacho.llorente.domain.modelo.Customer
import nacho.llorente.domain.usecases.DeleteCustomerUseCase
import nacho.llorente.domain.usecases.GetAllCustomersUseCase
import nacho.llorente.framework.common.ConstantesFramework
import nacho.llorente.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllCustomersUseCase: GetAllCustomersUseCase,
    private val deleteCustomerUseCase: DeleteCustomerUseCase,
) : ViewModel() {
    private val listaCustomers = mutableListOf<Customer>()
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var selectedCustomers = mutableListOf<Customer>()
    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState

    init {
        _uiState.value = MainState(
            customers = emptyList(),
            customersSeleccionadas = emptyList(),
            selectMode = false,
            error = this.error.value
        )
        _error.value = ""
        getCustomers()
    }

    fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.SeleccionaCustomer -> seleccionaCustomer(event.customer)

            MainEvent.GetCustomers -> {
                getCustomers()
            }
            is MainEvent.GetCustomersFiltrados -> getCustomers(event.filtro)

            is MainEvent.DeleteCustomersSeleccionados -> {
                _uiState.value?.let {
                    deleteCustomer(it.customersSeleccionadas)
                    resetSelectMode()
                }
            }
            is MainEvent.DeleteCustomer -> {
                deleteCustomer(event.customer)
            }

            MainEvent.ResetSelectMode -> resetSelectMode()
            MainEvent.StartSelectMode -> _uiState.value =
                _uiState.value?.copy(selectMode = true)

        }
    }

    private fun seleccionaCustomer(customer: Customer) {
        //si ya está seleccionado, lo quitamos de la lista, si no lo metemos
        if (isSelected(customer)) {
            selectedCustomers.remove(customer)
        } else {
            selectedCustomers.add(customer)
        }
        _uiState.value = _uiState.value?.copy(customersSeleccionadas = selectedCustomers)
    }

    private fun getCustomers() {
        viewModelScope.launch {
            val result = getAllCustomersUseCase.invoke()
            when (result) {
                is NetworkResult.Error<*> -> _error.value = result.message ?: ConstantesFramework.ERRORRED
                is NetworkResult.Success<*> -> {
                    if (result.data is List<*>) {
                        listaCustomers.clear()
                        listaCustomers.addAll(result.data as Collection<Customer>)
                    }
                }
            }
            _uiState.value = _uiState.value?.copy(customers = listaCustomers)
        }
    }

    private fun getCustomers(filtro: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(
                customers = listaCustomers.filter { customer ->
                    customer.name.contains(filtro, ignoreCase = true)
                }.toList()
            )
        }
    }


    private fun deleteCustomer(customers: List<Customer>) {
        viewModelScope.launch {
            val copiaCustomers = customers.toList()

            val customersParaEliminar = mutableListOf<Customer>()

            var isSuccessful = true
            for (customer in copiaCustomers) {
                val result = deleteCustomerUseCase.invoke(customer)
                if (result is NetworkResult.Error<*>) {
                    _error.value = ConstantesFramework.ERRORBORRAR
                    isSuccessful = false
                    break
                } else {
                    customersParaEliminar.add(customer)
                }
            }

            if (isSuccessful) {
                listaCustomers.removeAll(customersParaEliminar)
                selectedCustomers.removeAll(customersParaEliminar)
                _uiState.value =
                    _uiState.value?.copy(customersSeleccionadas = selectedCustomers.toList())
            }

            getCustomers()
        }
    }


    private fun deleteCustomer(customer: Customer) {
        viewModelScope.launch {
            if (deleteCustomerUseCase.invoke(customer) is NetworkResult.Error<*>) {
                _error.value = ConstantesFramework.ERRORBORRAR
            } else {
                listaCustomers.remove(customer)
                selectedCustomers.remove(customer)
                _uiState.value =
                    _uiState.value?.copy(customersSeleccionadas = selectedCustomers.toList())
            }
        }
    }

    private fun resetSelectMode() {
        selectedCustomers.clear()
        _uiState.value =
            _uiState.value?.copy(selectMode = false, customersSeleccionadas = selectedCustomers)
    }

    private fun isSelected(customer: Customer): Boolean {
        return selectedCustomers.contains(customer)
    }
}