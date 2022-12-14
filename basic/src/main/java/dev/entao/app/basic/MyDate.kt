@file:Suppress("unused", "RemoveRedundantQualifierName", "MemberVisibilityCanBePrivate")

package dev.entao.app.basic

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by entaoyang@163.com on 2016-09-26.
 *
 * 2016-9-26
 */

val Int.SEC: Long get() = this * 1000L
val Int.MIN: Long get() = this * 60_000L
val Int.HOR: Long get() = this * 3600_000L
val Int.HOUR: Long get() = this * 3600_000L
val Int.DAY: Long get() = this * 24 * 3600_000L


val Timestamp.myDate: MyDate get() = MyDate(this.time)
val java.sql.Date.myDate: MyDate get() = MyDate(this.time)
val java.sql.Time.myDate: MyDate get() = MyDate(this.time)
val java.util.Date.myDate: MyDate get() = MyDate(this.time)

class MyDate(time: Long = System.currentTimeMillis(), locale: Locale = Locale.getDefault()) {
    val calendar: Calendar = Calendar.getInstance(locale)

    init {
        calendar.timeInMillis = time
    }

    val toDate: java.util.Date get() = java.util.Date(time)
    val toDateSQL: java.sql.Date get() = java.sql.Date(time)
    val toTimeSQL: java.sql.Time get() = java.sql.Time(time)
    val toTimestamp: java.sql.Timestamp get() = java.sql.Timestamp(time)

    var time: Long
        get() {
            return calendar.timeInMillis
        }
        set(value) {
            calendar.timeInMillis = value
        }

    //2016
    var year: Int
        get() {
            return calendar.get(Calendar.YEAR)
        }
        set(value) {
            calendar.set(Calendar.YEAR, value)
        }

    //[0-11], 8
    var month: Int
        get() {
            return calendar.get(Calendar.MONTH)
        }
        set(value) {
            calendar.set(Calendar.MONTH, value)
        }

    //[1-31],  26
    var day: Int
        get() {
            return calendar.get(Calendar.DAY_OF_MONTH)
        }
        set(value) {
            calendar.set(Calendar.DAY_OF_MONTH, value)
        }

    var dayOfYear: Int
        get() {
            return calendar.get(Calendar.DAY_OF_YEAR)
        }
        set(value) {
            calendar.set(Calendar.DAY_OF_YEAR, value)
        }

    //[0-23]
    var hour: Int
        get() {
            return calendar.get(Calendar.HOUR_OF_DAY)
        }
        set(value) {
            calendar.set(Calendar.HOUR_OF_DAY, value)
        }

    //[0-59]
    var minute: Int
        get() {
            return calendar.get(Calendar.MINUTE)
        }
        set(value) {
            calendar.set(Calendar.MINUTE, value)
        }

    //[0-59]
    var second: Int
        get() {
            return calendar.get(Calendar.SECOND)
        }
        set(value) {
            calendar.set(Calendar.SECOND, value)
        }

    //[0-999]
    var millSecond: Int
        get() {
            return calendar.get(Calendar.MILLISECOND)
        }
        set(value) {
            calendar.set(Calendar.MILLISECOND, value)
        }


    var week: Int
        get() {
            return calendar.get(Calendar.DAY_OF_WEEK)
        }
        set(value) {
            calendar.set(Calendar.DAY_OF_WEEK, value)
        }

    val isSunday: Boolean get() = week == Calendar.SUNDAY
    val isMonday: Boolean get() = week == Calendar.MONDAY
    val isTuesday: Boolean get() = week == Calendar.TUESDAY
    val isWednesday: Boolean get() = week == Calendar.WEDNESDAY
    val isThursday: Boolean get() = week == Calendar.THURSDAY
    val isFriday: Boolean get() = week == Calendar.FRIDAY
    val isSaturday: Boolean get() = week == Calendar.SATURDAY


    fun addYear(n: Int): MyDate {
        calendar.add(Calendar.YEAR, n)
        return this
    }

    fun addMonth(n: Int): MyDate {
        calendar.add(Calendar.MONTH, n)
        return this
    }

    fun addDay(n: Int): MyDate {
        calendar.add(Calendar.DAY_OF_MONTH, n)
        return this
    }

    fun addHour(n: Int): MyDate {
        calendar.add(Calendar.HOUR_OF_DAY, n)
        return this
    }

    fun addMinute(n: Int): MyDate {
        calendar.add(Calendar.MINUTE, n)
        return this
    }

    fun addSecond(n: Int): MyDate {
        calendar.add(Calendar.SECOND, n)
        return this
    }

    fun addMillSecond(n: Int): MyDate {
        calendar.add(Calendar.MILLISECOND, n)
        return this
    }

    //yyyy-MM-dd HH:mm
    fun formatDateTimeShort(): String {
        return format(FORMAT_DATE_TIME_NO_SEC)
    }

    //yyyy-MM-dd HH:mm:ss
    fun formatDateTime(): String {
        return format(FORMAT_DATE_TIME)
    }

    //yyyy-MM-dd HH:mm:ss.SSS
    fun formatDateTimeX(): String {
        return format(FORMAT_DATE_TIME_X)
    }

    fun formatTimestamp(): String {
        return format(FORMAT_DATE_TIMESTAMP)
    }

    fun formatYearMonth(): String {
        return format("yyyy-MM")
    }

    //yyyy-MM-dd
    fun formatDate(): String {
        return format(FORMAT_DATE)
    }

    //HH:mm:ss
    fun formatTime(): String {
        return format(FORMAT_TIME)
    }

    //HH:mm:ss.SSS
    fun formatTimeX(): String {
        return format(FORMAT_TIME_X)
    }

    fun format(pattern: String): String {
        return format(time, pattern)
    }

    fun formatShort(): String {
        val now = MyDate()
        if (now.year != year) {
            return formatDate()
        }
        if (now.dayOfYear != dayOfYear) {
            return format("M-d")
        }
        return format("H:mm")
    }

    fun formatDuration(seconds: Long): String {
        if (seconds < 60) {
            return "${seconds}???"
        }
        if (seconds < 60 * 60) {
            return "${seconds / 60}???${seconds % 60}???"
        }
        return "${seconds / 3600}???${seconds % 3600 / 60}???${seconds % 60}???"
    }

    fun formatTemp(): String {
        return format("yyyyMMdd_HHmmss_SSS")
    }

    val shortTime: String
        get() {
            val now = MyDate()
            if (now.year != this.year) {
                return this.format("yy-MM-dd HH:mm")
            }
            if (now.dayOfYear != this.dayOfYear) {
                return this.format("MM-dd HH:mm")
            }
            return this.format("HH:mm")
        }

    companion object {
        const val FORMAT_DATE = "yyyy-MM-dd"
        const val FORMAT_TIME = "HH:mm:ss"
        const val FORMAT_TIME_X = "HH:mm:ss.SSS"
        const val FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss"
        const val FORMAT_DATE_TIME_NO_SEC = "yyyy-MM-dd HH:mm"
        const val FORMAT_DATE_TIME_X = "yyyy-MM-dd HH:mm:ss.SSS"
        const val FORMAT_DATE_TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS"

        fun format(date: Long, pattern: String): String {
            val ff = SimpleDateFormat(pattern, Locale.getDefault())
            return ff.format(Date(date))
        }

        fun makeDate(year: Int, month: Int, day: Int): Long {
            val c = Calendar.getInstance(Locale.getDefault())
            c.set(year, month, day)
            return c.timeInMillis
        }

        fun makeTime(hour: Int, minute: Int, second: Int = 0): Long {
            val c = Calendar.getInstance(Locale.getDefault())
            c.set(0, 0, 0, hour, minute, second)
            return c.timeInMillis
        }

        fun parse(format: String, dateStr: String): MyDate? {
            val ff = SimpleDateFormat(format, Locale.getDefault())
            try {
                val d = ff.parse(dateStr)
                if (d != null) {
                    return MyDate(d.time)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return null
        }

        fun parseTime(s: String?): MyDate? {
            if (s == null || s.length < 6) {
                return null
            }
            return parse(FORMAT_TIME, s)
        }

        fun parseTimeX(s: String?): MyDate? {
            if (s == null || s.length < 6) {
                return null
            }
            return parse(FORMAT_TIME_X, s)
        }

        fun parseDate(s: String?): MyDate? {
            if (s == null || s.length < 6) {
                return null
            }
            return parse(FORMAT_DATE, s)
        }

        fun parseDateTime(s: String?): MyDate? {
            if (s == null || s.length < 6) {
                return null
            }
            return parse(FORMAT_DATE_TIME, s)
        }

        fun parseDateTimeX(s: String?): MyDate? {
            if (s == null || s.length < 6) {
                return null
            }
            return parse(FORMAT_DATE_TIME_X, s)
        }

        fun parseTimestamp(s: String?): MyDate? {
            if (s == null || s.length < 6) {
                return null
            }
            return parse(FORMAT_DATE_TIMESTAMP, s)
        }

        private var tmpFileN: Long = 1
        fun tmpFile(): String {
            val s = MyDate().format("yyyyMMdd_HHmmss_SSS")
            ++tmpFileN
            return "$s-$tmpFileN"
        }
    }
}

