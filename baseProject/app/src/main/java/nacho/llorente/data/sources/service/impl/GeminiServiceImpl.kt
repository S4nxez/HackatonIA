<<<<<<< Updated upstream
package nacho.llorente.data.sources.service.impl

import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import nacho.llorente.data.common.Constants
import nacho.llorente.data.sources.service.GeminiService
import javax.inject.Inject

class GeminiServiceImpl: GeminiService{
    override suspend fun getGeminiResponse(request: String): String {
        val generativeModel =
            GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = "AIzaSyA1Z-sNJWczduJCpKOIB4DocBTfaN6HAUs"
            )
        val prompt = "Eres un profesor especializado en el tema " +
                "que se te va a plantear. Debes dar respuestas estructuradas, claras y completas. " +
                "Antes de responder, genera un índice de los principales puntos que vas a cubrir. Una " +
                "vez dado el índice, procede a desarrollar la primera parte del mismo, explicando paso " +
                "a paso y asegurándote de que la explicación sea comprensible. Mantén este estilo durante " +
                "toda la conversación. Responde siempre de manera profesional y pedagógica. el tema " +
                "es:"+request
        val response = generativeModel.generateContent(prompt)
        return response.text.toString();
    }
=======
package nacho.llorente.data.sources.service.impl

import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import nacho.llorente.data.common.Constants
import nacho.llorente.data.sources.service.GeminiService
import javax.inject.Inject

class GeminiServiceImpl: GeminiService{
    override suspend fun getGeminiResponse(request: String): String {
        val generativeModel =
            GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = "AIzaSyA1Z-sNJWczduJCpKOIB4DocBTfaN6HAUs"
            )
        val prompt = "Eres un profesor especializado en el tema " +
                "que se te va a plantear. Debes dar respuestas estructuradas, claras y completas. " +
                "Antes de responder, genera un índice de los principales puntos que vas a cubrir. Una " +
                "vez dado el índice, procede a desarrollar la primera parte del mismo, explicando paso " +
                "a paso y asegurándote de que la explicación sea comprensible. Mantén este estilo durante " +
                "toda la conversación. Responde siempre de manera profesional y pedagógica. el tema " +
                "es:"+request
        val response = generativeModel.generateContent(prompt)
        return response.text.toString();
    }
>>>>>>> Stashed changes
}