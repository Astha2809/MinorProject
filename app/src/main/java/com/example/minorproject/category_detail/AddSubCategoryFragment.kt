package com.example.minorproject.category_detail

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
import com.example.minorproject.subcategory.ui.SubcategoryFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.add_details_fragment.*
import kotlinx.android.synthetic.main.sub_category_fragment.*
import java.security.Timestamp
import java.sql.Time
import java.time.Instant.now
import java.time.LocalDate.now
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.time.LocalTime
import java.time.LocalTime.now
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

import java.util.*

class AddSubCategoryFragment :Fragment() {
    private lateinit var storage: FirebaseStorage
    lateinit var db: FirebaseFirestore
    lateinit var storageRef: StorageReference
    lateinit var mAuth: FirebaseAuth
    private var user: FirebaseUser? = null
    private var filepath: Uri? = null
    var url: String?=null
    var uid: String? = null
    var titleKey: String = "subcategorytitle"
    var imageKey: String = "subcategoryurl"
    lateinit var rootView: View
    lateinit var newSubCategoryName: String
    var categoryId:String=""
    var subcategoryId:String=""
    lateinit var snack: Snackbar
//    @RequiresApi(Build.VERSION_CODES.O)
//    @ServerTimestamp
//    val date=Date()
//    @RequiresApi(Build.VERSION_CODES.O)
//    val time=LocalTime.now()

    @RequiresApi(Build.VERSION_CODES.O)
    val currentTime=LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)
    val formattedtime=DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
@RequiresApi(Build.VERSION_CODES.O)
val time=currentTime.format(formattedtime)
       // DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

    //val timestamp = Timestamp.now()
    //val time=Timestamp.now()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.add_details_fragment, container, false)
        return rootView


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference
        user = mAuth.getCurrentUser()
        categoryId= arguments?.getString("categoryid").toString()
        Log.i("id add sub ","id"+categoryId)

//        subcategoryId=arguments?.getString("subcategoryid").toString()
//        Log.i("id add subcat ","id"+subcategoryId)




        savebutton_add_details_fragment.setOnClickListener(View.OnClickListener {
            newSubCategoryName=edittext_add_details_fragment.text.toString()
            sendImageToFireStore()

        })

        imageView_add_details_fragment.setOnClickListener(View.OnClickListener {
            chooseImage()

        })

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

                imageView_add_details_fragment.setImageURI(data.data)


            }

            8516 -> if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
                Log.i("INtent ki value", data.toString())

                filepath = data.getData()

                Log.i("image added fromgallery", resultCode.toString())

                imageView_add_details_fragment.setImageURI(data.data)


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
                Log.i("17/april/2020 image url", url)
                    sendUrlToSubCategoryCollection()
                    // sendDataToTimeline()

                if (url != null) {
                    Log.i("if mei aaye", "if mei aye")
                    Glide.with(this.context!!).load(url).into(imageView_add_details_fragment)
                    Log.i("17/april/2020urlinglide", url)


                } else {
                    Log.i("if mei ni  aaye", "if mei ni aye")
                }
            }

    }

    private fun sendUrlToSubCategoryCollection() {
        val imageDetails =
            hashMapOf(imageKey to url, titleKey to newSubCategoryName, "imageid" to categoryId)
        db.collection("Subcategory").document(categoryId).collection("SubcategoryImages")
            // .document(mAuth.currentUser!!.uid)
            .add(imageDetails as Map<*, *>)
            .addOnSuccessListener {DocumentReference ->
                val id1 = DocumentReference.id
            sendDataToTimeline(id1)

        Log.i("data added", "DocumentSnapshot added with ID")
                snack= Snackbar.make(layout_add_details_fragment,"Uploaded",Snackbar.LENGTH_LONG)
                snack.setAction("DISMISS",View.OnClickListener {
                    System.out.println("snack clicked")
                })
                snack.show()

            }
            .addOnFailureListener {
                Log.i("data not added", "Error adding document")
                snack= Snackbar.make(layout_add_details_fragment,"Failed",Snackbar.LENGTH_LONG)
                snack.setAction("DISMISS",View.OnClickListener {
                    System.out.println("snack clicked")
                })
                snack.show()

            }


    }

    private fun sendDataToTimeline(ab:String){
        val timelineDetails= hashMapOf(imageKey to url,titleKey to newSubCategoryName, "timestamp" to time)
        db.collection("Timeline").document(ab)
            .set(timelineDetails as Map<*,*>)
            .addOnCompleteListener {
                Log.i("timeline data added", "DocumentSnapshot added with ID")
            }
            .addOnFailureListener {
                Log.i("timeline data not added", "Error adding document")
            }

    }

//    private fun openSubCategoryFragment(){
//        val fragment= SubcategoryFragment()
//        val fragmentTransaction=activity!!.supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.container,fragment)
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
//    }
}






