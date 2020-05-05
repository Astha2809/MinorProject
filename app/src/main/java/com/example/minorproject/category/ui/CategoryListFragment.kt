package com.example.minorproject.category.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.minorproject.*
import com.example.minorproject.category.adapter.Adapter
import com.example.minorproject.category.viewmodel.CategoryModel
import com.example.minorproject.category.viewmodel.CategoryViewModel
import com.example.minorproject.category_detail.AddCategoryFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.ArrayList
import kotlinx.android.synthetic.main.category_list_fragment.*

const val categoryListScreen: Int = 0
const val subCategoryListScreen: Int = 1

class CategoryListFragment : Fragment() {
    lateinit var mAuth: FirebaseAuth
    lateinit var database: FirebaseFirestore
    lateinit var storageRef: StorageReference
    private lateinit var storage: FirebaseStorage
    lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categryList: LiveData<ArrayList<CategoryModel>>
    var currentScreen = categoryListScreen
    var isCategory: Boolean = true
    var adapter: Adapter? = null




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


    private fun initUi() {

        database = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        adapter = context?.let {
            Adapter(
                it
            )
        }
        recycler.layoutManager = GridLayoutManager(this.context, 2)
        recycler.setHasFixedSize(true)
        categoryViewModel = ViewModelProvider(activity!!).get(CategoryViewModel::class.java)
        categryList = categoryViewModel.categoryData



        arguments?.putBoolean("isCategory", isCategory)
        Log.i("is cat value", isCategory.toString())


        arguments?.let {
            isCategory = it.getBoolean("isCategory")

        }


        // categryList=categoryViewModel.getSavedDetails()

//            /if(isCategory){
//               // categryList = loadImages()
//                //categryList=categoryViewModel.getSavedDetails()
//            }
//            else{
//                categryList=loadSubCategoryImages()
//            }
//            val categoryListFragmentAdapter =
//                context?.let { CategoryListFragmentAdapter(it, categryList!!) }
        recycler.adapter = adapter



        categoryViewModel.categoryData.observe(
            viewLifecycleOwner,
            Observer<ArrayList<CategoryModel>> { list ->
                list?.let {
                    adapter?.setCategoryData(it)
                }
            })
        addbutton_add_categoy.setOnClickListener(View.OnClickListener {
            openAddDetailsFragment()
            Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()


        })
//        image_categorylist.setOnClickListener(View.OnClickListener {
//            catogary_content_list.visibility = View.INVISIBLE
//        })


    }


    //funtion to replace addcategorydetails fragment to open new fragment for adding title and image of catogary
    fun openAddDetailsFragment() {
        val addDetailsFragment = AddCategoryFragment()
        //?why to write activity here?
        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container, addDetailsFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()


    }



}
