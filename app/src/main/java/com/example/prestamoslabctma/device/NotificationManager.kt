package com.example.prestamoslabctma.device

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.prestamoslabctma.R

class PrestamoNotificationManager(
    private val context: Context
) {

    companion object {

        private const val CHANNEL_ID =
            "prestamolab_prestamos"

        private const val CHANNEL_NAME =
            "Préstamos"

        private const val CHANNEL_DESCRIPTION =
            "Notificaciones de préstamos y devoluciones"
    }

    fun crearCanal() {

        val manager =
            context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {

                description =
                    CHANNEL_DESCRIPTION
            }

        manager.createNotificationChannel(channel)
    }

    fun mostrarNotificacion(
        titulo: String,
        mensaje: String
    ) {

        if (
            android.os.Build.VERSION.SDK_INT >= 33 &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notification =
            NotificationCompat.Builder(
                context,
                CHANNEL_ID
            )
                .setSmallIcon(
                    android.R.drawable.ic_dialog_info
                )
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setPriority(
                    NotificationCompat.PRIORITY_DEFAULT
                )
                .setAutoCancel(true)
                .build()

        NotificationManagerCompat
            .from(context)
            .notify(
                System.currentTimeMillis().toInt(),
                notification
            )
    }
}