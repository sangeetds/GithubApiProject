import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.int


/**
 * The driver function which prompts for three arguments, namely the repository name, the number of repositories
 * to be generated and the top committees for each repository. Login id and authentication are taken as command line arguments.
 * It generates repositories for the given organisation and the top committees for each repositories
 */
class Hello : CliktCommand(
    help = "A Command line tool to show top committees of a particular github repo."
) {
    /**
     * Rate limit cap according to the Github API for an unauthorized request.
     */
    private val rateLimit = 60

    private val organisationName: String by option(help = "Name of the organisation").prompt("Enter the name of Organisation")
    private val repositoriesCount: Int by option(help = "Number of repositories to be selected").int()
        .prompt("Enter number of repositories to be selected")
    private val committees: Int by option(help = "Number of top committees to be displayed").int()
        .prompt("Enter the number of committees to be displayed")

    /**
     * Login and token credentials for the autherization for GithubAPI to increase rate limit. If the login and credentials don't match
     * or are incorrect, then the rate is kept at 60 and no erros are produced.
     */
    private val login by option(help = "User login id").default("")
    private val token by option(help = "Authorization token").default("")

    override fun run() {
        if (repositoriesCount + 1 >= rateLimit && login.isBlank() && token.isBlank()) {
            echo("As authorization credentials have not been given, results will be limited to API rate limiter")
        }

        echo(
            "You chose to view $committees top committees of $repositoriesCount popular repositories from ${
                organisationName.trim().capitalize()
            }:"
        )

        val newUserArguments = UserArguments(
            organisation = organisationName.trim(),
            repoCount = repositoriesCount,
            topCommittees = committees,
            token = if (token.isBlank()) "" else token.trim(),
            login = if (login.isBlank()) "" else login.trim()
        )
        val githubAPI = Github(newUserArguments)
        val repoList = githubAPI.getRepos()
        val committeesList = HashMap<String, List<User>>()

        echo("These are top $repositoriesCount popular repositories for ${organisationName.trim().capitalize()}:")
        repoList?.forEach { repo ->
            echo("Name: ${repo.name} \t Fork Count: ${repo.forks_count}")
        }

        echo("\nRepository wise top $committees committees:")
        repoList?.forEach { repo ->
            echo("Top commits for ${repo.name}:")
            committeesList[repo.name] = githubAPI.getTopUsers(repo)!!
            committeesList[repo.name]?.forEach { user ->
                echo("Name: ${user.author.login} \t Commits: ${user.total}")
            }
        }
    }
}

/**
 * Main function from where the CLI launched.
 */
fun main(args: Array<String>) = Hello().main(args)