@file:Suppress("MemberVisibilityCanBePrivate")

package dev.entao.app.basic

import java.sql.Date
import java.sql.Time
import kotlin.reflect.KClassifier
import kotlin.reflect.KProperty

val KProperty<*>.defaultValueByType: Any
    get() {
        return TextConvert.defaultValueOf<Any>(this)
    }

fun KProperty<*>.valueByText(text: String): Any {
    return TextConvert.textToValue(text, this)
}

interface ITextConvert {
    fun fromText(text: String): Any?
    fun toText(value: Any): String {
        return value.toString()
    }

    val defaultValue: Any
}

object TextConvert {

    private val allConverts = hashMapOf<KClassifier, ITextConvert>(
        String::class to StringText,
        Boolean::class to BoolText,
        java.lang.Boolean::class to BoolText,
        Byte::class to ByteText,
        java.lang.Byte::class to ByteText,
        Short::class to ShortText,
        java.lang.Short::class to ShortText,
        Char::class to CharText,
        java.lang.Character::class to CharText,
        Int::class to IntText,
        java.lang.Integer::class to IntText,
        Long::class to LongText,
        java.lang.Long::class to LongText,
        Float::class to FloatText,
        java.lang.Float::class to FloatText,
        Double::class to DoubleText,
        java.lang.Double::class to DoubleText,
        java.sql.Date::class to SQLDateText,
        java.sql.Time::class to SQLTimeText,
        java.util.Date::class to UtilDateText
    )

    fun putConvert(cls: KClassifier, c: ITextConvert) {
        allConverts[cls] = c
    }

    fun getConvert(cls: KClassifier): ITextConvert? {
        return allConverts[cls]
    }


    @Suppress("UNCHECKED_CAST")
    fun <V> defaultValueOf(p: KProperty<*>): V {
        val c = getConvert(p.returnType.classifier!!) ?: error("No Converter defined, property:${p.nameWithClass}")
        return c.defaultValue as V
    }

    @Suppress("UNCHECKED_CAST")
    fun <V> textToValue(v: String, property: KProperty<*>): V {
        val cls = property.returnType.classifier!!
        if (cls == String::class) return v as V
        val c = getConvert(cls) ?: error("No Converter defined for property: ${property.nameWithClass}, text: $v")
        return c.fromText(v) as V
    }
}


object StringText : ITextConvert {
    override val defaultValue: Any = ""
    override fun fromText(text: String): Any {
        return text
    }
}

object BoolText : ITextConvert {
    override val defaultValue: Any = false
    override fun fromText(text: String): Any {
        return text == "true"
    }
}

object ByteText : ITextConvert {
    override val defaultValue: Any = 0
    override fun fromText(text: String): Any? {
        return text.toByteOrNull()
    }
}

object ShortText : ITextConvert {
    override val defaultValue: Any = 0
    override fun fromText(text: String): Any? {
        return text.toShortOrNull()
    }
}

object CharText : ITextConvert {
    override val defaultValue: Any = 0
    override fun fromText(text: String): Any? {
        return text.firstOrNull()
    }
}

object IntText : ITextConvert {
    override val defaultValue: Any = 0
    override fun fromText(text: String): Any? {
        return text.toIntOrNull()
    }
}

object LongText : ITextConvert {
    override val defaultValue: Any = 0
    override fun fromText(text: String): Any? {
        return text.toLongOrNull()
    }
}

object FloatText : ITextConvert {
    override val defaultValue: Any = 0
    override fun fromText(text: String): Any? {
        return text.toFloatOrNull()
    }
}

object DoubleText : ITextConvert {
    override val defaultValue: Any = 0
    override fun fromText(text: String): Any? {
        return text.toDoubleOrNull()
    }
}


//yyy-MM-dd
object SQLDateText : ITextConvert {
    override val defaultValue: Any = java.sql.Date(0)
    override fun fromText(text: String): Any? {
        return MyDate.parseDate(text)?.toDateSQL
    }

    override fun toText(value: Any): String {
        return MyDate((value as Date).time).formatDate()
    }
}

//"HH:mm:ss"
object SQLTimeText : ITextConvert {
    override val defaultValue: Any = java.sql.Time(0)
    override fun fromText(text: String): Any? {
        return MyDate.parseTime(text)?.toTimeSQL
    }

    override fun toText(value: Any): String {
        return MyDate((value as Time).time).formatTime()
    }
}


//"yyyy-MM-dd HH:mm:ss.SSS"
object UtilDateText : ITextConvert {


    override val defaultValue: Any = java.util.Date(0)
    override fun fromText(text: String): Any? {
        return MyDate.parseDateTimeX(text)?.toDate
    }

    override fun toText(value: Any): String {
        return MyDate((value as java.util.Date).time).formatDateTimeX()
    }
}


