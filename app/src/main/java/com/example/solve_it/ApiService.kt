import com.example.solve_it.models.QueryHistory
import retrofit2.Response
import retrofit2.http.*
import com.example.solve_it.models.User

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: String): User

    @GET("QueryHistory")
    suspend fun getQueryHistory(): List<QueryHistory>

    @POST("QueryHistory")
    suspend fun addQueryHistory(@Body history: QueryHistory): Response<QueryHistory>

    @POST("users")
    suspend fun createUser(@Body user: User): Response<User>

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: String, @Body user: User): Response<User>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: String): Response<Unit>
}