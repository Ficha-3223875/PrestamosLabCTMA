package com.example.prestamoslabctma.data.repository

import com.example.prestamoslabctma.data.remote.RetrofitProvider

object RemoteRepositoryProvider {

    val repository: RemotePrestamoRepository by lazy {
        RemotePrestamoRepository(
            RetrofitProvider.api
        )
    }
}