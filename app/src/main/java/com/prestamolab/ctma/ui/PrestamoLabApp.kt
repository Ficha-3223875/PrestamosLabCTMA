package com.prestamolab.ctma.ui

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.prestamolab.ctma.util.LoanReminder
import com.prestamolab.ctma.viewmodel.EquipmentViewModel

@Composable
fun PrestamoLabApp(factory: EquipmentViewModel.Factory) {
    val nav = rememberNavController(); val vm: EquipmentViewModel = viewModel(factory=factory); val state by vm.uiState.collectAsStateWithLifecycle(); val context=LocalContext.current
    val notificationPermission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { }
    LaunchedEffect(Unit) { LoanReminder.createChannel(context); if (Build.VERSION.SDK_INT >= 33) notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS) }

    NavHost(navController=nav, startDestination="home") {
        composable("home") { HomeScreen(state.equipment,state.availableOnly,state.lastSync,vm::setAvailableOnly,vm::sync,{nav.navigate("detail/$it")},{nav.navigate("loans")},{nav.navigate("device")}) }
        composable("detail/{equipmentId}", arguments=listOf(navArgument("equipmentId"){type=NavType.IntType})) { back ->
            val id=back.arguments?.getInt("equipmentId") ?: -1; LaunchedEffect(id){vm.loadEquipment(id)}
            DetailScreen(state.selectedEquipment,state.message,{nav.popBackStack()},{nav.navigate("request/$id")})
        }
        composable("request/{equipmentId}", arguments=listOf(navArgument("equipmentId"){type=NavType.IntType})) { back ->
            val id=back.arguments?.getInt("equipmentId") ?: -1; LaunchedEffect(id){vm.loadEquipment(id)}
            RequestLoanScreen(state.selectedEquipment,state.formErrors,state.message,state.saving,{nav.popBackStack()}) { d,p,h ->
                vm.submitLoan(d,p,h) { loanId,duration -> LoanReminder.schedule(context,loanId,duration); nav.navigate("loan/$loanId") { popUpTo("home") } }
            }
        }
        composable("loans") { LoansScreen(state.loans,{nav.popBackStack()},{nav.navigate("loan/$it")}) }
        composable("loan/{loanId}", arguments=listOf(navArgument("loanId"){type=NavType.IntType})) { back ->
            val id=back.arguments?.getInt("loanId") ?: -1; LaunchedEffect(id){vm.loadLoan(id)}
            LoanDetailScreen(state.selectedLoan,state.message,{nav.popBackStack()},vm::cancelLoan,vm::attachEvidence,vm::returnLoan)
        }
        composable("device") { DeviceStatusScreen { nav.popBackStack() } }
    }
}
