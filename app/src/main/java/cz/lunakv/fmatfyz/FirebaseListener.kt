package cz.lunakv.fmatfyz
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*
import java.util.Calendar.*

// Receives incoming messages from Firebase and passes them on to playback
class FirebaseListener : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage?) {
        // play the received message in morse code
        val intent = Intent(this, PlayerService::class.java)
        intent.putExtra("message", p0?.data!!["morse"])
        intent.putExtra("id", p0?.data!!["id"])
        intent.putExtra("multiplier", p0?.data!!["multiplier"])
        startService(intent)

        // display a notification
        val info = getString(R.string.notification_sent) + " " + getDateTimeString()
        val builder = NotificationCompat.Builder(baseContext, CHANNEL_ID)
            .setContentText(info)
            .setContentTitle(getString(R.string.notification_sent_title))
            .setSmallIcon(R.drawable.ic_fmf_notification)
            .setStyle(NotificationCompat.BigTextStyle().bigText(info))
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(System.currentTimeMillis().toInt(), builder.build())
    }

    private fun getDateTimeString() : String {
        val cal = Calendar.getInstance()
        val y = cal[YEAR].toString().padStart(4, '0')
        val mon = cal[MONTH].toString().padStart(2, '0')
        val d = cal[DAY_OF_MONTH].toString().padStart(2 ,'0')
        val h = cal[HOUR_OF_DAY].toString().padStart(2, '0')
        val min = cal[MINUTE].toString().padStart(2, '0')
        val s = cal[SECOND].toString().padStart(2, '0')
        return "$y-$mon-$d $h:$min:$s"
    }
}