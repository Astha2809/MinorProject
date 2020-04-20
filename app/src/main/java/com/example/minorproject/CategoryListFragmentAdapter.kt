import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.minorproject.CategoryModal

import com.example.minorproject.R

class CategoryListFragmentAdapter(var context: Context, var catogaryList: List<CategoryModal>) :
    RecyclerView.Adapter<CategoryListFragmentAdapter.ItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.catogary_content_list, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount():
            Int = catogaryList.size


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val categoryModal: CategoryModal = catogaryList[position]
        holder.categoryTitleTextView.text = categoryModal.imageTitle

        Glide.with(context).load(categoryModal.imageUrl).into(holder.categoryImageImageView)

        holder.categoryImageImageView.setOnClickListener(View.OnClickListener {
            movetonewfragment()
        })


    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val categoryTitleTextView: TextView =
            itemView.findViewById(R.id.categoryname_textview_categorylist)
        val categoryImageImageView: ImageView = itemView.findViewById(R.id.image_categorylist)

    }

    private fun movetonewfragment() {

    }

}