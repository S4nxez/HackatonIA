package nacho.llorente.data.repositorios

import dagger.hilt.android.scopes.ActivityRetainedScoped
import nacho.llorente.data.model.GptRequest
import nacho.llorente.data.model.GptResponse
import nacho.llorente.data.model.Message
import nacho.llorente.data.sources.service.GptService
import javax.inject.Inject

@ActivityRetainedScoped
class GptRepository @Inject constructor(private val gptService: GptService) {

    suspend fun sendPrompt(prompt: String): GptResponse {
        val request = GptRequest(
            model = "gpt-4",
            messages = listOf(
                Message(
                    role = "system",
                    content = prompt
                )
            )
        )
        return gptService.getGptResponse(request)
    }
}