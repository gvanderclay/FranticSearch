package vanderclay.comet.benson.franticsearch.ui.activities


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import vanderclay.comet.benson.franticsearch.R

class CreateAccountActivity : AppCompatActivity(), View.OnClickListener {

    // Reference to the firebase authorization object
    private var mAuth: FirebaseAuth? = null

    // Reference to the firebase Auth State Changed listener
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null

    //Reference to password Text View
    private var passwordTextView: TextView? = null

    //Reference to password verification Text view
    private var verifyPasswordTextView: TextView? = null

    //Reference to Create Account Button
    private var createAccountButton: Button? = null

    private var emailTextView: TextView? = null

    //Log Keyword to make debuggin easier
    var TAG: String = "CreateAccountActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        passwordTextView = findViewById(R.id.password) as TextView
        verifyPasswordTextView = findViewById(R.id.retypePassword) as TextView
        createAccountButton = findViewById(R.id.createAccountButton) as Button
        emailTextView = findViewById(R.id.email) as TextView
        createAccountButton?.setOnClickListener(this)

        //Firebase Setup
        this.mAuth = FirebaseAuth.getInstance()

        mAuthListener = object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
                var user: FirebaseUser? = firebaseAuth.currentUser
                if (user != null) {
                    Log.w(TAG, "user create account successful")


                } else {


                    Log.w(TAG, "user signed out")
                }
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
        Log.w(TAG, "Stopping the task.")
        super.onStop()
        if (mAuthListener != null) {
            Log.w(TAG, "stop listening for changes in the login activity")
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }

    public override fun onStart() {
        super.onStart()
        Log.w(TAG, "Application has been started, assign the listener for auth state changes")
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
        return lhs.equals(rhs)
    }

    private fun showSnackBar(message: String) {
        //Dunno why I had to add an extra line in this version of the snackbar ???
        val snackbar = Snackbar.make(findViewById(R.id.CreateAccountActivity), message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    /*
     * Nothing like a bunch of rested ifs to make you want to die a little inside.
     * Oh well, it's 10:37 at night...
     */
    private fun checkCreateAccountParameters(email: String, verifyPassword: String, password: String): Boolean {
        if (isValidPassword(password) && isValidPassword(verifyPassword)) {
            if (isValidEmail(email)) {
                //congrats you can type like a decent human being
                if (samePassword(verifyPassword, password)) {
                    return true
                } else {
                    showSnackBar("passwords don't match")
                    return false
                }
            }
            showSnackBar("Check Email Format")
            return false
        }
        showSnackBar("Password Not Strong Enough")
        return false
    }

    /*
   * user can press create an account.
   * If anyone reads this I don't know why, but declaring an object into add
   * On completeListener with a lambda syntax doesn't work?
   * It's a kotlin thing moving on with my life.
   */
    private fun createAccount(email: String, verifyPassword: String, password: String) {
        Log.w(TAG, "createAccount with email: " + email)
        if (checkCreateAccountParameters(email, verifyPassword, password))
            mAuth?.createUserWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                        override fun onComplete(task: Task<AuthResult>) {
                            if (task.isSuccessful) {
                                Log.w(TAG, "createUserWithEmail: completed?: " + task.isSuccessful())
                                showSnackBar("Account Successfully Created")
                            } else if (!task.isSuccessful) {
                                Log.w(TAG, "createUserWithEmail: completed? : " + !task.isSuccessful())
                                showSnackBar(task.exception.toString())
                            }
                        }
                    })
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
