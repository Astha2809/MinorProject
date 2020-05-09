package com.example.minorproject.utils

class Validation {
    companion object{
        fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}