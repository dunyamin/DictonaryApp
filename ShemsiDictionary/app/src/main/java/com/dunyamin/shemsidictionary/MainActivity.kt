package com.dunyamin.shemsidictionary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dunyamin.shemsidictionary.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload()
        }
    }

    private fun reload() {
        val intent = Intent(this, WordsListActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun signup(view: View) {

        auth.createUserWithEmailAndPassword(binding.emailText.text.toString(), binding.passwordText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    val intent = Intent(this, WordsListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener { exception ->
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", exception)
                Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                updateUI(null)

            }

    }

    fun login(view: View) {

        auth.signInWithEmailAndPassword(binding.emailText.text.toString(), binding.passwordText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")

                    val user = auth.currentUser
                    updateUI(user)

                    Toast.makeText(this, "Welcome: $user", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, WordsListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener { exception ->
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", exception)
                Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()

                updateUI(null)

            }
    }

    private fun updateUI(user: FirebaseUser?) {
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

}