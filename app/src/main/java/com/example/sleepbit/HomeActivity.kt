package com.example.sleepbit

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {
    lateinit var mDatabase : DatabaseReference
    lateinit var database : FirebaseDatabase
    private lateinit var alarmPlayer: MediaPlayer
    private var count : Int = 0
    private lateinit var adapter: TimeAdapter
    private lateinit var timeView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        timeView = findViewById(R.id.recycle)
        database = FirebaseDatabase.getInstance("https://sleepbitt-default-rtdb.asia-southeast1.firebasedatabase.app")
        mDatabase = database.getReference("Sleep")
        Log.e("SLEEP",mDatabase.toString())
        var dataList: ArrayList<TimeAlarm> = ArrayList()

        Log.e("LIST",dataList.toString())
        adapter = TimeAdapter(this@HomeActivity, dataList)
        timeView.layoutManager = LinearLayoutManager(this@HomeActivity)
        timeView.adapter = adapter
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val sleep: String = snapshot.getValue().toString()
                Log.e("SLEEP",sleep)
                if(sleep == "yes")
                {
                    count++
                    val view = View.inflate(this@HomeActivity, R.layout.alarm_dialog, null)
                    val builder = AlertDialog.Builder(this@HomeActivity)
                    builder.setView(view)
                    val dialog = builder.create()
                    val lp = dialog.window!!.attributes
                    lp.dimAmount = 0.0f
                    dialog.window!!.attributes = lp
                    dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

                    dialog.show()
                    val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
                    Log.e("TIME",currentTime)
                    dataList.add(TimeAlarm(currentTime, R.drawable.ic_bullet))
//                    adapter.notifyItemInserted(dataList.size-1)
                    alarmPlayer = MediaPlayer.create(this@HomeActivity, R.raw.alarm)
                    alarmPlayer.start();
                    dialog.setCancelable(false)
                    dialog.setCanceledOnTouchOutside(false)
                    view.findViewById<Button>(R.id.returnhome).setOnClickListener {
                        alarmPlayer.stop();
                        dialog.dismiss()
                    }
                    adapter = TimeAdapter(this@HomeActivity, dataList)
                    timeView.layoutManager = LinearLayoutManager(this@HomeActivity)
                    timeView.adapter = adapter
                }
                if(sleep == "error")
                {
                    val intent = Intent(this@HomeActivity, ErrorActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        val logout = findViewById<ImageButton>(R.id.logout)
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Log.e("User", FirebaseAuth.getInstance().currentUser.toString())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}