package nacho.llorente.data.repositorios

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.runBlocking
import nacho.llorente.data.common.Constants
import nacho.llorente.data.model.Config
import nacho.llorente.data.model.DidFirstRequest
import nacho.llorente.data.model.DidFirstResponse
import nacho.llorente.data.model.Provider
import nacho.llorente.data.model.Root
import nacho.llorente.data.model.Script
import nacho.llorente.data.sources.service.CustomerService
import nacho.llorente.data.sources.service.DidService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit
import javax.inject.Inject
// other imports...
import com.google.ai.client.generativeai.GenerativeModel
@ActivityRetainedScoped
class DidRepository @Inject constructor(private val didService: DidService) {

    suspend fun sendText(geminiResponse:String):DidFirstResponse{
        val requestBodyData = DidFirstRequest(
            sourceUrl = "https://img.europapress.es/fotoweb/fotonoticia_20160527144948_690.jpg",
            script = Script(
                type = "text",
                subtitles = "false",
                provider = Provider(
                    type = "microsoft",
                    voiceId = "es-ES-SaulNeural"
                ),
                input = geminiResponse
            ),
            config = Config(
                fluent = "false",
                padAudio = "0.0"
            )
        )
        return didService.getDidIdFromResponse(requestBodyData)
    }

    suspend fun getDidVideo(didFirstResponse: DidFirstResponse): Root {
        return didService.getDidVideo(didFirstResponse.id)
    }



}

fun main() = runBlocking {
    // Configurar Retrofit
    // 1. Crear el cliente HTTP con el interceptor para la API Key
    val okHttpClient = OkHttpClient.Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .addInterceptor { chain: Interceptor.Chain ->
            val originalRequest: Request = chain.request()
            val newRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Basic aXRzbmFjaGV0dG9AZ21haWwuY29t:ZNDT8IZWoHMUsxz7wLEvQ")  // Sustituye con tu API Key
                .build()
            chain.proceed(newRequest)
        }
        .build()

    // 2. Crear el GsonConverterFactory
    val gsonConverterFactory = GsonConverterFactory.create()

    // 3. Crear Retrofit con el cliente HTTP y el convertidor
    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient) // Incluimos el cliente con la API Key
        .addConverterFactory(gsonConverterFactory)
        .build()

    // 4. Crear las instancias de los servicios
    val customerService = retrofit.create(DidService::class.java)


    // Crear instancia de DidService
    val didService = retrofit.create(DidService::class.java)

    // Crear instancia de DidRepository
    val didRepository = DidRepository(didService)

    // crear instancia de GeminiRepository
    val generativeModel =
        GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = "AIzaSyA1Z-sNJWczduJCpKOIB4DocBTfaN6HAUs"
        )
    val request = "la maravillosa apertura ponziani"
    val prompt = "Eres un profesor especializado en el tema " +
            "que se te va a plantear. Debes dar respuestas estructuradas, claras y completas. " +
            "Antes de responder, genera un índice de los principales puntos que vas a cubrir. Una " +
            "vez dado el índice, procede a desarrollar la primera parte del mismo, explicando paso " +
            "a paso y asegurándote de que la explicación sea comprensible. Mantén este estilo durante " +
            "toda la conversación. Responde siempre de manera profesional y pedagógica. el tema " +
            "es:"+request
    val response = generativeModel.generateContent(prompt)

    val geminiRespnse =  response.text.toString();

    // Enviar texto y obtener DidFirstResponse
    val didFirstResponse = didRepository.sendText(geminiRespnse)
    println("DidFirstResponse ID: ${didFirstResponse.id}")
    // Obtener video usando el id de DidFirstResponse
    sleep(60000)
    val root = didRepository.getDidVideo(didFirstResponse)

    // Imprimir la URL del video
    println("Video URL: ${root.resultUrl}")
}