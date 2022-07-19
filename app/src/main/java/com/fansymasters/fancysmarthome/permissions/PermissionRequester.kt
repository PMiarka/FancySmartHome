package com.fansymasters.fancysmarthome.permissions

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalPermissionsApi
@Composable
internal fun PermissionRequester(
    permission: String,
    grantedText: String,
    notGrantedRationale: String,
    notGranted: String,
    onPermissionStatus: (Boolean) -> Unit

) {
    val permissionState = rememberPermissionState(
        permission,
        onPermissionStatus
    )

    when (permissionState.status) {
        // If the camera permission is granted, then show screen with the feature enabled
        PermissionStatus.Granted -> {
            Text(grantedText)
        }
        is PermissionStatus.Denied -> {
            Column {
                val textToShow =
                    if ((permissionState.status as PermissionStatus.Denied).shouldShowRationale) {
                        notGrantedRationale
                    } else {
                        notGranted
                    }
                Text(textToShow)
                Button(onClick = { permissionState.launchPermissionRequest() }) {
                    Text("Request permission")
                }
            }
        }
    }
}