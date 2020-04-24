package com.example.minorproject

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.ArrayList

class FirebaseRepository {
    var database = FirebaseFirestore.getInstance()
    private lateinit var storage: FirebaseStorage

    var user = FirebaseAuth.getInstance().currentUser
    var mutableLiveData: MutableLiveData<ArrayList<CategoryModal>> = MutableLiveData()


    fun loadImages(): MutableLiveData<ArrayList<CategoryModal>> {
        // val a=Alpha()
        //imageList= ArrayList()
        // var items: ArrayList<CategoryModal> = ArrayList()
        //var arrayList:MutableLiveData<List<CategoryViewModel>>
        //var items:MutableLiveData<ArrayList<CategoryModal>> = MutableLiveData()
        // var aa:ArrayList<CategoryModal>
//        val user1 = User()
//        val userAddress = Address()
//        user1.name = "ujjwal"
//        userAddress.pincode = "1233"
//        user1.address = userAddress

        var arrayList: ArrayList<CategoryModal> = ArrayList()

        database.collection("categorynameimages")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        var imageTitle = document.data.get("categorytitle").toString()
                        var imageUrl = document.data.get("categorynameimage").toString()
                        Log.i("imagetitle", imageTitle)
                        Log.i("imageurl", imageUrl)

                        val items = arrayList.add(CategoryModal(imageTitle, imageUrl))




                        Log.i("DATA ADDED", items.toString())



                    }



                }
                mutableLiveData.value = arrayList

            }
        Log.i("items ki value", "${mutableLiveData}")

        return mutableLiveData
    }

}