import com.squareup.moshi.Json

/**
 * This class serves as the container where the information from JSON object regarding the repo is stored. It
 * saves total commits and [author] details of the committees of the repo of the organisation
 */
data class User(
    @field:Json(name = "total") val total: Int,
    @field:Json(name = "author") val author: Author
)

/**
 * This class serves as the container where the information from JSON object regarding the repo is stored. It
 * saves id and login of the author of the repo of the organisation
 */
data class Author(
    @field:Json(name = "login") val login: String,
    @field:Json(name = "id") val id: Int,
)