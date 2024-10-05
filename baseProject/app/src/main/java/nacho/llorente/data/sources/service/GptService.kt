package nacho.llorente.data.sources.service

import nacho.llorente.data.model.GptRequest
import nacho.llorente.data.model.GptResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GptService {
    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    suspend fun getGptResponse(@Body request: GptRequest): GptResponse

}