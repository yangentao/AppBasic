package dev.entao.app.basic


infix fun <T : Comparable<T>> T.cutby(p: Pair<T, T>): T {
    if (this < p.first) return p.first
    if (this > p.second) return p.second
    return this
}

infix fun <T : Comparable<T>> Pair<T, T>.cut(value: T): T {
    if (value < this.first) return this.first
    if (value > this.second) return this.second
    return value
}


operator fun <T : Comparable<T>> Pair<T, T>.contains(value: T): Boolean {
    return value >= this.first && value <= this.second
}

//i=include, e=exclude
// "a" in "a" ie "c"  => true
// "a" in "a" ei "c"  => false
class RangeX<T : Comparable<T>>(val start: T, val end: T, val includeStart: Boolean, val includeEnd: Boolean) {
    operator fun contains(value: T): Boolean {
        if (includeStart) {
            if (value < start) return false
        } else {
            if (value <= start) return false
        }
        if (includeEnd) {
            if (value > end) return false
        } else {
            if (value >= end) return false
        }
        return true
    }
}

//[], include, include
infix fun <T : Comparable<T>> T.ii(b: T): RangeX<T> {
    return RangeX(this, b, includeStart = true, includeEnd = true)
}

//(), exclude, exclude
infix fun <T : Comparable<T>> T.ee(b: T): RangeX<T> {
    return RangeX(this, b, includeStart = false, includeEnd = false)
}

//[), include, exclude
infix fun <T : Comparable<T>> T.ie(b: T): RangeX<T> {
    return RangeX(this, b, includeStart = true, includeEnd = false)
}

//(], exclude, include
infix fun <T : Comparable<T>> T.ei(b: T): RangeX<T> {
    return RangeX(this, b, includeStart = false, includeEnd = true)
}

