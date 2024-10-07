package nacho.llorente.data.repositorios
import nacho.llorente.data.sources.service.DidService
import nacho.llorente.domain.modelo.Class
import javax.inject.Inject

class ClassRepository @Inject constructor(private val didService: DidService) {
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
    val listaEstatica: MutableList<Class> = mutableListOf(
        Class(1, "Clase 1", true, "Descripcion de la clase 1","teacherDescription", "https://i.imgur.com/uP72MZs.png"),
        Class(2, "Clase 2", true,"Descripcion de la clase 2", "teacherDescription","https://i.imgur.com/uP72MZs.png")
    )

    fun getClasses(): List<Class> {
        return listaEstatica
    }

    // Método para eliminar una clase de la lista
    fun deleteClass(clase: Class): Boolean {
        return if (listaEstatica.contains(clase)) {
            listaEstatica.remove(clase)
            true // Retorna true si se elimina exitosamente
        } else {
            false // Retorna false si la clase no existe
        }
    }
}
