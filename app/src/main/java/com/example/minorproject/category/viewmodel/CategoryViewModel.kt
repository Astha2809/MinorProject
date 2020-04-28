package com.example.minorproject.category.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.FirebaseRepository

class CategoryViewModel : ViewModel( ) {
    //var addCategoryFragment = AddCategoryFragment()
    var firebaseRepository= FirebaseRepository()
    //var savedDetails: MutableLiveData<List<CategoryModal>> = MutableLiveData()
    var categoryData: MutableLiveData<ArrayList<CategoryModel>> = MutableLiveData()

    /*fun getSavedDetails(): LiveData<List<CategoryModal>> {
        firebaseRepository.getSavedDetails()
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.i("listen failed", "Listen failed.", e)
                    savedDetails.value = null

                }
                val savedDetailsList: MutableList<CategoryModal> = mutableListOf()
                for (doc in value!!) {
                    val categoryItems = doc.toObject(CategoryModal::class.java)
                    savedDetailsList.add(categoryItems)

                }
                savedDetails.value = savedDetailsList

            })


        return savedDetails


    }*/
    init {
        loadCategoryToRecycler()

    }
    fun loadCategoryToRecycler():LiveData<ArrayList<CategoryModel>> {
       categoryData= firebaseRepository.loadCategoryToRecycler()


        Log.i("category", categoryData.toString())
        return categoryData

    }

}