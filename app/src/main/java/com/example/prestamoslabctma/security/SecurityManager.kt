package com.example.prestamoslabctma.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class SecurityManager {

    companion object {
        private const val KEYSTORE_PROVIDER =
            "AndroidKeyStore"

        private const val KEY_ALIAS =
            "PrestamoLabSecureKey"

        private const val TRANSFORMATION =
            "AES/GCM/NoPadding"

        private const val IV_SIZE_BYTES = 12

        private const val TAG_LENGTH_BITS = 128
    }

    private fun obtenerClave(): SecretKey {

        val keyStore =
            KeyStore.getInstance(
                KEYSTORE_PROVIDER
            ).apply {
                load(null)
            }

        val claveExistente =
            keyStore.getKey(
                KEY_ALIAS,
                null
            ) as? SecretKey

        if (claveExistente != null) {
            return claveExistente
        }

        val keyGenerator =
            KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                KEYSTORE_PROVIDER
            )

        val parameterSpec =
            KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or
                        KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(
                    KeyProperties.BLOCK_MODE_GCM
                )
                .setEncryptionPaddings(
                    KeyProperties.ENCRYPTION_PADDING_NONE
                )
                .setRandomizedEncryptionRequired(true)
                .build()

        keyGenerator.init(parameterSpec)

        return keyGenerator.generateKey()
    }

    fun cifrar(
        texto: String
    ): String {

        if (texto.isBlank()) {
            return ""
        }

        return try {

            val cipher =
                Cipher.getInstance(
                    TRANSFORMATION
                )

            cipher.init(
                Cipher.ENCRYPT_MODE,
                obtenerClave()
            )

            val iv =
                cipher.iv

            val encrypted =
                cipher.doFinal(
                    texto.toByteArray(
                        StandardCharsets.UTF_8
                    )
                )

            val resultado =
                ByteArray(
                    iv.size + encrypted.size
                )

            System.arraycopy(
                iv,
                0,
                resultado,
                0,
                iv.size
            )

            System.arraycopy(
                encrypted,
                0,
                resultado,
                iv.size,
                encrypted.size
            )

            Base64.encodeToString(
                resultado,
                Base64.NO_WRAP
            )

        } catch (
            error: Exception
        ) {
            ""
        }
    }

    fun descifrar(
        textoCifrado: String
    ): String {

        if (textoCifrado.isBlank()) {
            return ""
        }

        return try {

            val datos =
                Base64.decode(
                    textoCifrado,
                    Base64.NO_WRAP
                )

            if (
                datos.size <= IV_SIZE_BYTES
            ) {
                return ""
            }

            val iv =
                datos.copyOfRange(
                    0,
                    IV_SIZE_BYTES
                )

            val encrypted =
                datos.copyOfRange(
                    IV_SIZE_BYTES,
                    datos.size
                )

            val cipher =
                Cipher.getInstance(
                    TRANSFORMATION
                )

            val gcmSpec =
                GCMParameterSpec(
                    TAG_LENGTH_BITS,
                    iv
                )

            cipher.init(
                Cipher.DECRYPT_MODE,
                obtenerClave(),
                gcmSpec
            )

            val decrypted =
                cipher.doFinal(
                    encrypted
                )

            String(
                decrypted,
                StandardCharsets.UTF_8
            )

        } catch (
            error: Exception
        ) {
            ""
        }
    }

    fun generarHash(
        texto: String
    ): String {

        val digest =
            java.security.MessageDigest
                .getInstance("SHA-256")

        val bytes =
            digest.digest(
                texto.toByteArray(
                    StandardCharsets.UTF_8
                )
            )

        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }

    fun verificarHash(
        texto: String,
        hashEsperado: String
    ): Boolean {

        return generarHash(texto) ==
                hashEsperado
    }
}