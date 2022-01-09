package com.example.sleepbit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class TimeAdapter (
    var context: Context?,
    val list : List<TimeAlarm>
) :
    RecyclerView.Adapter<TimeAdapter.Holder>() {
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val time: TextView = itemView.findViewById(R.id.time)
        val image: ImageView = itemView.findViewById(R.id.bullet)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.time_card, parent, false)
            return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val chat : TimeAlarm = list[position]


        holder.image.setImageResource(chat.image)
        holder.time.text = chat.time

    }
    override fun getItemCount(): Int {
        return list.size
    }
    }

