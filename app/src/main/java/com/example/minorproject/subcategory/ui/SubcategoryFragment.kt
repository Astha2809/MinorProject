package com.example.minorproject.subcategory.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.minorproject.R
import com.example.minorproject.category.adapter.Adapter
import com.example.minorproject.category.viewmodel.CategoryModel
import com.example.minorproject.category_detail.AddCategoryFragment
import com.example.minorproject.category_detail.AddSubCategoryFragment

import com.example.minorproject.subcategory.viewmodel.SubCategoryViewModel
import kotlinx.android.synthetic.main.category_list_fragment.*
import kotlinx.android.synthetic.main.sub_category_fragment.*
import java.util.ArrayList

class SubcategoryFragment : Fragment() {

    lateinit var rootview: View

    var categoryId: String? = null
    var subcategoryId: String? = null
    var adapter: Adapter? = null
    lateinit var subCategoryViewModel: SubCategoryViewModel
    private lateinit var subCategryList: LiveData<ArrayList<CategoryModel>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview = inflater.inflate(R.layout.sub_category_fragment, container, false)
        return rootview


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        adapter = context?.let {
            Adapter(it)
        }
        subcategory_recycler.layoutManager = GridLayoutManager(this.context, 2)
        subcategory_recycler.setHasFixedSize(true)


        categoryId = arguments?.getString("categoryid")
        Log.i("subcat id", "id" + categoryId)

        subcategoryId = arguments?.getString("subcategoryid")
        Log.i("subcat id2", "id" + subcategoryId)


        subCategoryViewModel = ViewModelProvider(activity!!).get(SubCategoryViewModel::class.java)
        subCategryList =
            subCategoryViewModel.loadSubCategoryToRecycler(categoryId!!, subcategoryId!!)
        subcategory_recycler.adapter = adapter


        subCategoryViewModel.subCategoryData.observe(viewLifecycleOwner, Observer
        { list -> list.let { adapter?.setCategoryData(it) } })

        addbutton_add_subcategoy.setOnClickListener(View.OnClickListener {
            openAddSubCategoryFragment()

        })


    }

    private fun openAddSubCategoryFragment() {

        //arguments?.putString("categoryid", categoryId)

        Log.i("subcat id", "id" + categoryId)

        val addSubCategoryFragment = AddSubCategoryFragment()
        var bundle=Bundle()
        bundle.putString("categoryid", categoryId)
        addSubCategoryFragment.arguments=bundle
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        if (fragmentTransaction != null) {
            fragmentTransaction.add(R.id.container, addSubCategoryFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }

}