package nacho.llorente.domain.usecases

import nacho.llorente.data.repositorios.ClassRepository
import javax.inject.Inject
import nacho.llorente.domain.modelo.Class

class GetAllClassesUseCase @Inject constructor(
    private val classRepository: ClassRepository
) {
    operator fun invoke(): List<Class>{
        return classRepository.getClasses()
    }
}