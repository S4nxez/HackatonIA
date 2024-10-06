package nacho.llorente.data.repositorios

import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import nacho.llorente.data.common.Constants

class GeminiRepository {

    suspend fun getGeminiResponse(request: String): String {
        val generativeModel =
            GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = Constants.geminiApiKey)
        val prompt = request
        val response = generativeModel.generateContent(prompt)
        return response.text.toString();
    }
}
