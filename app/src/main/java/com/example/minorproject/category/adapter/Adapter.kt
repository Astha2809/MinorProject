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
import com.example.minorproject.subcategory.ui.SubcategoryFragment

class Adapter(var context: Context) :
    RecyclerView.Adapter<Adapter.ItemViewHolder>() {
    //private var categoryListFragmentAdapter: CategoryListFragmentAdapter? = null
    // private var layoutBackground:RelativeLayout?=null

    private var catogaryList: ArrayList<CategoryModel>? = null
   // private var subCategoryList:ArrayList<SubCategoryModel>? =null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.catogary_content_list, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount():
            Int = catogaryList?.size ?: 0
//subcategoryLIst ka size b lena hai

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val categoryModal: CategoryModel? = catogaryList?.get(position)
        if (categoryModal != null) {
            holder.categoryTitleTextView.text = categoryModal.imageTitle
        }

        if (categoryModal != null) {
            Glide.with(context).load(categoryModal.imageUrl).into(holder.categoryImageImageView)
        }
//
        holder.categoryImageImageView.setOnClickListener(View.OnClickListener {
            //  clearData()
            val fragment = SubcategoryFragment()

            val bundle = Bundle()
            bundle.putString("categoryid", categoryModal?.id)
            Log.i("adapter id", "id" + categoryModal?.id)

            fragment.arguments = bundle

            val fragmentTransaction =
                (context as MainActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.addToBackStack(null)


            fragmentTransaction.commit()


        })

       // if condition lagao

//        val subCategoryModel:SubCategoryModel?=subCategoryList?.get(position)
//        if (subCategoryModel!=null){
//           Glide.with(context).load(subCategoryModel.subCategoryImageUrl).into(holder.categoryImageImageView)
//        }


    }

    fun setCategoryData(catogaryList: ArrayList<CategoryModel>) {
        this.catogaryList = catogaryList
        notifyDataSetChanged()
    }
//    fun setSubCategoryData(subCategoryList:ArrayList<CategoryModel>){
//        this.subCategoryList=subCategoryList
//        notifyDataSetChanged()
//    }

    fun clearData() {
        this.catogaryList?.clear()
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val categoryTitleTextView: TextView =
            itemView.findViewById(R.id.categoryname_textview_categorylist)
        val categoryImageImageView: ImageView = itemView.findViewById(R.id.image_categorylist)

        // var layoutBackground:RelativeLayout=itemView.findViewById(R.id.catogary_content_list)

    }

//    private fun openSubCategoryFragment() {
//
//
//        val fragment = SubcategoryFragment()
//
//        //val fragmentTransaction=supportFragmentManager.
//        val fragmentTransaction =
//            (context as MainActivity).supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.container, fragment)
//        fragmentTransaction.addToBackStack(null)
//
//
//        fragmentTransaction.commit()
//
//
//    }


}