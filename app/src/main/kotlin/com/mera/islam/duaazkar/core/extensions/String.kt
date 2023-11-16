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

fun String.findWordPositions(word: String): List<Int> {
    val pattern = Regex("(?<!\\w)($word)(?!\\w)", RegexOption.IGNORE_CASE)
    val matches = pattern.findAll(this)
    return matches.map { it.range.first }.toList()
}

fun main() {
    val text = "This is a SAMPLE,; text with a (sample) word, and also a sample-word!"
    val wordToFind = "sample"

    val positions = text.findWordPositions(wordToFind)
    println("The starting positions of '$wordToFind' are: $positions")
}
