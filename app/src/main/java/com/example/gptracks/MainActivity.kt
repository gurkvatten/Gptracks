package com.example.gptracks

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: TracksAdapter
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Firebase.firestore

        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = TracksAdapter(ArrayList<Tracks>())






        fetchTracks()
        recyclerView.adapter = adapter
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



