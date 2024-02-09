package com.example.chatapplication

import android.accessibilityservice.GestureDescription.StrokeDescription
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Signup : AppCompatActivity() {
    private lateinit var name:EditText
    private lateinit var Username: EditText
    private lateinit var Password:EditText
    private lateinit var Signup_Button: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mAuth = FirebaseAuth.getInstance()
        name = findViewById(R.id.name)
        Username = findViewById(R.id.email)
        Password = findViewById(R.id.setpassword)
        Signup_Button = findViewById(R.id.signup_button)

        Signup_Button.setOnClickListener {
        Signup_Button.setOnClickListener {
            val name_edt = name.text.toString()
            val email = Username.text.toString()
            val password = Password.text.toString()

            signup(name_edt,email,password)
        }
    }
}

    private fun signup(User_name:String,email: String, password: String) {
        //logic for create a new user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(User_name,email, mAuth.currentUser?.uid!!)
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@Signup,MainActivity::class.java)
                    startActivity(intent)
                    finish()
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

    private fun addUserToDatabase(name: String, email: String, uid: String?) {
        mDbRef = FirebaseDatabase.getInstance().getReference()

        if (uid != null) {
            mDbRef.child("user").child(uid).setValue(User(name , email , uid))
        }
    }


}