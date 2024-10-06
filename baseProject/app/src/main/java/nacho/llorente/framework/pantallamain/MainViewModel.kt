package nacho.llorente.framework.pantallamain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import nacho.llorente.domain.modelo.Class
import nacho.llorente.framework.common.ConstantesFramework
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nacho.llorente.domain.usecases.CreateClassFromInputUseCase
import nacho.llorente.domain.usecases.DeleteClassUseCase
import nacho.llorente.domain.usecases.GetAllClassesUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllClassesUseCase: GetAllClassesUseCase,
    private val deleteClassUseCase: DeleteClassUseCase,
    private val createClassFromInputUseCase: CreateClassFromInputUseCase,
) : ViewModel() {
    private val listaClasses = mutableListOf<Class>()
    //private val listaClasses = mutableListOf<Class>()
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var selectedClasses = mutableListOf<Class>()
    //private var selectedClasses = mutableListOf<Class>()
    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState

    init {
        _uiState.value = MainState(
            classes = emptyList(),
            classesSeleccionadas = emptyList(),
            selectMode = false,
            error = this.error.value
        )
        _error.value = ""
        getClasses()
    }

    fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.SeleccionaClass -> seleccionaClass(event.clase)

            MainEvent.GetClasses -> {
                getClasses()
            }

            is MainEvent.GetClassesFiltradas -> getClasses(event.filtro)

            is MainEvent.DeleteClassesSeleccionadas -> {
                _uiState.value?.let {
                    deleteClass(it.classesSeleccionadas)
                    resetSelectMode()
                }
            }

            is MainEvent.DeleteClass -> {
                deleteClass(event.clase)
            }

            MainEvent.ResetSelectMode -> resetSelectMode()

            MainEvent.StartSelectMode -> _uiState.value =
                _uiState.value?.copy(selectMode = true)
        }
    }

    private fun seleccionaClass(clase: Class) {
        //si ya está seleccionado, lo quitamos de la lista, si no lo metemos
        if (isSelected(clase)) {
            selectedClasses.remove(clase)
        } else {
            selectedClasses.add(clase)
        }
        _uiState.value = _uiState.value?.copy(classesSeleccionadas = selectedClasses)
    }

    private fun getClasses() {
        viewModelScope.launch {
            val result = getAllClassesUseCase.invoke()
            when (result) {
                is List<*> -> {
                    listaClasses.clear()
                    listaClasses.addAll(result as Collection<Class>)
                }
            }
            _uiState.value = _uiState.value?.copy(classes = listaClasses)
        }
    }

    private fun getClasses(filtro: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(
                classes = listaClasses.filter { Class ->
                    Class.name.contains(filtro, ignoreCase = true)
                }.toList()
            )
        }
    }

    //NO ES NETWORKRESULT, ES UNA LISTA ESTATICA, NO HAY OBJETO DE NETWORKRESULT
    private fun deleteClass(classes: List<Class>) {
        viewModelScope.launch {
            val copiaClasses = classes.toList()

            val classesParaEliminar = mutableListOf<Class>()
            var isSuccessful = true

            for (clase in copiaClasses) {
                val wasDeleted = deleteClassUseCase.invoke(clase) // Devuelve Boolean
                if (!wasDeleted) {
                    _error.value = ConstantesFramework.ERRORBORRAR
                    isSuccessful = false
                    break
                } else {
                    classesParaEliminar.add(clase)
                }
            }

            if (isSuccessful) {
                listaClasses.removeAll(classesParaEliminar)
                selectedClasses.removeAll(classesParaEliminar)
                _uiState.value = _uiState.value?.copy(classesSeleccionadas = selectedClasses.toList())
            }

            getClasses()
        }
    }


    private fun deleteClass(clase: Class) {
        viewModelScope.launch {
            val wasDeleted = deleteClassUseCase.invoke(clase) // Devuelve un Boolean

            if (wasDeleted) {
                // Si la clase fue eliminada exitosamente
                listaClasses.remove(clase)
                selectedClasses.remove(clase)
                _uiState.value = _uiState.value?.copy(classesSeleccionadas = selectedClasses.toList())
            } else {
                // Si hubo un error en la eliminación
                _error.value = ConstantesFramework.ERRORBORRAR
            }
        }
    }


    private fun resetSelectMode() {
        //selectedClasses.clear()
        selectedClasses.clear()
        _uiState.value =
            _uiState.value?.copy(selectMode = false,
                classesSeleccionadas = selectedClasses)
    }

    private fun isSelected(Class: Class): Boolean {
        return selectedClasses.contains(Class)
    }

    /*
    private fun isSelected(class: Class): Boolean {
        return selectedClasses.contains(class)
    }
    */
}