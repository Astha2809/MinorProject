package com.example.minorproject.login


import android.app.ActionBar
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.minorproject.R
import com.example.minorproject.category.ui.CategoryListFragment
import com.example.minorproject.login.ViewModel.LoginViewModel
import com.example.minorproject.login.repo.SignInRepo
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.loginfragment.*

const val EMAIL_SCREEN: Int = 0
const val PASSWORD_SCREEN: Int = 1


class LoginFragment : Fragment() {


    lateinit var snack: Snackbar
    lateinit var rootView: View
    lateinit var mAuth: FirebaseAuth
    var isNewUser: Boolean = false
    var email: String = ""
    var password: String = ""


    lateinit var database: FirebaseFirestore
    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        rootView = inflater.inflate(R.layout.loginfragment, container, false)



        return rootView

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initUi()
        setObservers()


    }


    private fun initUi() {

        loginViewModel = ViewModelProvider(activity!!).get(LoginViewModel::class.java)
        activity?.title = ""
        val navigationView: NavigationView? = view?.findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.isVisible = (false)
        }


        mAuth = FirebaseAuth.getInstance()


        database = FirebaseFirestore.getInstance()


        next.setOnClickListener(View.OnClickListener {
            //setObservers()

            if (loginViewModel.currentScreenVal == EMAIL_SCREEN) {
                email = edittext_email.text.toString()
                loginViewModel.check(email)

            } else if (loginViewModel.currentScreenVal == PASSWORD_SCREEN) {
                password = edittext_email.text.toString().trim()
                if (loginViewModel.isNewUser) {
                    Log.i("email ki vaue", email)
                    Log.i("password ki value", password)




                    loginViewModel.signUp(email, password)
                    loginViewModel.onSuccess1.observe(viewLifecycleOwner, Observer {
                        if (it == true) {

                            moveToNextScreen()
                        }


                    })


                } else {

                    loginViewModel.login(email, password)
                    loginViewModel.onSuccess1.observe(viewLifecycleOwner, Observer {
                        if (it == true) {

                            moveToNextScreen()
                        }


                    })


                }
            }


        })


    }

    private fun setObservers() {

        loginViewModel.getErrMessage().observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            edittext_email.error = it

        })
        loginViewModel.currentScreen.observe(viewLifecycleOwner, Observer {
            setScreen(it)

        })

    }




    private fun setScreen(it: Int) {

        if (it == PASSWORD_SCREEN) {
            edittext_email.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            edittext_email.setHint(R.string.enter_password)

            edittext_email.text.clear()


        }
    }

    private fun moveToNextScreen() {
        val fragment = CategoryListFragment()

        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        if (fragmentTransaction != null) {
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


    }
}


