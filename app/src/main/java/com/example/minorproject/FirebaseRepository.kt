package com.example.minorproject

import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import com.example.minorproject.category.adapter.Adapter
import com.example.minorproject.category.viewmodel.CategoryModel
import com.example.minorproject.category_detail.AddCategoryFragment
import com.example.minorproject.category_detail.AddSubCategoryFragment

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import java.util.ArrayList

class FirebaseRepository {
    var database = FirebaseFirestore.getInstance()
    private lateinit var storage: FirebaseStorage
//var id:String=bundle
//lateinit var subCategoryModel:SubCategoryModel
//var addCategoryFragment=AddCategoryFragment()
    //var adapter=Adapter()
    var user = FirebaseAuth.getInstance().currentUser
    var categoryLiveData: MutableLiveData<ArrayList<CategoryModel>> = MutableLiveData()
    var subCategoryLiveData:MutableLiveData<ArrayList<CategoryModel>> = MutableLiveData()


    fun loadCategoryToRecycler(): MutableLiveData<ArrayList<CategoryModel>> {

        var arrayList: ArrayList<CategoryModel> = ArrayList()
//        var aa =subCategoryModel.categoryId
//        Log.i("aa ki value","id"+aa)

        database.collection("categorynameimages")
            //.get()
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

    fun loadSubCategoryToRecycler(categoryid:String):MutableLiveData<ArrayList<CategoryModel>>{
        var arrayList: ArrayList<CategoryModel> = ArrayList()
       // var aa=subCategoryModel.categoryId
//        var aa =addSubCategoryFragment.categoryId
//        Log.i("aa ki value","id"+aa)

        database.collection("Subcategory").document(categoryid).collection("SubcategoryImages")
            .addSnapshotListener(EventListener<QuerySnapshot>{value, e ->
                if(e!=null){
                    Log.i("listen failed", "Listen failed.", e)
                    subCategoryLiveData.value=null
                    return@EventListener
                }
                if (value!=null){
                    for (document:QueryDocumentSnapshot in value){
                        var subCategoryTitle=document.data.get("subcategorytitle").toString()
                        var subCategoryImage=document.data.get("subcategoryurl").toString()

                        Log.i("subcatimagetitle", subCategoryTitle)
                        Log.i("subcatimageurl", subCategoryImage)

                        val items=arrayList.add(CategoryModel(subCategoryTitle,subCategoryImage,categoryid))
                        Log.i("DATA ADDED", items.toString())

                    }
                }
                subCategoryLiveData.value=arrayList
                Log.i("items ki value", "${subCategoryLiveData}")
            })
        return subCategoryLiveData
    }


}







