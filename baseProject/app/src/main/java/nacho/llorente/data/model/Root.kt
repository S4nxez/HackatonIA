package nacho.llorente.data.model

import com.google.gson.annotations.SerializedName

data class Root(
    @SerializedName("user") val user: User,
    @SerializedName("script") val script: Script2,
    @SerializedName("metadata") val metadata: Metadata,
    @SerializedName("audio_url") val audioUrl: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("face") val face: Face,
    @SerializedName("config") val config: Config2,
    @SerializedName("source_url") val sourceUrl: String,
    @SerializedName("created_by") val createdBy: String,
    @SerializedName("status") val status: String,
    @SerializedName("driver_url") val driverUrl: String,
    @SerializedName("modified_at") val modifiedAt: String,
    @SerializedName("user_id") val userId: String,
    @SerializedName("subtitles") val subtitles: Boolean,
    @SerializedName("id") val id: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("started_at") val startedAt: String,
    @SerializedName("result_url") val resultUrl: String
)

data class User(
    @SerializedName("features") val features: List<String?>,
    @SerializedName("stripe_plan_group") val stripePlanGroup: String,
    @SerializedName("authorizer") val authorizer: String,
    @SerializedName("owner_id") val ownerId: String,
    @SerializedName("id") val id: String,
    @SerializedName("plan") val plan: String,
    @SerializedName("email") val email: String
)

data class Script2(
    @SerializedName("length") val length: Int,
    @SerializedName("subtitles") val subtitles: Boolean,
    @SerializedName("type") val type: String,
    @SerializedName("provider") val provider: Provider
)



data class Metadata(
    @SerializedName("driver_url") val driverUrl: String,
    @SerializedName("mouth_open") val mouthOpen: Boolean,
    @SerializedName("num_faces") val numFaces: Int,
    @SerializedName("num_frames") val numFrames: Int,
    @SerializedName("processing_fps") val processingFps: Double,
    @SerializedName("resolution") val resolution: List<Int>,
    @SerializedName("size_kib") val sizeKib: Double
)

data class Face(
    @SerializedName("mask_confidence") val maskConfidence: Int,
    @SerializedName("detection") val detection: List<Int>,
    @SerializedName("overlap") val overlap: String,
    @SerializedName("size") val size: Int,
    @SerializedName("top_left") val topLeft: List<Int>,
    @SerializedName("face_id") val faceId: Int,
    @SerializedName("detect_confidence") val detectConfidence: Double
)

data class Config2(
    @SerializedName("stitch") val stitch: Boolean,
    @SerializedName("align_driver") val alignDriver: Boolean,
    @SerializedName("sharpen") val sharpen: Boolean,
    @SerializedName("normalization_factor") val normalizationFactor: Int,
    @SerializedName("result_format") val resultFormat: String,
    @SerializedName("fluent") val fluent: Boolean,
    @SerializedName("pad_audio") val padAudio: Double,
    @SerializedName("reduce_noise") val reduceNoise: Boolean,
    @SerializedName("auto_match") val autoMatch: Boolean,
    @SerializedName("show_watermark") val showWatermark: Boolean,
    @SerializedName("logo") val logo: Logo,
    @SerializedName("motion_factor") val motionFactor: Int,
    @SerializedName("align_expand_factor") val alignExpandFactor: Double
)

data class Logo(
    @SerializedName("url") val url: String,
    @SerializedName("position") val position: List<Int>
)
