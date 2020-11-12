package com.yogenp.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    lateinit var runnable: Runnable
    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_play: ImageButton = findViewById(R.id.btn_play)
        val btn_rewind: ImageButton = findViewById(R.id.btn_rewind)
        val btn_forward: ImageButton = findViewById(R.id.btn_forward )
        val seekBar: SeekBar = findViewById(R.id.seekBar)

        val mediaPlayer = MediaPlayer.create(this, R.raw.animals)
        seekBar.progress = 0
        seekBar.max = mediaPlayer.duration

        btn_play.setOnClickListener {

            if (!mediaPlayer.isPlaying){
                mediaPlayer.start()
                btn_play.setImageResource(R.drawable.ic_pause)
            }else{
                mediaPlayer.pause()
                btn_play.setImageResource(R.drawable.ic_play)
            }

        }

        btn_rewind.setOnClickListener {
            if (mediaPlayer.currentPosition == 0){
                Toast.makeText(this, "Cannot rewind!!!", Toast.LENGTH_SHORT).show()
            }else{
                mediaPlayer.seekTo(mediaPlayer.currentPosition - 5000)
            }

        }

        btn_forward.setOnClickListener {
            if (mediaPlayer.currentPosition == mediaPlayer.duration){
                Toast.makeText(this, "Cannot forward!!!", Toast.LENGTH_SHORT).show()
            }else{

                mediaPlayer.seekTo(mediaPlayer.currentPosition + 5000)
            }
        }

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        runnable = Runnable {
            seekBar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)

        mediaPlayer.setOnCompletionListener {

            btn_play.setImageResource(R.drawable.ic_play)
            seekBar.progress = 0
        }

    }
}