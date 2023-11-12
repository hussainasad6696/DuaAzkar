package  com.mera.islam.duaazkar.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OneTimeLocation @Inject constructor(@ApplicationContext private val context: Context) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    @SuppressLint("MissingPermission")
    suspend fun getLastKnownLocation(): Location? {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        return fusedLocationProviderClient.lastLocation.await()
    }

}