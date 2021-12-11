package id.ac.unhas.todolistapp.network

import id.ac.unhas.todolistapp.model.User
import id.ac.unhas.todolistapp.utils.TodoAppConstants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body

import retrofit2.http.POST


/**
 * Created by Praveen John on 11/12/2021
 * Helper class for network calls
 * */
interface MovieApi {

    /**
     * Api Call for login
     * @param email : Email
     * @param password: Password
     * */
    @POST("api/login ")
    fun loginValidation(@Body user: User?): Call<User>

}

/**
 * Retrofit instance creation
 * */
class RetrofitInstance {
    companion object {

        private val retrofit by lazy {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().addInterceptor(logging).build()


            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        }

        /**
         * Api client
         * */
        val api: MovieApi by lazy {

            retrofit.create(MovieApi::class.java)

        }

    }

}