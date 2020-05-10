package com.example.minorproject.login.repo

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.minorproject.MainActivity
import com.example.minorproject.R
import com.example.minorproject.category.ui.CategoryListFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class SignInRepo {
    val mAuth = FirebaseAuth.getInstance()
    var database = FirebaseFirestore.getInstance()
    var onSuccess = MutableLiveData<Boolean>(false)


    fun signUp(email: String, password: String): MutableLiveData<Boolean> {


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess.value = true
                addDataTOFirestore(email)
                // moveToNextScreen(view)
                Log.i("signup", "signup successfully")

            } else {

                onSuccess.value = false
                Log.i("failed", "not signup")
                Log.e("error", task.exception?.message)

            }

        }
        return onSuccess


    }


    fun login(email: String, password: String): MutableLiveData<Boolean> {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess.value = true


                Log.i("loged in", "loged in again")
                Log.i("password", password)

            }






            onSuccess.value = false

        }
        return onSuccess


    }


    private fun addDataTOFirestore(email: String) {
        val user = hashMapOf("email" to email)
        database.collection("userdetails")

            .document(mAuth.currentUser!!.uid).set(user as Map<String, Any>, SetOptions.merge())

            .addOnCompleteListener { documentReference ->
                Log.i("data added", "DocumentSnapshot added with ID")

            }
            .addOnFailureListener { documentRefrence ->
                Log.i("data not added", "Error adding document")
            }
    }


}



