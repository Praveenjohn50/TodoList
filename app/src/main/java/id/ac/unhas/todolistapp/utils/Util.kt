package id.ac.unhas.todolistapp.utils

import java.util.regex.Pattern
/**
 * Created by Praveen John on 11/12/2021
 * Utils class
 * */
class Util {

    companion object {
        fun isEmailValid(email: String): Boolean {
            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }
    }


}