package com.example.p3_azheng8

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class TurnsFragment : Fragment() {
    private var currentTurns : Int = 0
    private var turnsView : TextView? = null

    interface turnsInterface{
        fun setGameStatus(status : Boolean)
    }
    private var gameListener : turnsInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_turns, container, false)
        turnsView = view?.findViewById(R.id.turns_text)
        currentTurns = turnsView?.text.toString().toInt()
        gameListener = context as turnsInterface
        return  view
    }

    fun updateTurns(){
        currentTurns--
        turnsView?.text = currentTurns.toString()

        if(currentTurns == 0){
            val gameListener = context as turnsInterface
            gameListener.setGameStatus(false)
        }
    }
}