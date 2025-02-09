package  com.mera.islam.duaazkar.core.extensions

import android.app.DownloadManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.temporal.ChronoUnit


infix fun Context.share(shareable: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT,shareable)
        type = "text/plain"
    }

    this.startActivity(Intent.createChooser(sendIntent,"Dua And Azkar"))
}

infix fun Context.copy(shareable: String) {
    val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText("Copied Text", shareable))
}

fun LocalDateTime.nextDayNoon(): LocalDateTime {
    val desiredTime = LocalTime.NOON
    var nextDayAtNoon: LocalDateTime = this.toLocalDate().atTime(desiredTime)
    if (this.toLocalTime().isAfter(desiredTime))
        nextDayAtNoon = nextDayAtNoon.plus(1, ChronoUnit.DAYS)
    return nextDayAtNoon.also { "${it.dayOfMonth}/${it.month}/${it.year}".log() }
}

@Composable
fun Dp.dpToPx(): Float {
    val localDensity = LocalDensity.current
    return with(localDensity) { this@dpToPx.toPx() }
}
