package com.example.gptracks

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference

class Tracks() : AppCompatActivity() {
    private val trackList = mutableListOf<Track>()
    lateinit var auth: FirebaseAuth
    private lateinit var storageRef: StorageReference
    lateinit var adapter: TracksRecyclerAdapter
    val db = Firebase.firestore
    val trackCollection = db.collection("Tracks")

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracks)
        auth = Firebase.auth
        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        val recyclerView = findViewById<RecyclerView>(R.id.listTracks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        


        adapter = TracksRecyclerAdapter(trackList)
        recyclerView.adapter = adapter
        updateRecyclerView(trackCollection)
        // Log statement to verify if the adapter has been set
        Log.d(TAG, "Adapter set: ${recyclerView.adapter != null}")

// Log statement to verify if the layout manager has been set
        Log.d(TAG, "Layout manager set: ${recyclerView.layoutManager != null}")

        db.collection("Tracks").addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.e(1.toString(), "Error getting documents: ", exception)
                return@addSnapshotListener

            }

            if (snapshot != null) {
                // Update the RecyclerView with the new data


            }
        }
        fab.setOnClickListener { view ->
            //Show Dialog here to add new Item
            addNewTrack()
        }

    }

   fun addNewTrack() {

        val alert = AlertDialog.Builder(this)
        val itemEditText = EditText(this)
        alert.setMessage("Add new Track")
        alert.setTitle("Enter track name")

        alert.setView(itemEditText)

        alert.setView(itemEditText)

        alert.setPositiveButton("Submit")  { dialog, positiveButton ->


        }

        alert.show()
    }
    private fun updateRecyclerView(documents: CollectionReference) {
        documents.get()
            .addOnSuccessListener { querySnapshot ->
                trackList.clear()
                for (document in querySnapshot.documents) {
                    val track = document.toObject<Track>()
                    if (track != null) {
                        trackList.add(track)
                    }
                }
                adapter.updateTracks(trackList)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error getting documents", e)
            }
    }
    companion object {
        private const val TAG = "TracksActivity"
    }



}


