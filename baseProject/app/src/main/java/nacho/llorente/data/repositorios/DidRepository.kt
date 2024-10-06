package nacho.llorente.data.repositorios

import dagger.hilt.android.scopes.ActivityRetainedScoped
import nacho.llorente.data.model.Config
import nacho.llorente.data.model.DidFirstRequest
import nacho.llorente.data.model.DidFirstResponse
import nacho.llorente.data.model.Provider
import nacho.llorente.data.model.Root
import nacho.llorente.data.model.Script
import nacho.llorente.data.sources.service.DidService
import javax.inject.Inject

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