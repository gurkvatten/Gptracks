package com.example.gptracks


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TracksAdapter(var tracksList: ArrayList<Tracks>) :
    RecyclerView.Adapter<TracksAdapter.TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_item, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = tracksList[position]
        holder.bind(track)
    }

    override fun getItemCount(): Int {
        return tracksList.size
    }

    fun setTracks(tracksList: ArrayList<Tracks>) {
        this.tracksList = tracksList
        notifyDataSetChanged()
    }

    inner class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val countryTextView: TextView = itemView.findViewById(R.id.countryTextView)

        fun bind(track: Tracks) {
            nameTextView.text = track.name
            countryTextView.text = track.country
        }
    }
}