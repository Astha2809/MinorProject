package com.example.minorproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList

class FirebaseRepository {
    var database = FirebaseFirestore.getInstance()
    var user = FirebaseAuth.getInstance().currentUser
    var items:MutableLiveData<ArrayList<CategoryModal>> =MutableLiveData()

    fun getSavedDetails(): CollectionReference {
        val collectionReference = database.collection("categorynameimages")
         //loadImages()
        return collectionReference


    }

     fun loadImages():MutableLiveData<ArrayList<CategoryModal>> {
         val a=Alpha()
        //imageList= ArrayList()
       // var items: ArrayList<CategoryModal> = ArrayList()
         //var arrayList:MutableLiveData<List<CategoryViewModel>>
         //var items:MutableLiveData<ArrayList<CategoryModal>> = MutableLiveData()
              // var aa:ArrayList<CategoryModal>
        database.collection("categorynameimages")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        var imageTitle = document.data.get("categorytitle").toString()
                        var imageUrl = document.data.get("categorynameimage").toString()
                        Log.i("imagetitle", imageTitle)
                        Log.i("imageurl", imageUrl)

                        val bb = items.value?.add(CategoryModal(imageTitle, imageUrl))
                       // arrayList.value=items
                        Log.i("bb ki value", bb.toString())
                    }
                    // val addCategoryFragmentAdapter= context?.let { AddCategoryFragmentAdapter(it, categryList!!) }
                    //recycler.adapter=addCategoryFragmentAdapter


                }

            }


        return items
    }

}