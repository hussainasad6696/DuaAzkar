package  com.mera.islam.duaazkar.core.utils

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

object SdkHelper {
    @ChecksSdkIntAtLeast
    fun isTiramisu() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    @ChecksSdkIntAtLeast
    fun isO() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
}