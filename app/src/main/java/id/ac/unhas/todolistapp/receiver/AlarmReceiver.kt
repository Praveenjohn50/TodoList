package id.ac.unhas.todolistapp.receiver

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import id.ac.unhas.todolistapp.R


/**
 * Created by Praveen John on 11/12/2021
 * Alarm receiver for notifications
 * */
class AlarmReceiver : BroadcastReceiver() {

    /**
     * static object
     * */
    companion object {
        /**
         * Reminder unique id
         * */
        private const val ID_REMINDER = 100
        const val EXTRA_MESSAGE = "message"
    }

    /**
     *On Receive of Alarm event
     * */
    override fun onReceive(context: Context, intent: Intent) {
        val notifId = ID_REMINDER
        val todo = intent.getStringExtra(EXTRA_MESSAGE)
        val title = "ToDo Reminder"

        showReminderNotification(context, title, todo, notifId)
    }

    /**
     *setting the reminder
     * */
    fun setReminder(context: Context, dueDate: Long, message: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ID_REMINDER,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.set(AlarmManager.RTC_WAKEUP, dueDate, pendingIntent)
    }

    /**
     *Method to show notification
     * */
    private fun showReminderNotification(
        context: Context,
        title: String,
        todo: String?,
        notifId: Int
    ) {
        val channeId = "id.ac.unhas.todolistapp.util"
        val channelName = "ToDo Notification"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channeId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.CYAN
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = NotificationCompat.Builder(context, channeId)
                .setChannelId(channeId)
                .setContentTitle(title)
                .setContentText(todo)
                .setSmallIcon(R.drawable.ic_calendar_check_outline_24dp)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

        } else {
            builder = NotificationCompat.Builder(context, channeId)
                .setChannelId(channeId)
                .setContentTitle(title)
                .setContentText(todo)
                .setSmallIcon(R.drawable.ic_calendar_check_outline_24dp)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        }
        notificationManager.notify(notifId, builder.build())
    }
}
