package nacho.llorente.data.sources.service.impl

import nacho.llorente.data.repositorios.GeminiRepository
import nacho.llorente.data.sources.service.GeminiService
import javax.inject.Inject

class GeminiServiceImpl @Inject constructor(private val geminiRepository: GeminiRepository): GeminiService{
    override suspend fun getGeminiResponse(request: String): String {
        return geminiRepository.getGeminiResponse("Eres un profesor especializado en el tema " +
                "que se te va a plantear. Debes dar respuestas estructuradas, claras y completas. " +
                "Antes de responder, genera un índice de los principales puntos que vas a cubrir. Una " +
                "vez dado el índice, procede a desarrollar la primera parte del mismo, explicando paso " +
                "a paso y asegurándote de que la explicación sea comprensible. Mantén este estilo durante " +
                "toda la conversación. Responde siempre de manera profesional y pedagógica. el tema " +
                "es:"+request)
    }
}