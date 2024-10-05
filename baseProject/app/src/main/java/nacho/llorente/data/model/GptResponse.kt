package nacho.llorente.data.model

import nacho.llorente.domain.modelo.Message

import com.google.gson.annotations.SerializedName

data class GptResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("object")
    val objectType: String,
    @SerializedName("created")
    val created: Long,
    @SerializedName("model")
    val model: String,
    @SerializedName("choices")
    val choices: List<Choice>,
    @SerializedName("usage")
    val usage: Usage
)

data class Choice(
    @SerializedName("message")
    val message: Message,
    @SerializedName("finish_reason")
    val finishReason: String,
    @SerializedName("index")
    val index: Int
)

data class Message(
    @SerializedName("role")
    val role: String,
    @SerializedName("content")
    val content: String
)

data class Usage(
    @SerializedName("prompt_tokens")
    val promptTokens: Int,
    @SerializedName("completion_tokens")
    val completionTokens: Int,
    @SerializedName("total_tokens")
    val totalTokens: Int
)


fun GptResponse.toGpt(): Message {
    val messageContent = this.choices.firstOrNull()?.message?.content ?: ""
    val usage = this.usage
    return Message(
        content = messageContent,
    )
}
