package vanderclay.comet.benson.franticsearch.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import vanderclay.comet.benson.franticsearch.R

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var mEmailView: TextView? = null

    private var mPasswordView: TextView? = null

    // Reference to the firebase authorization object
    private var mAuth: FirebaseAuth? = null

    // Reference to the Sign in Button
    private var signInButton: Button? = null

    // Reference to the firebase Auth State Changed listener
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null

    //The tag filter
    private val loginActivityTag: String = "LoginActivity"

    //Reference to the create account Button
    private var createAccountButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Instantiate a Reference to the Firebase Auth Object
        this.mAuth = FirebaseAuth.getInstance()
        mEmailView = findViewById(R.id.email)
        mPasswordView = findViewById(R.id.password)
        signInButton = findViewById(R.id.email_sign_in_button)
        signInButton?.setOnClickListener(this)
        createAccountButton = findViewById(R.id.create_account_button)
        createAccountButton?.setOnClickListener(this)

        Log.w(loginActivityTag, "Assigning listener to Firebase Authorize object")
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                Log.w(loginActivityTag, "user sign in successful")
            } else {
                Log.w(loginActivityTag, "User not currently signed in.")
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        Log.w(loginActivityTag, "Application On Create called: Assigning Auth State Listener")
        mAuth!!.addAuthStateListener(this.mAuthListener!!)
    }

    /*
     * Override the onStop method and
     */
    public override fun onStop() {
        Log.w(loginActivityTag, "Stopping the task.")
        super.onStop()
        if (mAuthListener != null) {
            Log.w(loginActivityTag, "stop listening for changes in the login activity")
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }


    private fun signIn(email: String, password: String) {
        Log.w(loginActivityTag, "signIn: $email")
        val intent = Intent(baseContext, MainActivity::class.java)
        intent.action = "SEARCH_INTENT"
        if(!isValideForm()){
            showSnackBar("Password or Email had Invalid Format")
            return
        }
        signInButton?.isEnabled = false
        mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.w(loginActivityTag, "Sign In Complete" + task.isSuccessful)
                showSnackBar("Successfully Signed In")
                startActivity(intent)
                finish()
            } else {
                signInButton?.isEnabled = true
                showSnackBar("Login Unsuccessful")
            }
        }
    }

    private fun transferToCreateAccount(){
        val intent = Intent(this, CreateAccountActivity::class.java)
        super.startActivity(intent)
    }

    private fun showSnackBar(message: String){
        val snackbar = Snackbar.make(LoginActivity, message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    private fun isValideForm(): Boolean{
        val emailText = mEmailView?.text.toString()
        val passwordText = mPasswordView?.text.toString()
        if(!isEmailValid(emailText) || !isPasswordValid(passwordText)){
            return false
        }
        return true
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.email_sign_in_button) {
            signIn(mEmailView?.text.toString(), mPasswordView?.text.toString())
        }
        else if(i == R.id.create_account_button){
            transferToCreateAccount()
        }
    }

/*
    private fun signOut() {
        mAuth!!.signOut()
    }
*/

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 6
    }
}

