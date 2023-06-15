package com.example.gptracks

import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class TracksRecyclerAdapter(private var trackList: MutableList<Track>,private val onInfoClickListener: OnInfoClickListener) :
    RecyclerView.Adapter<TracksRecyclerAdapter.ViewHolder>() {
    interface OnInfoClickListener {
        fun onInfoClick(track: Track)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track = trackList[position]
        holder.tracks.text = track.name
        holder.countryTrack.text = track.country
        holder.infoButton.setOnClickListener {
            onInfoClickListener.onInfoClick(track)
        }
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tracks: TextView = itemView.findViewById(R.id.nameTextView)
        val countryTrack: TextView = itemView.findViewById(R.id.countryTextView)
        val infoButton: ImageButton = itemView.findViewById(R.id.infoButton)
    }

    fun updateTracks(tracks: List<Track>) {
        this.trackList.addAll(tracks)
        notifyDataSetChanged()
    }

}