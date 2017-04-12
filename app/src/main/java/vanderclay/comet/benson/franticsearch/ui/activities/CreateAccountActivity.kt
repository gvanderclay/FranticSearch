package vanderclay.comet.benson.franticsearch.ui.activities

import android.app.VoiceInteractor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*

import vanderclay.comet.benson.franticsearch.R

class CreateAccountActivity : AppCompatActivity(), View.OnClickListener {

    // Reference to the firebase authorization object
    private var mAuth: FirebaseAuth? = null

    // Reference to the firebase Auth State Changed listener
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null

//    private var verifyEmailText = findViewById()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
    }

//    /*
//    * user can press create an account.
//    */
//    private fun createAccount(email: String, password: String) {
//        Log.w(TAG, "createAccount: " + email)
//        mAuth?.createUserWithEmailAndPassword(email, password)
//                ?.addOnCompleteListener(this, OnCompleteListener {
//                    @Override
//                    fun onComplete(task: Task<AuthResult>) {
//                        if (task.isSuccessful) {
//                            Log.w(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful())
//                            val snackbar = Snackbar.make(LoginActivity, "Successfully Signed In", Snackbar.LENGTH_LONG)
//                            snackbar.show()
//                        } else if (!task.isSuccessful) {
//                            val snackbar = Snackbar.make(LoginActivity, "Welcome to AndroidHive", Snackbar.LENGTH_LONG)
//                            snackbar.show()
//                            Toast.makeText(this.applicationContext, R.string.auth_failed, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//    }

    override fun onClick(v: View) {
//        val i = v.id
//        if (i == R.id.email_sign_in_button) {
//            signIn(mEmailView?.getText().toString(), mPasswordView?.getText().toString())
//        }
//        else if(i == R.id.create_account_button){
//            transferToCreateAccount()
//        }
    }
}
