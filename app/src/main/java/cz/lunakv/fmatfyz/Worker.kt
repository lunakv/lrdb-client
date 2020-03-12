package cz.lunakv.fmatfyz

import android.app.IntentService
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationCompat.PRIORITY_LOW
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.net.ConnectException

// Plays audio messages in the background
class PlayerService : IntentService("PlayerService"){
    private val mp = MorsePlayer()
    val fm = FileMan(this)
    val msgUrl = getMessageUrl()
    val tknUrl = getTokenUrl()
    val mgc = getMagic()
    val vibratePattern = longArrayOf(0, shortPlayTime, symbolPauseTime, longPlayTime, symbolPauseTime, shortPlayTime)
    val vibrateWait = 8000L

    // magic constants are written in C++ to make them "harder to reverse engineer"
    external fun getMagic(): String
    external fun getMessageUrl(): String
    external fun getTokenUrl(): String

    companion object {
        init {
            System.loadLibrary("magician")
        }
    }

    // Called from FirebaseListener when new message is received.
    override fun onHandleIntent(intent: Intent?) {
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        val message = intent?.extras?.getString("message") ?: ""
        val multiplier = intent?.extras?.getString("multiplier")?.toDoubleOrNull() ?: 1.0

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentText(getString(R.string.notification_message))
            .setContentTitle(getText(R.string.app_name))
            .setSmallIcon(R.drawable.ic_fmf_notification)
            .setContentIntent(pendingIntent)
            .setPriority(PRIORITY_LOW)
            .build()

        sendConfirmation(intent)                // confirm message received to server
        startForeground(420, notification)  // <= 1 "playing" msg at a time - don't need unique id
        shake()                                 // vibrate the phone
        Thread.sleep(vibrateWait)
        mp.queue.add(Pair(multiplier, message)) // play the message
        mp.play()
    }

    // vibrates the phone, warning the user before playing a message
    private fun shake(){
        if (Build.VERSION.SDK_INT >= 26){
            (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(VibrationEffect.createWaveform(vibratePattern, -1))
        } else {
            (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(vibratePattern, -1)
        }
    }

    private fun sendConfirmation(intent: Intent?) {
        if (!fm.existsToken()) {
            if (!createToken()) {
                return
            }
        }

        val token = fm.getToken()
        val id = intent?.extras?.getString("id")
        // magic constants shouldn't include '&'
        val post = "magic=$mgc&token=$token&message=$id"
        val body = RequestBody.create(URLENCODED, post)
        val request = Request.Builder()
            .url(msgUrl)
            .post(body)
            .build()
        try {
            client.newCall(request).execute().use {}
        } catch (e: ConnectException){ }
    }

    val URLENCODED = MediaType.get("application/x-www-form-urlencoded")
    var client = OkHttpClient()

    // register a new token from the server
    fun createToken(): Boolean{
        val json = "magic=$mgc"
        val body = RequestBody.create(URLENCODED, json)
        val request = Request.Builder()
            .url(tknUrl)
            .post(body)
            .build()
        try {
            client.newCall(request).execute().use { response ->
                fm.saveToken(response.body()!!.string())
                return true
            }
        } catch (e: ConnectException) {
            return false
        }
    }
}

