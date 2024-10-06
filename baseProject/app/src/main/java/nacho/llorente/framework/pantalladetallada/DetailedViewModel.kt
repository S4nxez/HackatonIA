package nacho.llorente.framework.pantalladetallada

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailedViewModel @Inject constructor(
    //aqui llamo para sacar el video o para sacar el posible promt de gemini
) : ViewModel() {
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    private val _uiState = MutableLiveData(DetailedState())
    val uiState: LiveData<DetailedState> get() = _uiState

    init {
        _uiState.value = DetailedState(
            //class = null,
            error = this.error.value
        )
    }

    fun handleEvent(event: DetailedEvent) {
        when (event) {
            //manejo de eventos
            else -> {
                _error.value = "Error"}
        }
    }
}