package com.example.sleepbit

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AlarmActivity : AppCompatActivity() {
    private lateinit var alarmPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm2)

        alarmPlayer = MediaPlayer.create(this,R.raw.alarm);
        alarmPlayer.start();
    }
}