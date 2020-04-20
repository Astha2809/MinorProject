package com.example.minorproject


import CategoryListFragmentAdapter
//import CategoryModal
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.ArrayList
import kotlinx.android.synthetic.main.category_list_fragment.*

class CategoryListFragment : Fragment() {
    lateinit var mAuth: FirebaseAuth
    lateinit var database: FirebaseFirestore
    lateinit var storageRef: StorageReference
    private lateinit var storage: FirebaseStorage
    private var categryList: ArrayList<CategoryModal>? = null
//    lateinit var rootref:FirebaseStorage
//   lateinit var storageref:FirebaseStorage
    //lateinit var categoryViewModel: CategoryViewModel

    //private var imageList = ArrayList<CategoryModal>()
    //val addCategoryFragmentAdapter: AddCategoryFragmentAdapter
    //lateinit var addButton:FloatingActionButton
    lateinit var rootview: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview = inflater.inflate(R.layout.category_list_fragment, container, false)
        return rootview


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        initUi()
//    }


    private fun initUi() {

        database = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference.child("categorynameimages")



        recycler.layoutManager = GridLayoutManager(this.context, 2)
        recycler.setHasFixedSize(true)
        categryList = ArrayList()
        categryList = loadImages()
        val categoryListFragmentAdapter =
            context?.let { CategoryListFragmentAdapter(it, categryList!!) }
        recycler.adapter = categoryListFragmentAdapter


        //loadImages()
        //var user=mAuth.currentUser

        //getSavedDetails()
//        categoryViewModel=ViewModelProvider(activity!!).get(CategoryViewModel::class.java)
//        categoryViewModel.getSavedDetails()


        addbutton_add_categoy.setOnClickListener(View.OnClickListener {
            openAddDetailsFragment()
            Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()

        })
    }


    //funtion to replace addcategorydetails fragment to open new fragment for adding title and image of catogary
    private fun openAddDetailsFragment() {


        val fragment1 = AddDetailsFragment()

        //?why to write activity here?
        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment1)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()


    }

    private fun loadImages(): ArrayList<CategoryModal> {
        //imageList= ArrayList()
        var items: ArrayList<CategoryModal> = ArrayList()

        database.collection("categorynameimages")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        var imageTitle = document.data.get("categorytitle").toString()
                        var imageUrl = document.data.get("categorynameimage").toString()
                        Log.i("imagetitle", imageTitle)
                        Log.i("imageurl", imageUrl)

                        val bb = items.add(CategoryModal(imageTitle, imageUrl))
                        Log.i("bb ki value", bb.toString())
                    }
                    // val addCategoryFragmentAdapter= context?.let { AddCategoryFragmentAdapter(it, categryList!!) }
                    //recycler.adapter=addCategoryFragmentAdapter


                }

            }


        return items
    }
//fun getSavedDetails():CollectionReference{
//    val collectionReference=database.collection("categorynameimages")
//        return collectionReference


}
