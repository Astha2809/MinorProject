package com.example.minorproject.timeline.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.minorproject.R

import com.example.minorproject.timeline.ViewModel.TimelineModel

class TimelineAdapter(var context: Context): RecyclerView.Adapter<TimelineAdapter.ItemViewHolder>() {
    private var timelineImageList: ArrayList<TimelineModel>?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.timeline_content, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount():
            Int= timelineImageList?.size ?:0




    override fun onBindViewHolder(holder:TimelineAdapter.ItemViewHolder, position: Int) {
        val timelineModel:TimelineModel?=timelineImageList?.get(position)
        if (timelineModel != null) {
            holder.timeline_textviewname.text=timelineModel.imageTitle
            holder.timeline_textviewdate.text=timelineModel.imageTime


        }
        if (timelineModel != null) {
            Glide.with(context).load(timelineModel.imageUrl).into(holder.timeline_image)
        }

    }

    fun setTimelineData(timelineImageList:ArrayList<TimelineModel>){
        this.timelineImageList=timelineImageList
        notifyDataSetChanged()


    }

    inner class ItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val timeline_textviewname:TextView=itemView.findViewById(R.id.textview_timeline_name)
        val timeline_textviewdate:TextView=itemView.findViewById(R.id.textview_timeline_date)
        val timeline_image:ImageView=itemView.findViewById(R.id.image_timeline)

    }

}