package com.example.minorproject

import android.graphics.Bitmap
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.catogary_list.view.*

class AddCategoryFragmentAdapter: RecyclerView.Adapter<AddCategoryFragmentAdapter.MyViewHolder>() {
    private var catogaryList:List<AddCategoryFragment> = mutableListOf()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.catogary_list,parent,false)
        return MyViewHolder(itemView)
          }

    override fun getItemCount():
            Int = catogaryList.size



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var pos:Int=0
//        fun setData(categorytitle:String,category:Bitmap,position:Int){
//            itemView.categoryname_textview_categorylist.text=categorytitle
//            itemView.image_categorylist.image_categorylist.setImageBitmap(Bitmap)=category
//
//        }

    }


}

