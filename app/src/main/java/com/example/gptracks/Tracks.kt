package com.example.gptracks

import android.content.ContentValues.TAG
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
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

class Tracks() : AppCompatActivity(), TracksRecyclerAdapter.OnInfoClickListener {
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



        adapter = TracksRecyclerAdapter(trackList, this)
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
                adapter.updateTracks(trackList)

            }
        }
        fab.setOnClickListener { view ->
            //Show Dialog here to add new Item
            addNewTrack()
        }


    }
    override fun onInfoClick(track: Track) {
        showTrackInfo(track)
    }

    private fun showTrackInfo(track: Track) {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Track Information")
        alert.setMessage("Name: ${track.name}\nCountry: ${track.country}\nInfo: ${track.info} ")
        alert.setPositiveButton("OK", null)
        alert.show()
    }



    fun addNewTrack() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_track, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.nameEditText)
        val countryEditText = dialogView.findViewById<EditText>(R.id.countryEditText)
        val infoEditText = dialogView.findViewById<EditText>(R.id.infoEditText)

        val alert = AlertDialog.Builder(this)
            .setTitle("Add New Track")
            .setView(dialogView)
            .setPositiveButton("Submit") { dialog, _ ->
                val name = nameEditText.text.toString()
                val country = countryEditText.text.toString()
                val info = infoEditText.text.toString()
                saveTrackToFirebase(name, country, info)
            }
            .setNegativeButton("Cancel", null)
            .create()

        alert.show()
    }

    private fun saveTrackToFirebase(name: String, country: String, info: String) {
        val track = Track(name, country, info)
        trackCollection.add(track)
            .addOnSuccessListener { documentReference ->
                trackList.add(track)
                // Uppdatera RecyclerView
                adapter.updateTracks(trackList)

                Toast.makeText(this, "Track saved to Firebase", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save track to Firebase", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Error saving track to Firebase", e)
            }
    }
   private fun updateRecyclerView(documents: CollectionReference) {
        documents.get()
            .addOnSuccessListener { querySnapshot ->
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
        fun showinfo() {


        }


        private const val TAG = "TracksActivity"
    }



}


