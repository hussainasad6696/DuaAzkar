package  com.mera.islam.duaazkar.core.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OneTimeLocation @Inject constructor(@ApplicationContext private val context: Context) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    suspend fun getLastKnownLocation(): Location? {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        return if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation.await()
        } else null
    }

}