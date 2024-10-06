package nacho.llorente.domain.modelo

data class Class (
    val id: Int,
    val name: String,
    var isSelected: Boolean,
    val description: String,
    val teacher: String,
    val image: String
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false
        other as Class
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id
    }
}