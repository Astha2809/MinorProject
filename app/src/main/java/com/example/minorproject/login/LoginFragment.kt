package com.example.minorproject.login

//import com.google.firebase.auth.ProviderQueryResult
import android.app.ActionBar
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.minorproject.MainActivity
import com.example.minorproject.R
import com.example.minorproject.category.ui.CategoryListFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.loginfragment.*

const val emailScreen: Int = 0
const val passwordScreen: Int = 1
//const val imageScreen: Int = 2


class LoginFragment : Fragment() {
    // lateinit var editTextEmail: EditText
    lateinit var editTextPassword: EditText
    lateinit var buttonNext: Button
    lateinit var snack:Snackbar
    lateinit var rootView: View
    lateinit var mAuth: FirebaseAuth
    var isNewUser: Boolean = false
    lateinit var email: String
    lateinit var password: String
    var currentScreen = emailScreen
  //  lateinit var sp:SharedPreferences
   lateinit var database:FirebaseFirestore

   // val editTextEmail by lazy { view!!.findViewById<EditText>(R.id.edittext_email) }

    // private var mAuth: FirebaseAuth? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        rootView = inflater.inflate(R.layout.loginfragment, container, false)
           //hidenav()
//        val actionBar: ActionBar? =activity?.actionBar
//        actionBar?.hide()
        //(requireActivity() as MainActivity).supportActionBar!!.hide()

        return rootView

    }

    private fun initUi() {

//        activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)


        mAuth = FirebaseAuth.getInstance()
       // sp=getSharedPreferences("login", Context.MODE_PRIVATE)

        database= FirebaseFirestore.getInstance()


        next.setOnClickListener(View.OnClickListener {
            if (currentScreen == emailScreen) {
                Check()
            } else if (currentScreen == passwordScreen) {
                if (isNewUser) {
                    Log.i("email ki vaue",email)
                    signUp()
                    ////todo goto usernamescreen
                    //forsignup
                } else {
                    signIn()

                }
            }


        })


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initUi()


    }

    fun hidenav(){
        activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        // Set the content to appear under the system bars so that the
        // content doesn't resize when the system bars hide and show.
        or  View.SYSTEM_UI_FLAG_LAYOUT_STABLE
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


        email = edittext_email.text.toString()


        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(OnCompleteListener { task ->


            if (task.isSuccessful) {
                //it returns 1 if emailid exists
                //0 if does not exists
                val aa: Int? = task.getResult()?.signInMethods?.size

                if (aa == 0) {
                    //toas
                    isNewUser = true
                    Log.i("New user hai", "new user")

                } else if (aa == 1) {
                    isNewUser = false
                    Log.i("new user nhi hai", "not a new user")
                }
                Log.i("aa ki value", aa.toString())
                setScreen()

                //Signup()


            } else {
                //signup failed due to some aapi error
                snack= Snackbar.make(linear_loginfragment,"cant signup",Snackbar.LENGTH_LONG)
                snack.setAction("DISMISS",View.OnClickListener {
                    System.out.println("snack clicked")
                })
                snack.show()
                Log.i("signup fail hogya","signup failed")


            }


        })


    }

    private fun signIn() {
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
               // Log.i("login error",task.exception?.message)
                moveToNextScreen()
               // val aa: Int? = task.getResult()?.signInMethods?.size
                //sp.edit().putBoolean("logged",true).apply()
                //Log.i("sp", sp.toString())
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

    }

    private fun signUp() {
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





    }
    private fun addDataTOFirestore(){
        val user= hashMapOf("email" to email)
            database.collection("userdetails")
                //.add(user as Map<String, Any>)
                .document(mAuth.currentUser!!.uid).set(user as Map<String,Any>, SetOptions.merge())
              // database.collection("users").document(mAuth.currentUser!!.uid) .collection("categorynameimages")
        //db.collection("User").document(mAuth.currentUser!!.uid).set(user as Map<String, Any>)
            .addOnCompleteListener { documentReference->
                Log.i("data added", "DocumentSnapshot added with ID")

            }
            .addOnFailureListener { documentRefrence->
                Log.i("data not added", "Error adding document")
            }
    }

    private fun setScreen() {
        if (currentScreen == emailScreen) {
            currentScreen = passwordScreen
            edittext_email.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            edittext_email.setHint(R.string.enter_password)
            edittext_email.text.clear()
        }
    }

private fun moveToNextScreen(){
    val fragment= CategoryListFragment()
    //val fragmentTransaction=supportFragmentManager.
    val fragmentTransaction=activity!!.supportFragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.container,fragment)
    fragmentTransaction.addToBackStack(null)
    fragmentTransaction.commit()

}
}
//val fragment=LoginFragment()
//val fragmentTransaction=supportFragmentManager.beginTransaction()
//fragmentTransaction.replace(R.id.container,fragment)
//fragmentTransaction.addToBackStack(null)
//fragmentTransaction.commit()

