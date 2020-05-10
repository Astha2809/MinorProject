package com.example.minorproject.login.ViewModel

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.R

import com.example.minorproject.login.EMAIL_SCREEN
import com.example.minorproject.login.PASSWORD_SCREEN

import com.example.minorproject.login.repo.SignInRepo
import com.example.minorproject.utils.Validation
import com.google.android.gms.tasks.OnCompleteListener

import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private var errMessage = MutableLiveData<String>()
    var signInRepo = SignInRepo()


    var isNewUser: Boolean = false
    var currentScreen = MediatorLiveData<Int>()

    var onSuccess1= MutableLiveData<Boolean>()
    var currentScreenVal = EMAIL_SCREEN


    var mAuth = FirebaseAuth.getInstance()


    fun getErrMessage(): LiveData<String> {
        return errMessage
    }

    fun signUp(email: String, password: String): MutableLiveData<Boolean> {
        if (TextUtils.isEmpty(email)) {

            errMessage.value = "Empty email"

        } else if (!Validation.isValidEmail(email)) {

            errMessage.value = "Invalid Email Address"

        } else if (TextUtils.isEmpty(password)) {

            errMessage.value = "Empty Password"

        } else {
          onSuccess1=  signInRepo.signUp(email, password)
        }
        return onSuccess1

    }




    fun login(email: String, password: String): MutableLiveData<Boolean> {

        if (TextUtils.isEmpty(email)) {
            errMessage.value = "Empty email"

        } else if (!Validation.isValidEmail(email)) {

            errMessage.value = "Invalid Email Address"

        } else if (TextUtils.isEmpty(password)) {

            errMessage.value =  "Empty Password"
        }
        else if(password <= 6.toString()){
            errMessage.value =  "Password length should be greater than 6"

        }
        else {
          onSuccess1= signInRepo.login(email, password)


        }
        return onSuccess1
    }

    fun check(email: String) {
        if (TextUtils.isEmpty(email)) {

            errMessage.value = "Empty email"

        } else if (!Validation.isValidEmail(email)) {

            errMessage.value = "Invalid Email Address"
        } else {

            mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(OnCompleteListener { task ->


                    if (task.isSuccessful) {
                        //it returns 1 if emailid exists
                        //0 if does not exists

                        val aa: Int? = task.getResult()?.signInMethods?.size

                        if (aa == 0) {

                            isNewUser = true
                            Log.i("New user hai", "new user")

                        } else if (aa == 1) {
                            isNewUser = false
                            Log.i("new user nhi hai", "not a new user")
                        }
                        Log.i("aa ki value", aa.toString())
                        //setScreen()
                        currentScreenVal = PASSWORD_SCREEN
                        currentScreen.value = currentScreenVal



                    } else {

                        Log.i("signup fail", "signup failed")


                    }


                })


        }
    }


}