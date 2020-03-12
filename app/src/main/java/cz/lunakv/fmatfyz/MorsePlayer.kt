package cz.lunakv.fmatfyz

import android.media.AudioManager.STREAM_NOTIFICATION
import android.media.ToneGenerator
import android.media.ToneGenerator.*
import java.util.*

// configuration variables
var playing = false         // lock for playing messages - avoids overlaps
var shortPlayTime = 200L
var longPlayTime = 600L
var symbolPauseTime = 200L
var charPauseTime = 600L
var wordPauseTime = 1400L
var warningPauseTime = 2000L

// Converts text to morse and plays it through the speaker
class MorsePlayer {
    val tg = ToneGenerator(STREAM_NOTIFICATION, MAX_VOLUME)
    val queue = ArrayDeque<Pair<Double, String>>()    // queues messages if more come simultaneously
                                                      // stores msgs in format <multiplier, text>


    fun playWord(multiplier: Double = 1.0, word: String, inMorse: Boolean = false) {
        val morse = if (inMorse) word else getMorseWord(word)
        var len: Long

        for (c in morse) {
            // set tone length
            if (c == '.')
                len = (shortPlayTime * multiplier).toLong()
            else
                len = (longPlayTime * multiplier).toLong()

            // play tone and sleep for the duration + pause after
            if (c == '/')
                Thread.sleep((charPauseTime * multiplier).toLong())
            else {
                tg.startTone(TONE_CDMA_DIAL_TONE_LITE, len.toInt())
                Thread.sleep(len + (symbolPauseTime * multiplier).toLong())
            }
        }
    }

    // plays the jingle indicating start of message, i times
    fun playStart(i: Int) {
        for (int in 1..i) {
            tg.startTone(TONE_PROP_ACK)             // two beeps lasting 100ms each.
            Thread.sleep(700)                // 300 ms pause after the two beeps
        }
        Thread.sleep(warningPauseTime - 300) // sleep in for-loop already paused for 300 ms
    }

    fun playEnd() {
        tg.startTone(TONE_PROP_ACK)
        Thread.sleep(400)
        tg.startTone(TONE_PROP_ACK, 100)
        Thread.sleep(3000)
    }

    // Plays a single whole message from the queue
    fun playLine(line: Pair<Double, String>, inMorse: Boolean = false) {
        try {
            for (i in 0..1) {     // repeat the message twice
                playStart(2-i) // play start twice first time, once on repeat
                if (inMorse) {
                    playWord(line.first, line.second, true)
                    return
                }

                val words = line.second.split(' ')
                for (word in words) {
                    playWord(line.first, word)
                    Thread.sleep(wordPauseTime)
                }
            }
            playEnd()
        } catch (e: InterruptedException) {
            tg.stopTone()
        }
    }

    // The main function to be called from outside
    fun play()
    {
        if (playing) return

        playing = true
        try{
            while (!queue.isEmpty())
            {
                playLine(queue.poll())
            }
        } catch (e: InterruptedException) {
            tg.stopTone()
        }
        playing = false
    }
}

fun getMorseLetter(letter: Char): String = when (letter.toLowerCase()) {
    '.', ',' -> ""
    'a' -> ".-"
    'b' -> "-..."
    'c' -> "-.-."
    'd' -> "-.."
    'e' -> "."
    'f' -> "..-."
    'g' -> "--."
    'h' -> "...."
    'i' -> ".."
    'j' -> ".---"
    'k' -> "-.-"
    'l' -> ".-.."
    'm' -> "--"
    'n' -> "-."
    'o' -> "---"
    'p' -> ".--."
    'q' -> "--.-"
    'r' -> ".-."
    's' -> "..."
    't' -> "-"
    'u' -> "..-"
    'v' -> "...-"
    'w' -> ".--"
    'x' -> "-..-"
    'y' -> "-.--"
    'z' -> "--.."
    '1' -> ".----"
    '2' -> "..---"
    '3' -> "...--"
    '4' -> "....-"
    '5' -> "....."
    '6' -> "-...."
    '7' -> "--..."
    '8' -> "---.."
    '9' -> "----."
    '0' -> "-----"
    else -> throw IndexOutOfBoundsException("Invalid letter: $letter")
}

fun getMorseWord(word: String) : String {
    var res = ""
    for (c in word) {
        res += getMorseLetter(c) + '/'
    }
    return res
}

fun getMorseText(text: String): String {
    var res = ""
    for (word in text.split(' ')) {
        res += getMorseWord(word) + "//"
    }
    return res
}