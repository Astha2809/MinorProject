package com.example.minorproject.login

//import com.google.firebase.auth.ProviderQueryResult
import android.app.ActionBar
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.minorproject.R
import com.example.minorproject.category.ui.CategoryListFragment
import com.example.minorproject.login.ViewModel.LoginViewModel
import com.example.minorproject.repo.SignInRepo
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.loginfragment.*

const val EMAIL_SCREEN: Int = 0
const val PASSWORD_SCREEN: Int = 1
//const val imageScreen: Int = 2


class LoginFragment : Fragment() {


    lateinit var snack: Snackbar
    lateinit var rootView: View
    lateinit var mAuth: FirebaseAuth
    var isNewUser: Boolean = false
     var email: String=""
     var password: String=""
    var signInRepo = SignInRepo()
    //var currentScreen = emailScreen

    lateinit var database: FirebaseFirestore
    private val mViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        rootView = inflater.inflate(R.layout.loginfragment, container, false)
        //hidenav()
      val actionBar: ActionBar? =activity?.actionBar
//        actionBar?.hide()
        //(requireActivity() as MainActivity).supportActionBar!!.hide()

        return rootView

    }




    private fun initUi() {

//        activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)


        mAuth = FirebaseAuth.getInstance()


        database = FirebaseFirestore.getInstance()


        next.setOnClickListener(View.OnClickListener {
            //setObservers()

            if (mViewModel.currentScreenVal == EMAIL_SCREEN) {
                 email=edittext_email.text.toString()
                mViewModel.check(email)

            } else if (mViewModel.currentScreenVal == PASSWORD_SCREEN) {
                password = edittext_email.text.toString().trim()
                if (mViewModel.isNewUser) {
                    Log.i("email ki vaue", email)
                    Log.i("password ki value", password)
                    //signUp()

                    view?.let { it1 -> mViewModel.signUp(email, password, it1) }
                    //dataaddkamethodcall
//                    signInRepo.onSuccess.observe(viewLifecycleOwner, Observer {
//
//                        Log.i("it ki vaue", it.toString())
//                        if (it == true){
//                            moveToNextScreen()
//                        }
//
//                    })
                        //moveToNextScreen()


                    //forsignup
                } else {
                    //signIn()
                    view?.let { it1 -> mViewModel.login(email, password, it1) }
//                    signInRepo.onSuccess.observe(viewLifecycleOwner, Observer {
//                        Log.i("it", it.toString())
//                        if (it == true){
//                            moveToNextScreen()
//                        }
//
//
//                    })

                   // moveToNextScreen()

                }
            }


        })



    }
    private fun setObservers() {
        mViewModel.getErrMessage().observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            //showToast(it)
        })
        mViewModel.currentScreen.observe(viewLifecycleOwner,Observer{
            setScreen(it)

        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initUi()
        setObservers()



    }

    //fun abc(){
//   mViewModel.signUp(email,password)
//}
    fun hidenav() {
        activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                // or  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)


    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initUi()
//    }


    private fun Check() {
//        edittext_email.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
//        edittext_email.setHint(R.string.enter_password)
           //setObservers()
        email = edittext_email.text.toString()



        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(OnCompleteListener { task ->


            if (task.isSuccessful) {
                //it returns 1 if emailid exists
                //0 if does not exists

                val aa: Int? = task.getResult()?.signInMethods?.size

                if (aa == 0) {

                    isNewUser = true
                    Log.i("New user hai", "new user")

                } else if (aa == 1) {
                    isNewUser = false
                    Log.i("new user nhi hai", "not a new user")
                }
                Log.i("aa ki value", aa.toString())
               // setScreen(it)

                //Signup()


            } else {
                //signup failed due to some aapi error
                snack = Snackbar.make(linear_loginfragment, "cant signup", Snackbar.LENGTH_LONG)
                snack.setAction("DISMISS", View.OnClickListener {
                    System.out.println("snack clicked")
                })
                snack.show()
                Log.i("signup fail hogya", "signup failed")


            }


        })


    }

    /*private fun signIn() {
//        email = edittext_email.text.toString()
        password = edittext_email.text.toString().trim()
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                snack= Snackbar.make(linear_loginfragment,"LOggedIn",Snackbar.LENGTH_LONG)
                snack.setAction("DISMISS",View.OnClickListener {
                    System.out.println("snack clicked")
                })
                snack.show()
                Log.i("loged in", "loged in again")
                Log.i("password",password)

                moveToNextScreen()

            }

            else {

                snack= Snackbar.make(linear_loginfragment,"cant log in",Snackbar.LENGTH_LONG)
                snack.setAction("DISMISS",View.OnClickListener {
                    System.out.println("snack clicked")
                })
                snack.show()
                Log.i("not loged in", "not loged in again")
                Log.i("password",password)
                Log.i("login error",task.exception?.message)
               // moveToNextScreen()


            }
        }

    }*/

    /* private fun signUp() {
        // email = edittext_email.text.toString().trim()


         password = edittext_email.text.toString().trim()
         mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
             if (task.isSuccessful) {
                 snack= Snackbar.make(linear_loginfragment,"signup",Snackbar.LENGTH_LONG)
                 snack.setAction("DISMISS",View.OnClickListener {
                     System.out.println("snack clicked")
                 })
                 snack.show()
                 addDataTOFirestore()
                 moveToNextScreen()
                 Log.i("signup hogya", "signup successfully")
             } else {
                 snack= Snackbar.make(linear_loginfragment,"cant signup",Snackbar.LENGTH_LONG)
                 snack.setAction("DISMISS",View.OnClickListener {
                     System.out.println("snack clicked")
                 })
                 snack.show()

                 Log.i("nhi hua signup", "not signup")
                 Log.e("error",task.exception?.message)
                // moveToNextScreen()
             }

         }





     }*/

    /* private fun addDataTOFirestore(){
         val user= hashMapOf("email" to email)
             database.collection("userdetails")

                 .document(mAuth.currentUser!!.uid).set(user as Map<String,Any>, SetOptions.merge())

             .addOnCompleteListener { documentReference->
                 Log.i("data added", "DocumentSnapshot added with ID")

             }
             .addOnFailureListener { documentRefrence->
                 Log.i("data not added", "Error adding document")
             }
     }*/

    private fun setScreen(it: Int) {

           if(it== PASSWORD_SCREEN) {
               edittext_email.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            edittext_email.setHint(R.string.enter_password)

            edittext_email.text.clear()
            //password = edittext_email.text.toString().trim()
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


