package com.example.minorproject.category.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.FirebaseRepository

class CategoryViewModel : ViewModel( ) {

    var firebaseRepository= FirebaseRepository()

    var categoryData: MutableLiveData<ArrayList<CategoryModel>> = MutableLiveData()

      init {
        loadCategoryToRecycler()

    }
    fun loadCategoryToRecycler():LiveData<ArrayList<CategoryModel>> {
       categoryData= firebaseRepository.loadCategoryToRecycler()


        Log.i("category", categoryData.toString())
        return categoryData

    }

}