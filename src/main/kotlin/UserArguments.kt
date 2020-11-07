/**
 * This class serves as an container to store the arguments provided to the CLI
 */
data class UserArguments(
    val organisation: String,
    val repoCount: Int,
    val topCommittees: Int,
    val login: String,
    val token: String
)

