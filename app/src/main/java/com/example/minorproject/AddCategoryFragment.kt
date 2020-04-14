package com.example.minorproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.add_category_fragment.*

class AddCategoryFragment :Fragment(){


    //lateinit var addButton:FloatingActionButton
    lateinit var rootview:View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview = inflater.inflate(R.layout.add_category_fragment, container, false)
        return rootview


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUi()
    }
    private fun initUi(){
        addbutton_add_categoy.setOnClickListener(View.OnClickListener {
            openAddDetailsFragment()
            Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show()

        })
    }
//funtion to replace addcategorydetails fragment to open new fragment for adding title and image of catogary
    private fun openAddDetailsFragment(){

        val fragment1=AddDetailsFragment()
       // val fragmentManager=activity!!.supportFragmentManager
    //?why to write activity here?
        val fragmentTransaction= activity!!.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container,fragment1)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

//
//        val fragment1=FragmentAddUser()
//        val fragmentTransaction1=supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.main_linear,fragment)
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()

    }



}
