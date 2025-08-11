package com.example.p3_azheng8

import android.content.DialogInterface
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class GameActivity : AppCompatActivity(), GridHandler.gameInterface, TurnsFragment.turnsInterface {
    private var gridHandler : GridHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }

    //Updates the score in fragment
    override fun updateScore(matches: Int){
        val scoreFrag = supportFragmentManager.findFragmentById(R.id.score_fragment) as ScoreFragment
        scoreFrag.updateScore(matches)
    }

    //Update number of turns remaining in fragment
    override fun updateTurns(){
        val turnFrag = supportFragmentManager.findFragmentById(R.id.turns_fragment) as TurnsFragment
        turnFrag.updateTurns()
    }

    //Close game activity
    override fun setGameStatus(status : Boolean){
        val scoreFrag = supportFragmentManager.findFragmentById(R.id.score_fragment) as ScoreFragment
        val score = scoreFrag.getScore()

        if(score >= 4500){
            finishGame("CONGRATULATIONS! YOU WIN!")
        }
        else{
            finishGame("BETTER LUCK NEXT TIME!")
        }
    }


    //Alert Dialogue when game finishes
    fun finishGame(message: String){
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this@GameActivity)
        dialog.setTitle(message)
            .setIcon(R.drawable.ic_launcher_background)
            .setMessage("What Do You Want To Do?")
            .setNegativeButton("QUIT",
                DialogInterface.OnClickListener { dialoginterface, i ->
                    finish()
                })
            .setPositiveButton("RETRY", DialogInterface.OnClickListener { dialoginterface, i ->
                val intent = intent
                finish()
                startActivity(intent)
            }).show()
    }

    override fun onStart(){
        super.onStart()
        val gridImg : ImageView? = findViewById(R.id.grid)

        gridImg?.post{
            gridHandler = GridHandler(this, gridImg)
            val rootView : ViewGroup = findViewById(R.id.game)
            rootView.addView(gridHandler)
        }
    }
}