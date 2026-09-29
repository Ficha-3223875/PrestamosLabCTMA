package com.example.prestamoslabctma.device

import android.content.Context
import android.net.Uri

class PhotoManager(
    private val context: Context
) {

    fun obtenerUri(uri: Uri?): String? {
        return uri?.toString()
    }

    fun existeFoto(
        uri: String?
    ): Boolean {
        if (uri.isNullOrBlank()) {
            return false
        }

        return try {
            context.contentResolver
                .openInputStream(
                    Uri.parse(uri)
                )
                ?.use {
                    true
                } ?: false
        } catch (
            error: Exception
        ) {
            false
        }
    }
}