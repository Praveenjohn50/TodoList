package id.ac.unhas.todolistapp.ui.login

import androidx.lifecycle.*
import id.ac.unhas.todolistapp.model.Repository
import kotlinx.coroutines.launch

/**
 * Created by Praveen John on 11/12/2021
 * ViewModel for login
 * */
class LoginViewModel : ViewModel() {

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            Repository.validateCredentials(email, pass)
        }
    }

}