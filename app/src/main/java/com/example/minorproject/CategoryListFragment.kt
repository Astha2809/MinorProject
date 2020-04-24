package com.example.minorproject


//import CategoryListFragmentAdapter
//import CategoryModal
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.ArrayList
import kotlinx.android.synthetic.main.category_list_fragment.*

const val categoryListScreen:Int=0
const val subCategoryListScreen:Int=1

class CategoryListFragment : Fragment() {
    lateinit var mAuth: FirebaseAuth
    lateinit var database: FirebaseFirestore
    lateinit var storageRef: StorageReference
    private lateinit var storage: FirebaseStorage
    private lateinit var categryList: LiveData<ArrayList<CategoryModal>>
    var currentScreen= categoryListScreen
    var isCategory:Boolean = true
     var categoryListFragmentAdapter:CategoryListFragmentAdapter? =null

    //    lateinit var rootref:FirebaseStorage
//   lateinit var storageref:FirebaseStorage
    lateinit var categoryViewModel: CategoryViewModel

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




    private fun initUi() {

        database = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        categoryListFragmentAdapter= context?.let { CategoryListFragmentAdapter(it) }
        storageRef = storage.reference.child("categorynameimages")
        recycler.layoutManager = GridLayoutManager(this.context, 2)
        recycler.setHasFixedSize(true)

            arguments?.putBoolean("isCategory",isCategory)
        Log.i("is cat value", isCategory.toString())


            //arguments?.let{
            //  isCategory= it.getBoolean("isCategory")
            //categryList = ArrayList()

       // var categoryViewModel=ViewModelProvider.of()
        categoryViewModel=ViewModelProvider(activity!!).get(CategoryViewModel::class.java)

        categryList=categoryViewModel.loadImages()

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
            recycler.adapter = categoryListFragmentAdapter







            addbutton_add_categoy.setOnClickListener(View.OnClickListener {
                openAddDetailsFragment()
                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()

            })
        //ViewModel

      categoryViewModel.allData.observe(viewLifecycleOwner, Observer<ArrayList<CategoryModal>>{ list->
            list.let {
                categoryListFragmentAdapter?.setData(it)
            }
        })

        }



    //funtion to replace addcategorydetails fragment to open new fragment for adding title and image of catogary
    private fun openAddDetailsFragment() {
        val addDetailsFragment = AddDetailsFragment()
        //?why to write activity here?
        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container,addDetailsFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()


    }

   /* private fun loadImages(): ArrayList<CategoryModal> {
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
    }*/
//fun getSavedDetails():CollectionReference{
//    val collectionReference=database.collection("categorynameimages")
//        return collectionReference


    /*private fun loadSubCategoryImages():ArrayList<CategoryModal>{
    var subCatItems:ArrayList<CategoryModal> =ArrayList()
    database.collection("categorynameimages").document().collection("subcategoryimages")
        .get()
        .addOnCompleteListener { task ->
            if(task.isSuccessful){
                for(document in task.result!!){
                    var subCatImgTitle=document.data.get("subcattitle").toString()
                    var subCatImgUrl=document.data.get("subcatname").toString()
                    val cc=subCatItems.add(CategoryModal(subCatImgTitle,subCatImgUrl))
                }
            }
        }
    return subCatItems
}*/


}
