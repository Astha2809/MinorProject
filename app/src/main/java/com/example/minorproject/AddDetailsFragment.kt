package com.example.minorproject

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.android.gms.common.wrappers.Wrappers.packageManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.add_details_fragment.*
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*

class AddDetailsFragment : Fragment() {
    lateinit var rootView: View
    //    lateinit var selectedImage1: Bitmap
//    lateinit var selectedImage:Uri
    private lateinit var storage: FirebaseStorage
    lateinit var db: FirebaseFirestore
    lateinit var storageRef: StorageReference
    lateinit var mAuth: FirebaseAuth
    private var user:FirebaseUser?=null
    private  var filepath: Uri?=null
    private lateinit var url:String

    //lateinit var newCategory:EditText
    //lateinit var newCategoryImage:String
    //lateinit var saveCategoryButton:Button
//    lateinit var fstorage: FirebaseStorage
//    lateinit var aa:FirebaseFirestore

    lateinit var newCategoryName: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.add_details_fragment, container, false)

        //initUi()
        return rootView

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUi()
        Log.i("activity created","activity created")
    }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initUi()
//        Log.i("view created","view created")
//
//    }

    private fun initUi() {
        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference
        user= mAuth.getCurrentUser()


        savebutton_add_details_fragment.setOnClickListener(View.OnClickListener {
            //            //add this new category(image and edit text to recyclerview)
            //return back to add_category_fragment(recyclerview waala fragment)
            sendImageToFireStore()
            newCategoryName=edittext_add_details_fragment.text.toString()





        })


        imageView_add_details_fragment.setOnClickListener(View.OnClickListener {
            chooseImage()

        })

        if(url!=null) {
            Log.i("if mei aaye", "if mei aye")
            Glide.with(this.context!!).load(url).into(imageView_add_details_fragment)
            Log.i("url in glide", url)


        }
        else{
            Log.i("if mei ni aaye", "if mei ni aye")
        }

    }

    private fun chooseImage() {
        val options = arrayOf("Take Photo", "Choose From Gallery", "Cancel")
        val builder = MaterialAlertDialogBuilder(context)
        with(builder) {
            setItems(options) { dialog, which ->
                if (options[which].equals("TakePhoto")) {
                    dialog.dismiss()
                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePictureIntent, 9347)
                } else if (options[which].equals("Choose From Gallery")) {
                    dialog.dismiss()
                    val takeImageFromGallery = Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
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
            9347 -> if (resultCode == RESULT_OK && data != null && data.data != null) {
                filepath = data.getData()
                Log.i("image added from camera", resultCode.toString())

                imageView_add_details_fragment.setImageURI(data.data)



            }

            8516 -> if (resultCode == RESULT_OK && data!=null && data.data!=null) {
                Log.i("INtent ki value", data.toString())

                filepath = data.getData()

                Log.i("image added fromgallery", resultCode.toString())

                imageView_add_details_fragment.setImageURI(data.data)


            }
        }

    }

    private fun sendImageToFireStore() {
     val uid: String? = mAuth.currentUser?.uid
        if (filepath != null) {
            //val imageRef = storageRef.child("images/" + UUID.randomUUID().toString())
            val imageRef = storageRef.child("images/")
                .child(uid+".jpeg")
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
    private fun downloadUrl(imageRef: StorageReference){
        imageRef.getDownloadUrl()
            .addOnSuccessListener {
                url= it.toString()
                Log.i(" image url",url)
                sendUrlToCollection()
//                if(url!=null) {
//                    Log.i("if mei aaye", "if mei aye")
//                    Glide.with(this.context!!).load(url).into(imageView_add_details_fragment)
//                    Log.i("url in glide", url)
//
//
//                }
//                else{
//                    Log.i("if mei ni  aaye", "if mei ni aye")
//                }

            }


    }
    private fun sendUrlToCollection(){
        val imageDetails= hashMapOf("categorynameimage" to url ,"categorytitle" to newCategoryName)
        db.collection("categorynameimages")
            .document(mAuth.currentUser!!.uid)
            .set(imageDetails as Map<*, *> )
            .addOnCompleteListener {
                Log.i("data added", "DocumentSnapshot added with ID")
            }
            .addOnFailureListener {
                Log.i("data not added", "Error adding document")
            }
    }

}








