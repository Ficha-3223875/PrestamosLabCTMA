package com.example.prestamolab_ctma
import android.app.Application
import com.example.prestamolab_ctma.data.local.*
import com.example.prestamolab_ctma.data.remote.RetrofitProvider
import com.example.prestamolab_ctma.data.repository.*
class PrestamoApplication:Application(){val preferences by lazy{DataStoreManager(this)};val repository by lazy{RoomPrestamoRepository(PrestamoDatabase.getInstance(this),RetrofitProvider.api)}}
