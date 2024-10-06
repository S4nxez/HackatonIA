package nacho.llorente.data.model

import com.google.gson.annotations.SerializedName

data class DidFirstRequest(
    @SerializedName("source_url") val sourceUrl: String,
    @SerializedName("script") val script: Script,
    @SerializedName("config") val config: Config
)

data class Script(
    @SerializedName("type") val type: String,
    @SerializedName("subtitles") val subtitles: String,
    @SerializedName("provider") val provider: Provider,
    @SerializedName("input") val input: String
)

data class Provider(
    @SerializedName("type") val type: String,
    @SerializedName("voice_id") val voiceId: String
)

data class Config(
    @SerializedName("fluent") val fluent: String,
    @SerializedName("pad_audio") val padAudio: String
)

