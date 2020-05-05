package com.example.minorproject.timeline.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minorproject.R
import com.example.minorproject.timeline.ViewModel.TimelineModel
import com.example.minorproject.timeline.ViewModel.TimelineViewModel
import com.example.minorproject.timeline.adapter.TimelineAdapter
import kotlinx.android.synthetic.main.timeline_fragment.*

class TimelineFragment :Fragment(){
    lateinit var rootView: View
    var timelineAdapter:TimelineAdapter?=null
    var timelineViewModel:TimelineViewModel?=null
    private lateinit var timelineList: LiveData<ArrayList<TimelineModel>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.timeline_fragment, container, false)
        return rootView
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        initUi()
//        super.onActivityCreated(savedInstanceState)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initUi(){
       timelineAdapter=context?.let { TimelineAdapter(it) }
        timeline_recycler.layoutManager=LinearLayoutManager(this.context)
        timeline_recycler.setHasFixedSize(true)
        timelineViewModel=ViewModelProvider(this).get(TimelineViewModel::class.java)
        //timelineList= timelineViewModel!!.timelineData
        timeline_recycler.adapter=timelineAdapter


        timelineViewModel?.timelineData?.observe(viewLifecycleOwner, Observer {
                list->list.let{timelineAdapter?.setTimelineData((it))} })



    }
}