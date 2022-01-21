package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController

class ScoreViewModel(finalScore: Int): ViewModel() {

    private val _finalScore = MutableLiveData<Int>()
    val finalScore: LiveData<Int>
        get() = _finalScore

    private val _playAgain = MutableLiveData<Boolean>()
    val playAgain: LiveData<Boolean>
        get() = _playAgain

    init {
        _playAgain.value = false
        _finalScore.value = finalScore
    }

    fun onPlayAgain() {
        _playAgain.value = true
    }

    fun onPlayAGainComplete() {
        _playAgain.value = false
    }
}