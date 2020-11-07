import com.squareup.moshi.Json

/**
 * This class serves as the container where the information from JSON object regarding the repo is stored. It
 * saves id, name and forks_count of the repo of the organisation
 */
data class Repo(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "forks_count") val forks_count: Long
)