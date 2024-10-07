package nacho.llorente.domain.usecases

import nacho.llorente.data.repositorios.ClassRepository
import javax.inject.Inject
import nacho.llorente.domain.modelo.Class

class DeleteClassUseCase @Inject constructor(
    private val classRepository: ClassRepository
) {
    operator fun invoke(clase: Class):Boolean{
        return classRepository.deleteClass(clase)
    }
}