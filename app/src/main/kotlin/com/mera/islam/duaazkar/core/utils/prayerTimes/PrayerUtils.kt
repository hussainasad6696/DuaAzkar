package  com.mera.islam.duaazkar.core.utils.prayerTimes

import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.Madhab
import com.batoulapps.adhan.PrayerTimes
import com.batoulapps.adhan.data.DateComponents
import  com.mera.islam.duaazkar.core.extensions.log
import  com.mera.islam.duaazkar.core.utils.OneTimeLocation
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.util.Date
import javax.inject.Inject

class PrayerUtils @Inject constructor(
    private val location: OneTimeLocation
) {

    suspend fun calculatePrayer(prayerTimeListener: (localDateTime: LocalDateTime, prayers: Prayers) -> Unit) {
        location.getLastKnownLocation()?.let { location ->
            val localDateTime = LocalDateTime.now()

            val coordinates = Coordinates(location.latitude, location.longitude)
            val date = DateComponents(
                localDateTime.year,
                localDateTime.monthValue,
                localDateTime.dayOfMonth
            )
            val params = CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters
            params?.madhab = Madhab.SHAFI
            val prayerTimes = PrayerTimes(coordinates, date, params)

            val fajarTime = getPrayerLocalDateTime(prayerTimes.fajr!!)
            val zoharTime = getPrayerLocalDateTime(prayerTimes.dhuhr!!)
            val asrTime = getPrayerLocalDateTime(prayerTimes.asr!!)
            val magribTime = getPrayerLocalDateTime(prayerTimes.maghrib!!)
            val ishaTime = getPrayerLocalDateTime(prayerTimes.isha!!)

            if (localDateTime.isAfter(ishaTime)) {
                prayerTimeListener(
                    fajarTime.plusMinutes(15).plusDays(1),
                    Prayers.FAJAR
                )
            } else {
                when {
                    localDateTime.isBefore(fajarTime) -> prayerTimeListener(
                        fajarTime.plusMinutes(15),
                        Prayers.FAJAR
                    )

                    localDateTime.isBefore(zoharTime) -> prayerTimeListener(
                        zoharTime.plusMinutes(15),
                        Prayers.ZOHAR
                    )

                    localDateTime.isBefore(asrTime) -> prayerTimeListener(
                        asrTime.plusMinutes(15),
                        Prayers.ASR
                    )

                    localDateTime.isBefore(magribTime) -> prayerTimeListener(
                        magribTime.plusMinutes(15),
                        Prayers.MAGHRIB
                    )

                    localDateTime.isBefore(ishaTime) -> prayerTimeListener(
                        ishaTime.plusMinutes(15),
                        Prayers.ISHA
                    )

                    else -> {}
                }
            }
        } ?: "Unknown location".log()
    }

    private fun getPrayerLocalDateTime(date: Date) =
        ZonedDateTime.ofInstant(Instant.ofEpochMilli(date.time), ZoneId.systemDefault())
            .toLocalDateTime()

}