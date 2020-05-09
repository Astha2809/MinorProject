package com.example.minorproject.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.minorproject.R
import com.example.minorproject.login.ViewModel.LoginModel
import com.example.minorproject.login.ViewModel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.display_user_details_fragment.*

class DisplayUserDetailsFragment :Fragment() {
    lateinit var rootView: View

    private lateinit var mAuth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    var useremail: String = ""
    var username: String = ""
    var userimage: String =""
    lateinit var loginViewModel: LoginViewModel
    lateinit var loginModel: LoginModel
    private lateinit var userList: LiveData<LoginModel>
//    private val mViewModel by lazy {
//        ViewModelProvider(this).get(LoginViewModel::class.java)
//    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.display_user_details_fragment, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        loginViewModel = ViewModelProvider(activity!!).get(LoginViewModel::class.java)
       // val loginModel=LoginModel()

        loginViewModel.loadData().observe(viewLifecycleOwner, Observer {
//            it.email1
//            it.name1
//            it.image1

            textview_profile_email.setText(it.email1)
            textview_profile_name.setText(it.name1)
            Glide.with(this).load(it.image1).apply(RequestOptions.circleCropTransform()).into(image_user_profile)


        })


//        mAuth = FirebaseAuth.getInstance()
//        db = FirebaseFirestore.getInstance()
//        val documentReference: DocumentReference =
//            db.collection("userdetails").document(mAuth.currentUser!!.uid)
//        documentReference.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
//            useremail = documentSnapshot?.getString("email")
//            username = documentSnapshot?.getString("username")
//            userimage = documentSnapshot?.getString("profile picture")
//           textview_profile_email.text =loginModel.email1
//            textview_profile_name.text = loginModel.name1
////
//           Glide.with(this).load(loginModel.image1).apply(RequestOptions.circleCropTransform()).into(image_user_profile)
//        subCategoryViewModel.subCategoryData.observe(viewLifecycleOwner, Observer
//        { list -> list.let { adapter?.setCategoryData(it) } })
//


//
//
//        }
      //  mViewModel.loadUserData(textview_profile_email.text.toString(),textview_profile_name.text.toString(),image_user_profile.toString())


        //Glide.with(this).load(userimage).apply(RequestOptions.circleCropTransform()).into(image_user_profile)
//


    }
}