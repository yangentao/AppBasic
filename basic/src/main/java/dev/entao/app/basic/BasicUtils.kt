package dev.entao.app.basic

import android.os.Build
import android.util.Log
import java.lang.ref.WeakReference
import java.util.*

const val KB: Long = 1024L
const val MB: Long = 1024 * 1024
const val GB: Long = 1024 * 1024 * 1024
const val UTF8 = "UTF-8"

typealias BlockUnit = () -> Unit

val UUID.hexText: String get() = String.format("%x%016x", this.mostSignificantBits, this.leastSignificantBits)
fun makeTempName(ext: String = "tmp"): String {
    val dotExt = when {
        ext.isEmpty() -> ""
        ext.startsWith(".") -> ext
        else -> ".$ext"
    }
    return UUID.randomUUID().hexText + dotExt
}

inline fun <R> safe(block: () -> R): R? {
    try {
        return block()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return null
}

inline fun <T : AutoCloseable, R> T.closeAuto(block: (T) -> R): R {
    try {
        return block(this)
    } finally {
        try {
            this.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}

class WeakRef<T : Any>(obj: T? = null) {
    private var weakRef: WeakReference<T>? = null

    init {
        if (obj != null) {
            weakRef = WeakReference(obj)
        }
    }


    var value: T?
        get() = weakRef?.get()
        set(value) {
            weakRef = null
            if (value != null) {
                weakRef = WeakReference(value)
            }
        }

}


operator fun StringBuilder.plusAssign(s: String) {
    this.append(s)
}

operator fun StringBuilder.plusAssign(ch: Char) {
    this.append(ch)
}

fun String.notEmpty(block: (String) -> Unit): String {
    if (this.isNotEmpty()) {
        block(this)
    }
    return this
}

fun buildInfoDump() {
    val tag = "xlog"
    Log.d(tag, "MODEL: ${Build.MODEL}")
    Log.d(tag, "DISPLAY: ${Build.DISPLAY}")
    Log.d(tag, "DEVICE: ${Build.DEVICE}")
    Log.d(tag, "BOARD: ${Build.BOARD}")
    Log.d(tag, "BRAND: ${Build.BRAND}")
    Log.d(tag, "HARDWARE: ${Build.HARDWARE}")
    Log.d(tag, "MANUFACTURER: ${Build.MANUFACTURER}")
    Log.d(tag, "PRODUCT: ${Build.PRODUCT}")
    Log.d(tag, "VERSION.RELEASE: ${Build.VERSION.RELEASE}")
    Log.d(tag, "VERSION.SDK_INT: ${Build.VERSION.SDK_INT}")
    Log.d(tag, "VERSION.BASE_OS: ${Build.VERSION.BASE_OS}")
    Log.d(tag, "VERSION.CODENAME: ${Build.VERSION.CODENAME}")
    Log.d(tag, "VERSION.SECURITY_PATCH: ${Build.VERSION.SECURITY_PATCH}")
}
