package com.example.minorproject.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.minorproject.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.add_details_fragment.*
import kotlinx.android.synthetic.main.loginfragment.*
import kotlinx.android.synthetic.main.profile_fragment.*
import java.util.*

class UserProfileFragment : Fragment() {
    lateinit var rootview: View
    private var filepath: Uri? = null
    var uid: String? = null
    var url: String? = null
    var username:String?=null
    private lateinit var storage: FirebaseStorage
    lateinit var db: FirebaseFirestore
    lateinit var storageRef: StorageReference
    lateinit var mAuth: FirebaseAuth
    lateinit var snack: Snackbar
    private var user: FirebaseUser? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview = inflater.inflate(R.layout.profile_fragment, container, false)
        return rootview

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        activity?.title = "Update Profile"



        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference
        user = mAuth.getCurrentUser()

        savebutton_profilefragment.setOnClickListener(View.OnClickListener {
            username=edittext_username.text.toString()
            sendImageToFireStore()
        })
        imageview_profile_fragment.setOnClickListener(View.OnClickListener {
            chooseImage()
        })


    }

    private fun chooseImage() {
        val options = arrayOf("Take Photo", "Choose From Gallery", "Cancel")
        val builder = MaterialAlertDialogBuilder(context)
        with(builder) {
            setItems(options) { dialog, which ->
                if (options[which].equals("Take Photo")) {
                    dialog.dismiss()
                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePictureIntent, 9347)
                } else if (options[which].equals("Choose From Gallery")) {
                    dialog.dismiss()
                    val takeImageFromGallery = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(takeImageFromGallery, 8516)
                } else if (options[which].equals("Cancel")) {
                    dialog.dismiss()
                }
            }


        }
        builder.show()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            9347 -> if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
                filepath = data.getData()
                Log.i("image added from camera", resultCode.toString())

                imageview_profile_fragment.setImageURI(data.data)


            }

            8516 -> if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
                Log.i("INtent ki value", data.toString())

                filepath = data.getData()

                Log.i("image added fromgallery", resultCode.toString())

                imageview_profile_fragment.setImageURI(data.data)


            }
        }

    }

    private fun sendImageToFireStore() {
        uid = mAuth.currentUser?.uid
        if (filepath != null) {
            val imageRef = storageRef.child("images/" + UUID.randomUUID().toString())
            imageRef.putFile(filepath!!)
                .addOnSuccessListener {
                    Log.i("on success", "uploaded")
                    downloadUrl(imageRef)

                }
                .addOnFailureListener {
                    Log.i("on failure", "not uploaded")

                }

        }
    }

    private fun downloadUrl(imageRef: StorageReference) {
        imageRef.getDownloadUrl()
            .addOnSuccessListener {
                url = it.toString()
                addProfileTOFirestore()
                Log.i("profilepicture", url)



                if (url != null) {

                    Glide.with(this.context!!).load(url).into(imageview_profile_fragment)
                    Log.i("profilepicture", url)


                } else {
                    Log.i("if is not working", "if mei ni aye")
                }
            }

    }

    private fun addProfileTOFirestore() {

        val user = hashMapOf( "profile picture" to url ,"username" to username)
        db.collection("userdetails")
            .document(mAuth.currentUser!!.uid)
            .set(user as Map<*, *>, SetOptions.merge())
            .addOnCompleteListener { documentReference ->
                Log.i("data added", "DocumentSnapshot added with ID")
                snack = Snackbar.make(linear_profile_fragment, "Uploaded", Snackbar.LENGTH_LONG)
                snack.setAction("DISMISS", View.OnClickListener {
                    System.out.println("snack clicked")
                })
                snack.show()

            }
            .addOnFailureListener { documentRefrence ->
                Log.i("data not added", "Error adding document")

                snack = Snackbar.make(linear_profile_fragment, "Failed", Snackbar.LENGTH_LONG)
                snack.setAction("DISMISS", View.OnClickListener {
                    System.out.println("snack clicked")
                })
                snack.show()

            }
    }


}