package vanderclay.comet.benson.franticsearch.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import vanderclay.comet.benson.franticsearch.R

class CreateAccountActivity : AppCompatActivity(), View.OnClickListener {

    private var mAuth: FirebaseAuth? = null

    private var mAuthListener: FirebaseAuth.AuthStateListener? = null

    private var passwordTextView: TextView? = null

    private var verifyPasswordTextView: TextView? = null

    private var createAccountButton: Button? = null

    private var emailTextView: TextView? = null

    private var createAccountTag: String = "CreateAccountActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        passwordTextView = findViewById(R.id.password)
        verifyPasswordTextView = findViewById(R.id.retypePassword)
        createAccountButton = findViewById(R.id.createAccountButton)
        emailTextView = findViewById(R.id.email)
        createAccountButton?.setOnClickListener(this)

        //Firebase Setup
        this.mAuth = FirebaseAuth.getInstance()

        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user: FirebaseUser? = firebaseAuth.currentUser
            if (user != null) {
                Log.w(createAccountTag, "user create account successful")
            } else {
                Log.w(createAccountTag, "user signed out")
            }
        }
    }

    public override fun onResume() {
        super.onResume()
    }

    /*
     * Override the onStop method and Detach the auth state listener for the class.
     */
    public override fun onStop() {
        Log.w(createAccountTag, "Stopping the task.")
        super.onStop()
        if (mAuthListener != null) {
            Log.w(createAccountTag, "stop listening for changes in the login activity")
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }

    public override fun onStart() {
        super.onStart()
        Log.w(createAccountTag, "Application has been started, assign the listener for auth state changes")
        mAuth!!.addAuthStateListener(this.mAuthListener!!)
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@")
    }

    /*
      * Verifies that a users password contains an upper case letter, is 6 characters long
      * and at least one of those characters is a number.
     */
    private fun isValidPassword(email: String): Boolean {
        var hasUpper = false
        var hasNumber = false
        if (email.length > 6) {
            for (character: Char in email) {
                if (character.isUpperCase()) {
                    hasUpper = true
                } else if (character.isDigit()) {
                    hasNumber = true
                }
            }
            return hasUpper && hasNumber
        }
        return false
    }

    /*
     * verifies that two strings are equal... because it's easier to read nothing more
     * Compares left hand side and right hand side to make sure they're the same...
     */
    private fun samePassword(lhs: String, rhs: String): Boolean {
        return lhs == rhs
    }

    private fun showSnackBar(message: String) {
        val smacker = Snackbar.make(findViewById(R.id.CreateAccountActivity), message, Snackbar.LENGTH_LONG)
        smacker.show()
    }

    /*
     * Nothing like a bunch of rested ifs to make you want to die a little inside.
     * Oh well, it's 10:37 at night...
     */
    private fun checkCreateAccountParameters(email: String, verifyPassword: String, password: String): Boolean {
        if (isValidPassword(password) && isValidPassword(verifyPassword)) {
            if (isValidEmail(email)) {
                //congrats you can type like a decent human being
                return if (samePassword(verifyPassword, password)) {
                    true
                } else {
                    showSnackBar("passwords don't match")
                    false
                }
            }
            showSnackBar("Check Email Format")
            return false
        }
        showSnackBar("Password Not Strong Enough")
        return false
    }

    private fun createAccount(email: String, verifyPassword: String, password: String) {
        if (checkCreateAccountParameters(email, verifyPassword, password))
            mAuth?.createUserWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener(this
                    ) { task ->
                        if (task.isSuccessful) {
                            showSnackBar("Account Successfully Created")
                        } else if (!task.isSuccessful) {
                            showSnackBar(task.exception.toString())
                        }
                    }
    }

    override fun onClick(v: View) {
        val i = v.id
        //try and create their account display appropriate error messages to the user.
        if (i == R.id.createAccountButton) {
            createAccount(email?.text.toString(),
                    password?.text.toString(),
                    verifyPasswordTextView?.text.toString())
        }
    }
}
