package com.example.gptracks


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var storageRef: StorageReference
    var trackList = mutableListOf<Track>()

    lateinit var emailView: EditText
    lateinit var passView: EditText
    val db = Firebase.firestore
    val trackCollection = db.collection("Tracks")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Firebase.firestore

        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = TracksAdapter(ArrayList<Tracks>())



        auth = Firebase.auth

        emailView = findViewById(R.id.editTextTextEmailAddress)
        passView = findViewById(R.id.editTextTextPassword)
        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val email = emailView.text?.toString()
        val password = passView.text?.toString()

        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("!!!", "Log in Success")
                        goToTracks()
                    } else {
                        Log.d("!!!","Login not found ${task.exception}")
                    }
                }
        } else {
            Log.d("!!!", "Email or password is empty")
        }
    }

    private fun goToTracks() {
        val intent = Intent(this, Tracks::class.java)
        startActivity(intent)
    }



    private fun fetchTracks() {
        val firestore = FirebaseFirestore.getInstance()
        val tracksCollection = firestore.collection("test")

        tracksCollection.get()
            .addOnSuccessListener { result ->
                val tracksList = ArrayList<Tracks>()
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val track = document.toObject(Tracks::class.java)
                    tracksList.add(track)
                }
                adapter.setTracks(tracksList)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
    private fun updateRecyclerView(tracksList: ArrayList<Tracks>) {
        TracksAdapter(tracksList)

    }
}



