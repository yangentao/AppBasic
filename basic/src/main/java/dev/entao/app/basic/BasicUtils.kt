package dev.entao.app.basic

import java.lang.ref.WeakReference

typealias BlockUnit = () -> Unit

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