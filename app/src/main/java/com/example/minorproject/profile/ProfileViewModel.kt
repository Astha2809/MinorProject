package com.example.minorproject.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.FirebaseRepository

//class ProfileViewModel:ViewModel(){
//    var firebaseRepository= FirebaseRepository()
//    var userData: MutableLiveData<List<ProfileModel>> = MutableLiveData()
//
//    init {
//        loadUserData()
//    }
//
//    fun loadUserData(): LiveData<List<ProfileModel>>{
//        userData=firebaseRepository.loadUserData()
//        return userData
//    }
//
//}