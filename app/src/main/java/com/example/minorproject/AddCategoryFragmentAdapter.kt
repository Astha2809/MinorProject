////import android.content.Context
////import android.view.LayoutInflater
////import android.view.View
////import android.view.ViewGroup
////import android.widget.ImageView
////import android.widget.TextView
////import androidx.recyclerview.widget.RecyclerView
////import com.bumptech.glide.Glide
////import com.example.minorproject.AddCategoryFragment
////import com.example.minorproject.CategoryModal
////import com.example.minorproject.R
////
////
////
////class AddCategoryFragmentAdapter(private val catogaryList: AddCategoryFragment): RecyclerView.Adapter<AddCategoryFragmentAdapter.ItemViewHolder>() {
////    lateinit var categoryTitleTextView: TextView
////    lateinit var categoryImageImageView: ImageView
////   // private var catogaryList:List<AddCategoryFragment> = mutableListOf()
////          //lateinit var categoryModal:CategoryModal
////
////    private var context: Context? = null
////
////
////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
////        context = parent.context
////        val itemView=
////            LayoutInflater.from(parent.context).inflate(R.layout.catogary_list,parent,false)
////        return ItemViewHolder(itemView)
////    }
////
////    override fun getItemCount():
////        Int = catogaryList.size
////
////
////
////    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//////val current=catogaryList[position]
////
////        holder.bind()
////    }
////   inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
////       fun bind() {
////           categoryTitleTextView = itemView.findViewById(R.id.categoryname_textview_categorylist)
////           categoryImageImageView = itemView.findViewById(R.id.image_categorylist)
////          // val image=catogaryList.get(adapterPosition)
////           val categoryModal=catogaryList.get(adapterPosition)
////           //Load image
////          // Glide.with(this).load
////          // Glide.with(AddCategoryFragmentAdapter).load(image.getSavedDetails()).into(categoryImageImageView)
////          // categoryTitleTextView.text=categoryModal.imgTitle
////           //categoryImageImageView.setImageURI(categoryModal.imgTitle)
////           //Glide.with().load(categoryModal.image).into(categoryImageImageView)
////           categoryTitleTextView.text=categoryModal.imageTitle
////            Glide.with(context!!).load(categoryModal.imageUrl).into(categoryImageImageView)
////
////       }
////
////
////
////    }
////
////}
//
//
//package com.example.minorproject
//
//import android.graphics.Bitmap
//import android.media.Image
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import kotlinx.android.synthetic.main.catogary_list.view.*
//
//class AddCategoryFragmentAdapter: RecyclerView.Adapter<AddCategoryFragmentAdapter.MyViewHolder>() {
//    private var catogaryList:List<AddCategoryFragment> = mutableListOf()
//
//
//
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.catogary_list,parent,false)
//        return MyViewHolder(itemView)
//    }
//
//    override fun getItemCount():
//            Int = catogaryList.size
//
//
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//
//    }
//
//    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private var pos:Int=0
////        fun setData(categorytitle:String,category:Bitmap,position:Int){
////            itemView.categoryname_textview_categorylist.text=categorytitle
////            itemView.image_categorylist.image_categorylist.setImageBitmap(Bitmap)=category
////
////        }
//
//    }
//
//
//}
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.minorproject.AddCategoryFragment
import com.example.minorproject.CategoryModal
//import com.example.minorproject.CategoryModal
import com.example.minorproject.R

class AddCategoryFragmentAdapter(var context: Context,var catogaryList: List<CategoryModal>) :
    RecyclerView.Adapter<AddCategoryFragmentAdapter.ItemViewHolder>() {
    lateinit var categoryTitleTextView: TextView
    lateinit var categoryImageImageView: ImageView
    // private var catogaryList:List<AddCategoryFragment> = mutableListOf()
    //lateinit var categoryModal:CategoryModal

    //private var context: Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.catogary_list, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount():
            Int = catogaryList.size


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val categoryModal:CategoryModal = catogaryList[position]
      holder.categoryTitleTextView.text=categoryModal.imageTitle

        Glide.with(context).load(categoryModal.imageUrl).into(holder.categoryImageImageView)

        holder.categoryImageImageView.setOnClickListener(View.OnClickListener {
            movetonewfragment()
        })



    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


val categoryTitleTextView:TextView = itemView.findViewById(R.id.categoryname_textview_categorylist)
 val categoryImageImageView:ImageView= itemView.findViewById(R.id.image_categorylist)

    }

    private fun movetonewfragment(){

    }

}