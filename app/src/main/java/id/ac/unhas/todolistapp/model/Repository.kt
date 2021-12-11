package id.ac.unhas.todolistapp.model

import androidx.lifecycle.MutableLiveData
import id.ac.unhas.todolistapp.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by Praveen John on 11/12/2021
 * Repository class for login
 * */
object Repository {

    /**
     * Live data for login state
     * */
    var isLogin: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * Method to post the login
     * @param email : Email
     * @param password: Password
     * */
    suspend fun validateCredentials(email: String, password: String) {

        val response = validateViaServer(email, password)
        handleLoginResponse(response)


    }

    /**
     * Response from login
     * @param response : Response
     * */
    private fun handleLoginResponse(response: Call<User>) {
        response.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                isLogin.postValue(true)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                isLogin.postValue(true)
            }
        })

    }

    /**
     * Api Call for login
     * @param email : Email
     * @param password: Password
     * */
    private suspend fun validateViaServer(email: String, password: String) =
        RetrofitInstance.api.loginValidation(User(email, password))

}