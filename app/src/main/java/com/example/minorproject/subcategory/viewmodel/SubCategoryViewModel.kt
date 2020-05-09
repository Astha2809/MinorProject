package com.example.minorproject.subcategory.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.common.FirebaseRepository
import com.example.minorproject.category.viewmodel.CategoryModel

class SubCategoryViewModel :ViewModel(){
    var firebaseRepository= FirebaseRepository()

    var subCategoryData:MutableLiveData<ArrayList<CategoryModel>> = MutableLiveData()

//    init {
//        loadSubCategoryToRecycler()
//    }


    fun loadSubCategoryToRecycler(categyid:String,subcategoryid:String): LiveData<ArrayList<CategoryModel>> {
        subCategoryData=firebaseRepository.loadSubCategoryToRecycler(categyid,subcategoryid)

        Log.i("subcategory",subCategoryData.toString())
        return subCategoryData
    }

}