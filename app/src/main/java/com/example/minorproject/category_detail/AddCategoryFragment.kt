package com.example.minorproject.category_detail

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
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
import com.example.minorproject.category.ui.CategoryListFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.add_details_fragment.*
import java.util.*

class AddCategoryFragment : Fragment() {
    lateinit var rootView: View
    private lateinit var storage: FirebaseStorage
    lateinit var db: FirebaseFirestore
    lateinit var storageRef: StorageReference
    lateinit var mAuth: FirebaseAuth
    private var user: FirebaseUser? = null
    private var filepath: Uri? = null
    lateinit var url: String
    var uid: String? = null
    var titleKey: String = "categorytitle"
    var imageKey: String = "categorynameimage"
    var isCategory: Boolean = true
    lateinit var snack: Snackbar


    lateinit var newCategoryName: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.add_details_fragment, container, false)
        return rootView

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUi()
        Log.i("activity created", "activity created")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {
            isCategory = it.getBoolean("isCategory")
            Log.i("on create", "on create")

            if (!isCategory) {
                Log.i("if is category", isCategory.toString())
                imageKey = "subcategoryimage"
                titleKey = "subcategorytitle"
            } else {
                Log.i("is categoryindetails", isCategory.toString())
            }
        }


    }


    private fun initUi() {
        activity?.title=""
        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference
        user = mAuth.getCurrentUser()


        val aa = db.collection("categorynameimages")


        savebutton_add_details_fragment.setOnClickListener(View.OnClickListener {
            //            //add this new category(image and edit text to recyclerview)
            //return back to category_list_fragment(rimecyclerview waala fragment)
            sendImageToFireStore()

            newCategoryName = edittext_add_details_fragment.text.toString()


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
                if (options[which] == "Take Photo") {
                    dialog.dismiss()
                    val takePictureIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePictureIntent, 9347)
                    Log.i("REquest code", targetRequestCode.toString())

                } else if (options[which] == "Choose From Gallery") {
                    dialog.dismiss()
                    val takeImageFromGallery = Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(takeImageFromGallery, 8516)
                    Log.i("REquest code", targetRequestCode.toString())
                } else if (options[which] == "Cancel") {
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
            9347 -> if (resultCode == RESULT_OK && data != null) {
                Log.i("INtent ki value", data.toString())
                filepath = data.getData()
                Log.i("image added from camera", resultCode.toString())


                imageView_add_details_fragment.setImageURI(data.data)


            }

            8516 -> if (resultCode == RESULT_OK && data != null && data.data != null) {
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
                if (!isCategory) {
                    Log.i("sub cat called", "sub cat called")

                } else {
                    Log.i("catg called", "catg called")
                    sendUrlToCategoryCollection()
                }
                if (url != null) {
                    Log.i("if mei aaye", "if mei aye")
                    Glide.with(this.context!!).load(url).into(imageView_add_details_fragment)
                    Log.i("17/april/2020urlinglide", url)


                } else {
                    Log.i("if mei ni  aaye", "if mei ni aye")
                }

            }


    }

    private fun sendUrlToCategoryCollection() {
        val imageDetails = hashMapOf(imageKey to url, titleKey to newCategoryName, "imageid" to uid)
        db.collection("categorynameimages")

            .add(imageDetails as Map<*, *>)
            .addOnCompleteListener {

                Log.i("data added", "DocumentSnapshot added with ID")
                snack = Snackbar.make(layout_add_details_fragment, "Uploaded", Snackbar.LENGTH_LONG)
                snack.setAction("DISMISS", View.OnClickListener {
                    System.out.println("snack clicked")
                })
                snack.show()


            }
            .addOnFailureListener {
                Log.i("data not added", "Error adding document")
                snack = Snackbar.make(layout_add_details_fragment, "Failed", Snackbar.LENGTH_LONG)
                snack.setAction("DISMISS", View.OnClickListener {
                    System.out.println("snack clicked")
                })
                snack.show()

            }
    }


}









