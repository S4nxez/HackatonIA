package nacho.llorente.data.model

import com.google.gson.annotations.SerializedName

data class GptRequest(
    @SerializedName("model")
    val model: String,
    @SerializedName("messages")
    val messages: List<Message>
)