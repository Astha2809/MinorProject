package com.example.minorproject.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.minorproject.R
import kotlinx.android.synthetic.main.display_user_details_fragment.*

class DisplayUserDetailsFragment : Fragment() {
    lateinit var rootView: View


    lateinit var profileViewModel: ProfileViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.display_user_details_fragment, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        activity?.title = "My Profile"

        profileViewModel = ViewModelProvider(activity!!).get(ProfileViewModel::class.java)


        profileViewModel.loadData().observe(viewLifecycleOwner, Observer {


            textview_profile_email.setText(it.email1)
            textview_profile_name.setText(it.name1)

            Glide.with(this).load(it.image1).apply(RequestOptions.circleCropTransform())
                .into(image_user_profile)


        })


    }
}