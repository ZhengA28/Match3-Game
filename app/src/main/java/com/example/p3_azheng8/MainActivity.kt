package com.example.p3_azheng8

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var imgView : ImageView? = null
    private var animation : AnimationDrawable? = null
    private var music : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imgView = findViewById(R.id.running_animation)
        imgView!!.setBackgroundResource(R.drawable.fox_running)
        animation = imgView!!.background as AnimationDrawable

        music = MediaPlayer.create(this, R.raw.keep_running)
        music?.setLooping(true)
        music?.start()
    }

    fun  onRules(view: View){
        val intent = Intent(this, RulesActivity::class.java)
        startActivity(intent)
    }

    fun onCredit(view: View){
        val intent = Intent(this, CreditActivity::class.java)
        startActivity(intent)
    }

    fun startGame(view: View){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    override fun onStart(){
        super.onStart()
        animation!!.start()
    }
}