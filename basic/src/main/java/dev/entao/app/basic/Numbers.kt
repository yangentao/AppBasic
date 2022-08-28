@file:Suppress("unused")

package dev.entao.app.basic


val Int.high0: Byte get() = ((this ushr 24) and 0x00ff).toByte()
val Int.high1: Byte get() = ((this ushr 16) and 0x00ff).toByte()
val Int.high2: Byte get() = ((this ushr 8) and 0x00ff).toByte()
val Int.high3: Byte get() = (this and 0x00ff).toByte()

val Int.low0: Byte get() = (this and 0x00ff).toByte()
val Int.low1: Byte get() = ((this ushr 8) and 0x00ff).toByte()
val Int.low2: Byte get() = ((this ushr 16) and 0x00ff).toByte()
val Int.low3: Byte get() = ((this ushr 24) and 0x00ff).toByte()


val Short.lowByte: Byte get() = (this.toInt() and 0x00FF).toByte()


val Short.highByte: Byte get() = ((this.toInt() ushr 8) and 0x00ff).toByte()


fun Int.hasBits(bitsFlag: Int): Boolean {
    return (this and bitsFlag) != 0
}

fun Int.removeBits(bitsFlag: Int): Int {
    return this and bitsFlag.inv()
}

fun makeShort(hi: Byte, lo: Byte): Short {
    return ((hi.toInt() shl 8) + (lo.toInt() and 0xff)).toShort()
}

fun makeIntValue(h1: Byte, h2: Byte, h3: Byte, h4: Byte): Int {
    return (h1.toInt() shl 24) or ((h2.toInt() shl 16) and 0x00ff0000) or ((h3.toInt() shl 8) and 0x0000ff00) or (h4.toInt() and 0x000000ff)
}


val LongComparator: Comparator<Long> = Comparator { o1, o2 ->
    when {
        o1 > o2 -> 1
        o1 < o2 -> -1
        else -> 0
    }
}
