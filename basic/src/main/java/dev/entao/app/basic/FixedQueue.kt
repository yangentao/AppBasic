package dev.entao.app.basic

import java.util.*

/**
 * Created by yangentao on 2015/11/22.
 * entaoyang@163.com
 */
class FixedQueue<T>(val max: Int, val list: MutableList<T> = LinkedList<T>()) : Iterable<T> {

    val size: Int get() = list.size

    @Synchronized
    fun clear() {
        list.clear()
    }

    @Synchronized
    fun add(value: T) {
        list.add(value)
        if (list.size > max) {
            list.removeLastOrNull()
        }
    }

    operator fun get(index: Int): T {
        return list[index]
    }

    override fun iterator(): Iterator<T> {
        return list.iterator()
    }


    @Synchronized
    fun toList(): ArrayList<T> {
        return ArrayList<T>(list)
    }
}
