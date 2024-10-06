package nacho.llorente.data.sources.service

import nacho.llorente.data.model.DidFirstRequest
import nacho.llorente.data.model.DidFirstResponse
import nacho.llorente.data.model.Root
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DidService {
    @POST("talks")
    suspend fun getDidIdFromResponse(@Body didfirstRequest:DidFirstRequest) : DidFirstResponse

    @GET("talks/{id}")
    suspend fun getDidVideo(@Path("id") id: String): Root
}