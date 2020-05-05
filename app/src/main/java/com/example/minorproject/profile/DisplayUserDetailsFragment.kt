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
import com.example.minorproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.display_user_details_fragment.*

class DisplayUserDetailsFragment :Fragment() {
    lateinit var rootView: View
    private lateinit var mAuth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    var useremail: String? = null
    var username: String? = null
    var userimage: String? = null


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

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val documentReference: DocumentReference =
            db.collection("userdetails").document(mAuth.currentUser!!.uid)
        documentReference.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            useremail = documentSnapshot?.getString("email")
            username = documentSnapshot?.getString("username")
            userimage = documentSnapshot?.getString("profile picture")
            textview_profile_email.text = useremail
            textview_profile_name.text = username

            Glide.with(this).load(userimage).into(image_user_profile)


        }

    }
}