package nacho.llorente.framework.pantallamain
import nacho.llorente.domain.modelo.Class

sealed class MainEvent {
    class DeleteClassesSeleccionadas() : MainEvent()
    class DeleteClass(val clase:Class) : MainEvent()
    class SeleccionaClass(val clase: Class) : MainEvent()
    class GetClassesFiltradas(val filtro: String) : MainEvent()
    object GetClasses : MainEvent()

    object StartSelectMode: MainEvent()
    object ResetSelectMode: MainEvent()

}
