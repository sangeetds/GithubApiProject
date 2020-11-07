import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface for making API requests. The annotations help in converting the functions into appropriate API
 * request.
 */
interface GithubService {

    /**
     * To get details about the repositories of the organisation given. It takes organisation name as the param
     * and returns and Callable object which can be converted into response.
     */
    @GET("orgs/{org}/repos?per_page=100")
    fun getOrgReposCall(
        @Path("org") org: String,
    ): Call<List<Repo>>

    /**
     * To get details about the committees repositories of the organisation given. It takes organisation name
     * and repo name as the parameters returns and Callable object which can be converted into response.
     */
    @GET("repos/{owner}/{repo}/stats/contributors?per_page=100")
    fun getRepoContributorsCall(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<List<User>>
}

/**
 * Function which makes the actual call gets the response's body to give back the repo details.
 */
fun loadRepos(service: GithubService, req: UserArguments) = service.getOrgReposCall(req.organisation)
    .execute()
    .also { logRepos(req = req, response = it) }

/**
 * Function which makes the actual call gets the response's body to give back the committees detail.
 */
fun Repo.loadContributors(service: GithubService, req: UserArguments) =
    service
        .getRepoContributorsCall(req.organisation, this.name)
        .execute()
        .also { logUsers(repo = this, response = it) }






