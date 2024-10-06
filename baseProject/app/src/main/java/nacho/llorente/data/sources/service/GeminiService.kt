package nacho.llorente.data.sources.service

interface GeminiService {
    suspend fun getGeminiResponse(request: String): String
}
