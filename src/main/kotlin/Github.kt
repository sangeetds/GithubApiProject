import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Base64.getEncoder

/**
 * [Github] is  the core of this project. It makes request to the Github API with the help of [okhttp3] and [Retrofit]
 * frameworks. JSON objects are converted into Kotlin classes with the help of [Moshi] converter. It helps in making
 * requests to the API and loads all the repos first and gets committees for each of them.
 *
 * @param input User arguments to load repositories and committees.
 */
class Github(private val input: UserArguments) {
    private val username = input.login
    private val password = input.token

    /**
     * Converts the user credentials to authorizable token if login details are given else it remains null
     */
    private val authToken = if (username.isNotBlank() && password.isNotBlank())
        "Basic " + getEncoder().encode("$username:$password".toByteArray()).toString(Charsets.UTF_8)
    else
        null

    /**
     * httpClient which helps in making http requests.
     */
    private val httpClient = if (authToken != null) OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()
                .header("Accept", "application/vnd.github.v3+json")
                .header("Authorization", authToken)
            val request = builder.build()
            chain.proceed(request)
        }
        .build()
    else
        OkHttpClient.Builder().build()

    /**
     * Moshi helps in converting JSON objects into Java Classes and parameters.
     */
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(httpClient)
        .build()

    private var service: GithubService = retrofit.create(GithubService::class.java)

    /**
     * Makes a request through [GithubService] and returns a list of all the repos of the organisation.
     */
    fun getRepos(): List<Repo>? {
        val popularRepos = mutableListOf<Repo>()
        var hasMorePage = input.repoCount > 0

        while (hasMorePage) {
            val currRequest = loadRepos(service, input)
            popularRepos.addAll(currRequest.body()!!)
            hasMorePage =
                popularRepos.size < input.repoCount && ("rel=\"next\"" in currRequest.headers()["Link"].orEmpty())
        }

        return popularRepos.sortedByDescending { repo -> repo.forks_count }
            .take(input.repoCount)
    }


    /**
     * Makes a request through [GithubService] and returns a list of all the committees of the repos of the organisation.
     */
    fun getTopUsers(repo: Repo): List<User>? {
        val topCommittees = mutableListOf<User>()
        var hasMorePages = input.topCommittees > 0

        while (hasMorePages) {
            val currRequest = repo.loadContributors(service, input)
            if (currRequest.body() == null) break
            topCommittees.addAll(currRequest.body()!!)
            hasMorePages =
                topCommittees.size < input.topCommittees && ("rel=\"next\"" in currRequest.headers()["Link"].orEmpty())
        }

        return topCommittees.sortedByDescending { user -> user.total }
            .take(input.topCommittees)
    }
}
