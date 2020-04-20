//package com.example.minorproject
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.google.firebase.firestore.EventListener
//import com.google.firebase.firestore.QuerySnapshot
//
//class CategoryViewModel :ViewModel( ) {
//    var addCategoryFragment = AddCategoryFragment()
//    var savedDetails: MutableLiveData<List<CategoryModal>> = MutableLiveData()
//
//    fun getSavedDetails(): LiveData<List<CategoryModal>> {
//        addCategoryFragment.getSavedDetails()
//            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
//                if (e != null) {
//                    Log.i("listen failed", "Listen failed.", e)
//                    savedDetails.value = null
//
//                }
//                val savedDetailsList: MutableList<CategoryModal> = mutableListOf()
//                for (doc in value!!) {
//                    val categoryItems = doc.toObject(CategoryModal::class.java)
//                    savedDetailsList.add(categoryItems)
//
//                }
//                savedDetails.value = savedDetailsList
//
//            })
//
//
//        return savedDetails
//
//
//    }
//}