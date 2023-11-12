package  com.mera.islam.duaazkar.core.presentation.permissions

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import  com.mera.islam.duaazkar.R

@Composable
fun RequestPermission(
    title: String = stringResource(R.string.notification_permission_required),
    subTitle: String = stringResource(R.string.to_show_daily_dua_reminder_notification_permission_is_required),
    permissions: Array<String> = emptyArray(),
    confirmButtonText: String = stringResource(id = R.string.grant),
    denyButtonText: String = stringResource(id = R.string.deny),
    allPermissionsGranted: () -> Unit,
    permissionsDenied: (permissions: List<String>) -> Unit
) {
    if (permissions.isEmpty()) return

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissionsResult ->
            if (permissionsResult.values.all { it }) allPermissionsGranted()
            val deniedPermissions = permissionsResult.filter { !it.value }
            if (deniedPermissions.isNotEmpty())
                permissionsDenied(deniedPermissions.keys.toList())
        }
    )

    var requestPermission by remember {
        mutableStateOf(true)
    }

    if (requestPermission)
        AlertDialog(
            onDismissRequest = { requestPermission = !requestPermission },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = subTitle)
            },
            confirmButton = {
                TextButton(onClick = {
                    launcher.launch(permissions)
                    requestPermission = !requestPermission
                }) {
                    Text(text = confirmButtonText)
                }
            },
            dismissButton = {
                TextButton(onClick = { requestPermission = !requestPermission }) {
                    Text(text = denyButtonText)
                }
            }
        )
}