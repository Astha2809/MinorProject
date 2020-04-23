import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.minorproject.*

class CategoryListFragmentAdapter(var context: Context) :
    RecyclerView.Adapter<CategoryListFragmentAdapter.ItemViewHolder>() {
    //private var categoryListFragmentAdapter: CategoryListFragmentAdapter? = null
private var catogaryList:List<CategoryModal>?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.catogary_content_list, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount():
            Int = catogaryList?.size?:0


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val categoryModal: CategoryModal? = catogaryList?.get(position)
        if (categoryModal != null) {
            holder.categoryTitleTextView.text = categoryModal.imageTitle
        }

        if (categoryModal != null) {
            Glide.with(context).load(categoryModal.imageUrl).into(holder.categoryImageImageView)
        }

        holder.categoryImageImageView.setOnClickListener(View.OnClickListener {

            holder.categoryTitleTextView.setText("")
            holder.categoryImageImageView.setImageURI(null)
            relodeFragment()
          //  categoryListFragmentAdapter?.notifyDataSetChanged()

        })


    }

    fun setData(catogaryList: List<CategoryModal>) {
        this.catogaryList = catogaryList
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val categoryTitleTextView: TextView =
            itemView.findViewById(R.id.categoryname_textview_categorylist)
        val categoryImageImageView: ImageView = itemView.findViewById(R.id.image_categorylist)


    }

    private fun relodeFragment() {
        val addDetailsFragment = AddDetailsFragment()

        //?why to write activity here?
        val fragmentTransaction =
            (context as MainActivity).supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container, addDetailsFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }


}