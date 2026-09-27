package com.prestamolab.ctma.util

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.prestamolab.ctma.R

object LoanReminder {
    const val CHANNEL_ID = "loan_reminders"

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(NotificationChannel(CHANNEL_ID, "Recordatorios de devolución", NotificationManager.IMPORTANCE_DEFAULT))
        }
    }

    fun schedule(context: Context, loanId: Int, durationHours: Int) {
        val intent = Intent(context, LoanReminderReceiver::class.java).putExtra("loanId", loanId)
        val pending = PendingIntent.getBroadcast(context, loanId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val trigger = System.currentTimeMillis() + durationHours.coerceAtLeast(1) * 60L * 60L * 1000L
        context.getSystemService(AlarmManager::class.java)?.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, trigger, pending)
    }
}

class LoanReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        LoanReminder.createChannel(context)
        if (Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) return
        val id = intent.getIntExtra("loanId", 0)
        val notification = NotificationCompat.Builder(context, LoanReminder.CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("PréstamoLab CTMA")
            .setContentText("Recuerda revisar la devolución de tu préstamo #$id.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
        context.getSystemService(NotificationManager::class.java)?.notify(1000 + id, notification)
    }
}
