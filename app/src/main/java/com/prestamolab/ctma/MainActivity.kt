package com.prestamolab.ctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.prestamolab.ctma.ui.PrestamoLabApp
import com.prestamolab.ctma.viewmodel.EquipmentViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as PrestamoLabApplication
        setContent { MaterialTheme { PrestamoLabApp(EquipmentViewModel.Factory(app.repository)) } }
    }
}
