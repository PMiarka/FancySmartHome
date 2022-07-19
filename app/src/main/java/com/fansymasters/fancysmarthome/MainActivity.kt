package com.fansymasters.fancysmarthome

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fansymasters.fancysmarthome.details.DetailsScreen
import com.fansymasters.fancysmarthome.navigation.Navigation
import com.fansymasters.fancysmarthome.permissions.PermissionRequester
import com.fansymasters.fancysmarthome.ui.theme.FancySmartHomeTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat
import no.nordicsemi.android.support.v18.scanner.ScanCallback
import no.nordicsemi.android.support.v18.scanner.ScanResult
import no.nordicsemi.android.support.v18.scanner.ScanSettings

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val devices: List<DiscoveredBluetoothDevice> = listOf()
    private lateinit var requestPermission: ActivityResultLauncher<String>
    private lateinit var requestPermissions: ActivityResultLauncher<Array<String>>
    private val scanner by lazy { BluetoothLeScannerCompat.getScanner() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission = registerForActivityResult(
            RequestPermission()
        ) { _ -> }

        requestPermissions = registerForActivityResult(
            RequestMultiplePermissions()
        ) { _ -> startScan(viewModel) }


        setContent {
            FancySmartHomeTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Navigation.MainScreen.destination
                ) {
                    composable(Navigation.MainScreen.destination) { MainScreen(navController) }
                    composable(Navigation.Details.destination) { DetailsScreen(navController) }
                }
            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun MainScreen(navController: NavHostController) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { paddingValues ->
            val state = viewModel.state.collectAsState()
            Column(modifier = Modifier.padding(paddingValues)) {
                Greeting("Android")
                Text(text = "Search device",
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .clickable {
                            startScan(viewModel)
                        })
                RequestPermissions()
                LazyColumn(modifier = Modifier.height(200.dp)) {
                    items(state.value) { item ->
                        Text(
                            text = "UUID: ${item.device.address}",
                            Modifier
                                .padding(12.dp)
                                .clickable {
                                    scanner.stopScan(scanCallback)
                                    navController.navigate(Navigation.Details.destination)
                                }
                        )
                    }
                }
            }
        }
    }


    @ExperimentalPermissionsApi
    @Composable
    private fun RequestPermissions() {
        PermissionRequester(
            Manifest.permission.BLUETOOTH_SCAN,
            "BLUETOOTH_SCAN scan granted",
            "BLUETOOTH_SCAN not granted rationale",
            "BLUETOOTH_SCAN not granted"
        ) { startScan(viewModel) }
        PermissionRequester(
            Manifest.permission.BLUETOOTH_CONNECT,
            "BLUETOOTH_CONNECT scan granted",
            "BLUETOOTH_CONNECT not granted rationale",
            "BLUETOOTH_CONNECT not granted"
        ) { startScan(viewModel) }
    }

    private fun startScan(viewModel: MainViewModel) {
        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setReportDelay(1000)
            .setUseHardwareBatchingIfSupported(false)
            .build()

        scanner.startScan(null, settings, scanCallback)
    }

    private val scanCallback = object : ScanCallback() {
        override fun onBatchScanResults(results: MutableList<ScanResult>) {
            Log.e("Piotrek", "onBatchScanResults")
            results.forEach { result ->
                if (!isNoise(result)
//                        && devices.deviceDiscovered(result)
                ) {
                    viewModel.onDevicesFound(results)
                    Log.e(
                        "Piotrek",
                        "onBatchScanResults.deviceDiscovered: ${result.device}"
                    )
                }
            }
        }


        override fun onScanResult(callbackType: Int, result: ScanResult) {
            if (!isNoise(result) && devices.deviceDiscovered(result)) {
                Log.e("Piotrek", "onScanResult.deviceDiscovered: ${result.device}")
            }
        }
    }

    private fun isNoise(result: ScanResult): Boolean {
        if (!result.isConnectable) return true
        if (result.rssi < -80) return true
//        if (FilterUtils.isBeacon(result)) return true
//        return if (FilterUtils.isAirDrop(result)) true else false
        return false
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FancySmartHomeTheme {
        Greeting("Android")
    }
}