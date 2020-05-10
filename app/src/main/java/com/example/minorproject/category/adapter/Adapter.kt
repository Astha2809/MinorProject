package com.example.minorproject.category.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.minorproject.category.viewmodel.CategoryModel
import com.example.minorproject.MainActivity
import com.example.minorproject.R
import com.example.minorproject.subcategory.ui.DisplaySubCategoryFragment
import com.example.minorproject.subcategory.ui.SubcategoryFragment

class Adapter(var context: Context) :
    RecyclerView.Adapter<Adapter.ItemViewHolder>() {


    private var catogaryList: ArrayList<CategoryModel>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.catogary_content_list, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount():
            Int = catogaryList?.size ?: 0


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val categoryModal: CategoryModel? = catogaryList?.get(position)
        if (categoryModal != null) {

            holder.categoryTitleTextView.text = categoryModal.imageTitle
        }

        if (categoryModal != null) {
            Glide.with(context).load(categoryModal.imageUrl).into(holder.categoryImageImageView)
        }

        holder.categoryImageImageView.setOnClickListener(View.OnClickListener {
            //  clearData()
            // val categoryListFragment=CategoryListFragment()
            val subcategoryfragment = SubcategoryFragment()


            val bundle = Bundle()
            bundle.putString("categoryid", categoryModal?.id.toString())
            Log.i("adapter id", "id" + categoryModal?.id.toString())
            bundle.putString("subcategoryid", categoryModal?.subcategoryid.toString())
            Log.i("adapter id", "id" + categoryModal?.subcategoryid.toString())

            bundle.putString("categoryTitle",categoryModal?.imageTitle).toString()


            subcategoryfragment.arguments = bundle

            val fragmentTransaction =
                (context as MainActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, subcategoryfragment)

            fragmentTransaction.addToBackStack(null)


            fragmentTransaction.commit()


        })
        holder.categoryTitleTextView.setOnClickListener(View.OnClickListener {
            val displaySubCategoryFragment = DisplaySubCategoryFragment()
            val bundle1 = Bundle()
            bundle1.putString("categoryid", categoryModal?.id)
            bundle1.putString("subcategoryid", categoryModal?.subcategoryid.toString())

            bundle1.putString("subcategorytitle", categoryModal?.imageTitle)

            displaySubCategoryFragment.arguments = bundle1
            val fragmentTransaction =
                (context as MainActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, displaySubCategoryFragment)

            fragmentTransaction.addToBackStack(null)


            fragmentTransaction.commit()

        })


    }


    fun setCategoryData(catogaryList: ArrayList<CategoryModel>) {
        this.catogaryList = catogaryList
        notifyDataSetChanged()
    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val categoryTitleTextView: TextView =
            itemView.findViewById(R.id.categoryname_textview_categorylist)
        val categoryImageImageView: ImageView = itemView.findViewById(R.id.image_categorylist)

    }


}