package com.example.minorproject

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.minorproject.category.viewmodel.CategoryModel
import com.example.minorproject.login.ViewModel.LoginModel
import com.example.minorproject.timeline.ViewModel.TimelineModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.loginfragment.*
import java.util.ArrayList

class FirebaseRepository {
    var database = FirebaseFirestore.getInstance()
   var  mAuth = FirebaseAuth.getInstance()
    var categoryLiveData: MutableLiveData<ArrayList<CategoryModel>> = MutableLiveData()
    var subCategoryLiveData: MutableLiveData<ArrayList<CategoryModel>> = MutableLiveData()
    var timelineLiveData: MutableLiveData<ArrayList<TimelineModel>> = MutableLiveData()
   // var userLiveData: MutableLiveData<List<ProfileModel>> = MutableLiveData()

    /*fun signUp(email:String,password:String): MutableLiveData<LoginModel>{
        var result:MutableLiveData<Result<Boolean>> = MutableLiveData()
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->

            if (task.isSuccessful) {
               result.value=Result.success(true)
                Log.i("signup hogya", "signup successfully")
            } else {

                result.value= Result.failure()
                Log.i("nhi hua signup", "not signup")
                Log.e("error",task.exception?.message)
                // moveToNextScreen()
            }
        }



    }*/


    fun loadCategoryToRecycler(): MutableLiveData<ArrayList<CategoryModel>> {

        var arrayList: ArrayList<CategoryModel> = ArrayList()


        database.collection("categorynameimages")
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.i("listen failed", "Listen failed.", e)
                    categoryLiveData.value = null
                    return@EventListener
                }
                if (value != null) {
                    for (document: QueryDocumentSnapshot in value) {
                        var categoryid: String = document.id
                        var imageTitle = document.data.get("categorytitle").toString()
                        var imageUrl = document.data.get("categorynameimage").toString()
                        Log.i("imagetitle", imageTitle)
                        Log.i("imageurl", imageUrl)

                        val items = arrayList.add(
                            CategoryModel(
                                imageTitle,
                                imageUrl, categoryid
                            )
                        )
                        Log.i("DATA ADDED", items.toString())
                    }
                }
                categoryLiveData.value = arrayList
                Log.i("items ki value", "${categoryLiveData}")
            })
        return categoryLiveData
    }

    fun loadSubCategoryToRecycler(
        categoryid: String,
        subcategoryid: String
    ): MutableLiveData<ArrayList<CategoryModel>> {

        var arrayList: ArrayList<CategoryModel> = ArrayList()


        database.collection("Subcategory").document(categoryid).collection("SubcategoryImages")
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.i("listen failed", "Listen failed.", e)
                    subCategoryLiveData.value = null
                    return@EventListener
                }
                if (value != null) {
                    for (document: QueryDocumentSnapshot in value) {
                        var subcategoryid = document.id
                        var subCategoryTitle = document.data.get("subcategorytitle").toString()
                        var subCategoryImage = document.data.get("subcategoryurl").toString()

                        Log.i("subcatimagetitle", subCategoryTitle)
                        Log.i("subcatimageurl", subCategoryImage)

                        val items = arrayList.add(
                            CategoryModel(
                                subCategoryTitle,
                                subCategoryImage,
                                categoryid, subcategoryid
                            )
                        )
                        Log.i("DATA ADDED", items.toString())

                    }
                }
                subCategoryLiveData.value = arrayList
                Log.i("items ki value", "${subCategoryLiveData}")
            })
        return subCategoryLiveData
    }

    fun loadtimelineData(): MutableLiveData<ArrayList<TimelineModel>> {
        var arrayList: ArrayList<TimelineModel> = ArrayList()
        database.collection("Timeline").orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.i("listen failed", "Listen failed.", e)
                    timelineLiveData.value = null
                    return@EventListener
                }
                if (value != null) {
                    for (document: QueryDocumentSnapshot in value) {
                        var timelineTitle = document.data.get("subcategorytitle").toString()
                        var timelineImage = document.data.get("subcategoryurl").toString()

                        var timelineDate = document.data.get("timestamp").toString()


                        Log.i("timelinetitle", timelineTitle)
                        Log.i("subcatimageurl", timelineImage)

                        Log.i("timelinedate", timelineDate)


                        val items = arrayList.add(
                            TimelineModel(
                                timelineTitle,
                                timelineImage,
                                timelineDate
                            )
                        )
                        Log.i("DATA ADDED", items.toString())


                    }
                }
                timelineLiveData.value = arrayList
                Log.i("items ki value", "${timelineLiveData}")
            })
        return timelineLiveData
    }




    /*fun loadUserData(): MutableLiveData<List<ProfileModel>> {
        var arrayList: ArrayList<ProfileModel> = ArrayList()
        database.collection("userdetails")
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.i("listen failed", "Listen failed.", e)
                    userLiveData.value = null
                    return@EventListener
                }
                if (value != null) {
                    for (document: QueryDocumentSnapshot in value) {
                        var userName = document.data.get("username").toString()
                        var userImage = document.data.get("profile picture").toString()
                        var userEmail = document.data.get("email").toString()
                        Log.i("username", userName)
                        Log.i("userimage", userImage)
                        Log.i("useremail", userEmail)

                        val items = arrayList.add(
                            ProfileModel(
                                userName,
                                userImage, userEmail
                            )
                        )
                        Log.i("DATA ADDED", items.toString())
                    }
                }
                userLiveData.value = arrayList

            })
        return userLiveData


    }*/
}







