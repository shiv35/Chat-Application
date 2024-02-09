package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var userRecycler: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mdRef: DatabaseReference
    private lateinit var logoutbutton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userList = ArrayList()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Health Connect"
        setSupportActionBar(toolbar)
        logoutbutton = findViewById(R.id.logout_Button)
        adapter = UserAdapter(userList, this)
        mdRef = FirebaseDatabase.getInstance().getReference()
        mAuth = FirebaseAuth.getInstance()
        userRecycler = findViewById(R.id.userRecyclerview)
        userRecycler.layoutManager = LinearLayoutManager(this)
        userRecycler.adapter = adapter
        logoutbutton.setOnClickListener {
            mAuth.signOut()
            val intent_new = Intent(this@MainActivity, Login::class.java)
            startActivity(intent_new)
            finish()
        }
        mdRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postsnapshot in snapshot.children) {
                    val currentUser = postsnapshot.getValue(User::class.java)
                    if (mAuth.currentUser?.uid != currentUser?.uid) {
                        userList.add(currentUser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}