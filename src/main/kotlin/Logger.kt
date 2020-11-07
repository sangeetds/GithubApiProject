import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.output.TermUi.echo
import retrofit2.Response

/**
 * This functions logs checks the response details and log appropriate message to the user.
 */
fun logRepos(req: UserArguments, response: Response<List<Repo>>) {
    val repos = response.body()
    if (!response.isSuccessful || repos == null) {
        throw PrintMessage("Failed loading repos for ${req.organisation} with response: '${response.code()}: ${response.message()}'. Please check the name of org and try again.")
    } else {
        echo("${req.organisation}: loaded ${req.repoCount} repos")
    }
}

/**
 * This functions logs checks the response details and log appropriate message to the user.
 */
fun logUsers(repo: Repo, response: Response<List<User>>) {
    val users = response.body()
    if (response.code() == 202) {
        echo("Results for ${repo.name} has not been prepared and is being prepared by the server. Please run the program again after a few moments to get the result")
    } else if (!response.isSuccessful || users == null) {
        echo("Failed to load stats for ${repo.name} with response: '${response.code()}: ${response.message()}'")
    }
}
