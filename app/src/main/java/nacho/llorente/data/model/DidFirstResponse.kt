package nacho.llorente.data.model

import com.google.gson.annotations.SerializedName

data class DidFirstResponse(
    @SerializedName("id") val id: String,
    @SerializedName("object") val objectType: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("created_by") val createdBy: String,
    @SerializedName("status") val status: String
)
