package  com.mera.islam.duaazkar.core.extensions

import android.util.Log
import  com.mera.islam.duaazkar.BuildConfig

fun List<Any>.listToString(): String {
    return this.joinToString(",")
}

fun String.log() {
    if (BuildConfig.DEBUG)
    Log.i("DuaAndAzkar", "log: $this")
}

inline fun build(builder: StringBuilder.() -> Unit): String = StringBuilder().apply {
    builder()
}.toString()

