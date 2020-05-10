package com.example.minorproject.subcategory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.minorproject.MainActivity
import com.example.minorproject.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.display_subcategory_fragment.*


class DisplaySubCategoryFragment : Fragment() {
    lateinit var rootView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.display_subcategory_fragment, container, false)
        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initUi() {

        var title = arguments?.getString("subcategorytitle")
        var catid = arguments?.getString("categoryid")
        var subcatid = arguments?.getString("subcategoryid")

        activity?.title = title

        val db = FirebaseFirestore.getInstance().collection("Subcategory").document(catid!!)

        val documentReference = db.collection("SubcategoryImages").document(subcatid!!)
        documentReference.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->

            val imageUrl: String? = documentSnapshot?.getString("subcategoryurl")


            Glide.with(this).load(imageUrl).into(imageview_display_subcategory)
        }

        delete_display_subcategory.setOnClickListener(View.OnClickListener {

            FirebaseFirestore.getInstance().collection("Subcategory").document(catid)
                .collection("SubcategoryImages").document(subcatid).delete()
                .addOnCompleteListener {
                    FirebaseFirestore.getInstance().collection("Timeline").document(subcatid)
                        .delete().addOnCompleteListener {
                            //arguments?.putString("categoryid",catid)
                            //arguments?.putString("subcategoryid",subcatid)
                            val subcategoryfragment = SubcategoryFragment()

                            val bundle = Bundle()
                            bundle.putString("categoryid", catid)
                            bundle.putString("subcategoryid", subcatid)

                            subcategoryfragment.arguments = bundle
                            val fragmentTransaction =
                                (context as MainActivity).supportFragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.container, subcategoryfragment)
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()


                        }
                }

        })


    }
}