package com.example.minorproject.timeline.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.FirebaseRepository

class TimelineViewModel:ViewModel(){
    var firebaseRepository=FirebaseRepository()
    var timelineData:MutableLiveData<ArrayList<TimelineModel>> = MutableLiveData()

init {
    loadTimelineData()
}
    fun loadTimelineData():LiveData<ArrayList<TimelineModel>>{
        timelineData=firebaseRepository.loadtimelineData()


        return timelineData
    }

}