package com.example.chatapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var Username:EditText
    private lateinit var Password:EditText
    private lateinit var Signup_Button:Button
    private lateinit var Login:Button
    private lateinit var mAuth:FirebaseAuth
//    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    FirebaseApp.initializeApp(this)

    // Check if a user is already authenticated
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    if (currentUser != null) {
        intent = Intent(this@Login,MainActivity::class.java)
        startActivity(intent)
        finish()
    } else {
        mAuth = FirebaseAuth.getInstance()
        Username = findViewById(R.id.username)
        Password = findViewById(R.id.password)
        Signup_Button = findViewById(R.id.signup_login)
        Login = findViewById(R.id.loginbutton)
        Signup_Button.setOnClickListener {
            val intent = Intent(this@Login, Signup::class.java)
            startActivity(intent)
        }
        Login.setOnClickListener {
            val email = Username.text.toString()
            val password = Password.text.toString()

            login(email, password)
        }
    }
}
    private fun login(email: String, password: String) {

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@Login , MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    // Sign in success, update UI with the signed-in user's information
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }

    }
}