package com.example.minorproject.repo

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.minorproject.MainActivity
import com.example.minorproject.R
import com.example.minorproject.category.ui.CategoryListFragment
import com.example.minorproject.login.ViewModel.LoginModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.display_user_details_fragment.*

class SignInRepo {
    val mAuth = FirebaseAuth.getInstance()
    var database = FirebaseFirestore.getInstance()
    //var onSuccess = MutableLiveData<Boolean>()


    fun signUp(email: String, password: String,view: View){


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
//                snack= Snackbar.make(linear_loginfragment,"signup",Snackbar.LENGTH_LONG)
//                snack.setAction("DISMISS",View.OnClickListener {
//                    System.out.println("snack clicked")
//                })
//                snack.show()
                //onSuccess.value = true
                addDataTOFirestore(email)
              moveToNextScreen(view)
                Log.i("signup hogya", "signup successfully")

            } else {
//                snack= Snackbar.make(linear_loginfragment,"cant signup",Snackbar.LENGTH_LONG)
//                snack.setAction("DISMISS",View.OnClickListener {
//                    System.out.println("snack clicked")
//                })
//                snack.show()
                //onSuccess.value = false
                Log.i("nhi hua signup", "not signup")
                Log.e("error", task.exception?.message)
                // moveToNextScreen()
                // SignInFailureHelper.handler(task,true)
            }

        }
        //return onSuccess


    }


    fun login(email: String, password: String, view: View) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
               // onSuccess.value = true

                Log.i("loged in", "loged in again")
                Log.i("password", password)
                moveToNextScreen(view)
            }





//            else {
//            Log.i("not loged in", "not loged in again")
//
//            Log.i("not loged in", "not loged in again")
//            Log.i("password", password)
//            Log.i("login error", task.exception?.message)
//            onSuccess.value = false
//
//
//        }
        }

        //  moveToNextScreen(view)


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

    /*fun loadUser(useremail1: String, username1: String, userimage1: String) {


        val documentReference: DocumentReference =
            database.collection("userdetails").document(mAuth.currentUser!!.uid)
        documentReference.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            var useremail = documentSnapshot?.getString("email")
            var username = documentSnapshot?.getString("username")
            var userimage = documentSnapshot?.getString("profile picture")
            //textview_profile_email.text = useremail
            // textview_profile_name.text = username
            //Glide.with().load(userimage).apply(RequestOptions.circleCropTransform()).into(image_user_profile)

            //email1=useremail

        }


    }*/

    fun moveToNextScreen(view: View) {
        val fragment = CategoryListFragment()

        val fragmentTransaction =
            (view.context as MainActivity).supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()


    }
}



