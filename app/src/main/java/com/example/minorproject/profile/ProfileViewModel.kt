package com.example.minorproject.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minorproject.common.FirebaseRepository
import com.example.minorproject.login.ViewModel.LoginModel

class ProfileViewModel: ViewModel(){
    var firebaseRepository= FirebaseRepository()
var userData: MutableLiveData<LoginModel> = MutableLiveData()


fun loadData(): MutableLiveData<LoginModel> {

    userData = firebaseRepository.loadUserData()
    Log.i("user", userData.toString())
    return userData

}

}