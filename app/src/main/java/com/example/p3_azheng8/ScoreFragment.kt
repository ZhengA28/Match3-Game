package com.example.p3_azheng8

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ScoreFragment : Fragment() {
    private var currentScore : Int = 0
    private var scoreView : TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_score, container, false)
        scoreView = view?.findViewById(R.id.score)
        return view
    }

    fun updateScore(matches: Int){
        //Give points based on number of matches
        currentScore += when(matches){
            3-> 100*matches
            4-> 150*matches
            5-> 200*matches
            else-> 300*matches
        }
        println("matches:$matches")
        println("current score:${currentScore}")
        scoreView?.text = currentScore.toString()
    }

    fun getScore(): Int{
        return currentScore
    }
}