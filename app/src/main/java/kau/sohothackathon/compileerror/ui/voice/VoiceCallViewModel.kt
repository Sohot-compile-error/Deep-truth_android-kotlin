package kau.sohothackathon.compileerror.ui.voice

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class VoiceCallViewModel @Inject constructor() : ViewModel() {

    private val _callTime = mutableStateOf(0)
    val callTime: MutableState<Int> get() = _callTime

    private val _isDetected = mutableStateOf(false)
    val isDetected: State<Boolean> get() = _isDetected

    private var timer = Timer()
    private var timerTask: TimerTask = object : TimerTask() {
        override fun run() {
            _callTime.value = _callTime.value + 1
            if (_callTime.value <= 0) {
                timer.cancel()
            }
        }
    }

    private fun startTimer() {
        _callTime.value = 0
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                _callTime.value = _callTime.value + 1
            }
        }
        timer.schedule(timerTask, 0, 1000);
    }

    fun stopTimer() {
        timer.cancel()
    }

    fun resetTimer() {
        _callTime.value = 0
        startTimer()
    }

    fun updateIsDetected(isDetected: Boolean) {
        _isDetected.value = isDetected
    }
}