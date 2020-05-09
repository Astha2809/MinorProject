package com.example.minorproject.login.ViewModel

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.common.FirebaseRepository
import com.example.minorproject.login.EMAIL_SCREEN
import com.example.minorproject.login.PASSWORD_SCREEN

import com.example.minorproject.repo.SignInRepo
import com.example.minorproject.utils.Validation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private var errMessage = MutableLiveData<String>()
    var signInRepo = SignInRepo()
    var firebaseRepository = FirebaseRepository()
    var isNewUser:Boolean=false
    var currentScreen=MediatorLiveData<Int>()
    //var onSuccess=MediatorLiveData<Boolean>()
    var currentScreenVal= EMAIL_SCREEN
   // lateinit var context: Context
   var mAuth = FirebaseAuth.getInstance()

    var userData: MutableLiveData<LoginModel> = MutableLiveData()



    fun getErrMessage(): LiveData<String> {
        return errMessage
    }

    fun signUp(email: String, password: String,view: View) {
        if (TextUtils.isEmpty(email)) {

            errMessage.value = "Empty email"

        } else if (!Validation.isValidEmail(email)) {

            errMessage.value = "Invalid Email Address"

        } else if (TextUtils.isEmpty(password)) {

            errMessage.value = "Empty Password"

        } else {
            signInRepo.signUp(email, password,view)
        }
//        else if(TextUtils.isEmpty(password) {
//            // Empty password
//            errMessage.value = "empty password"
//            return
//        }

    }


    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    }

    fun login(email: String, password: String,view:View) {

        if (TextUtils.isEmpty("Empty email")) {
            errMessage.value = "Empty email"
            return
        }
        else if (!Validation.isValidEmail(email)) {

            errMessage.value = "Invalid Email Address"

        } else if (TextUtils.isEmpty(password)) {

            errMessage.value = "Empty Password"
        }

            else {
            signInRepo.login(email, password,view)



        }
    }

    //    fun loadUserData(username:String,useremail: String,userimage:String){
//        signInRepo.loadUser(useremail, username, userimage)
//    }
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

                        //Signup()


                    } else {
                        //signup failed due to some aapi error
                        // snack = Snackbar.make(linear_loginfragment, "cant signup", Snackbar.LENGTH_LONG)
                        //snack.setAction("DISMISS", View.OnClickListener {
                        System.out.println("snack clicked")
                        //})
                        //snack.show()
                        Log.i("signup fail hogya", "signup failed")


                    }


                })


        }
    }


    fun loadData(): MutableLiveData<LoginModel> {
        userData = firebaseRepository.loadUserData()
        Log.i("user", userData.toString())
        return userData

    }
}