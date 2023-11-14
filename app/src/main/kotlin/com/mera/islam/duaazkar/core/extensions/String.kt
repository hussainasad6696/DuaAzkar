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

fun String.findWordPosition(word: String): Int {
    val pattern = Regex("\\b${Regex.escape(word)}\\b", RegexOption.IGNORE_CASE)
    val matchResult = pattern.find(this)
    return matchResult?.range?.start ?: -1
}