@file:Suppress("unused")

package dev.entao.app.basic

import java.io.*
import java.nio.charset.Charset
import java.util.zip.ZipInputStream

interface Progress {
    fun onProgressStart(total: Int)
    fun onProgress(current: Int, total: Int, percent: Int)
    fun onProgressFinish()
}

fun <T : Closeable> T.closeSafe() {
    try {
        this.close()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

inline fun <T : Closeable, R> T.useSafe(block: (T) -> R): R? {
    try {
        return block(this)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            this.close()
        } catch (ex: Exception) {
            //ex.printStackTrace()
        }
    }
    return null
}

inline fun <T : Closeable> T.closeAfter(block: (T) -> Unit) {
    try {
        block(this)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            this.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}

fun InputStream.readText(charset: Charset = Charsets.UTF_8): String {
    val bs = this.readBytes()
    return String(bs, charset)
}

fun ZipInputStream.unzipFirst(file: File): Boolean {
    val fos = FileOutputStream(file)
    val b = unzipFirst(fos)
    fos.closeSafe()
    return b
}

fun ZipInputStream.unzipFirst(os: OutputStream): Boolean {
    val e = nextEntry
    if (e != null) {
        copyTo(os)
        closeEntry()
        return true
    }
    return false
}

fun ZipInputStream.unzipByName(name: String, os: OutputStream): Boolean {
    var e = nextEntry
    while (e != null) {
        if (e.name == name && !e.isDirectory) {
            copyTo(os)
            closeEntry()
            return true
        }
        closeEntry()
        e = nextEntry
    }
    return false
}