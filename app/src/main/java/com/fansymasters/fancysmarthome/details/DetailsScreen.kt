package com.fansymasters.fancysmarthome.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fansymasters.fancysmarthome.navigation.Navigation

@Composable
internal fun DetailsScreen(
    navController: NavHostController,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.connect(context)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Text("Details",
            modifier = Modifier.clickable {
//                navController.navigate(Navigation.MainScreen.destination)
                viewModel.setLed()
            })
    }
}