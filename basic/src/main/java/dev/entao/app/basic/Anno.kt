@file:Suppress("unused")

package dev.entao.app.basic

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.javaField

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class KeepMembers

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class KeepNames


@MustBeDocumented
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.LOCAL_VARIABLE)
@Retention(AnnotationRetention.BINARY)
annotation class PX

@MustBeDocumented
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.LOCAL_VARIABLE)
@Retention(AnnotationRetention.BINARY)
annotation class DP

@MustBeDocumented
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.LOCAL_VARIABLE)
@Retention(AnnotationRetention.BINARY)
annotation class SP


@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Label(val value: String)


@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Name(val value: String)

@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultValue(val value: String)


@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Required


enum class ExcludeFor {
    ALL, SQL, JSON
}


@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Exclude(val value: ExcludeFor = ExcludeFor.ALL)

//字段长度--字符串
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Length(val value: Int)


val KProperty<*>.userName: String
    get() {
        return this.findAnnotation<Name>()?.value ?: this.name
    }

val KClass<*>.userName: String
    get() {
        return this.findAnnotation<Name>()?.value ?: this.simpleName!!
    }

val KParameter.userName: String
    get() {
        return this.findAnnotation<Name>()?.value ?: this.name
        ?: throw IllegalStateException("参数没有名字")
    }


val KFunction<*>.userName: String
    get() {
        return this.findAnnotation<Name>()?.value ?: this.name
    }

val KProperty<*>.nameWithClass: String
    get() {
        val clsName = this.javaField?.declaringClass?.kotlin?.userName
        val fname = this.findAnnotation<Name>()?.value ?: this.name
        return clsName!! + "." + fname
    }


val KClass<*>.userLabel: String
    get() {
        return this.findAnnotation<Label>()?.value ?: this.simpleName!!
    }
val KProperty<*>.userLabel: String
    get() {
        return this.findAnnotation<Label>()?.value ?: this.userName
    }

val KFunction<*>.userLabel: String
    get() {
        return this.findAnnotation<Label>()?.value ?: this.userName
    }


val KProperty<*>.userDefaultValueText: String?
    get() {
        return this.findAnnotation<DefaultValue>()?.value
    }


val KProperty<*>.labelValue: String?
    get() {
        return this.findAnnotation<Label>()?.value
    }


val KFunction<*>.labelValue: String?
    get() {
        return this.findAnnotation<Label>()?.value
    }

