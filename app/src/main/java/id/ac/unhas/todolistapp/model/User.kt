package id.ac.unhas.todolistapp.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Praveen John on 11/12/2021
 * Model class for login
 * */
data class User (

    @SerializedName("email"     ) var email     : String? = null,
    @SerializedName("password"  ) var password  : String? = null

)