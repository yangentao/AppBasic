package dev.entao.app.basic

import kotlin.jvm.internal.CallableReference
import kotlin.jvm.internal.FunctionReference
import kotlin.reflect.*
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaMethod


typealias Prop = KProperty<*>
typealias Prop0 = KProperty0<*>
typealias Prop1 = KProperty1<*, *>


val KType.genericArgs: List<KTypeProjection> get() = this.arguments.filter { it.variance == KVariance.INVARIANT }
val KType.isGeneric: Boolean get() = this.arguments.isNotEmpty()

val KFunction<*>.paramName1: String? get() = this.valueParameters.firstOrNull()?.name


/*
 * class A{
 *      val set:HashSet<String> = HashSet()
 * }
 * A::set.firstGenericType => String::class
 */
val KProperty<*>.firstGenericType: KClass<*>? get() = this.returnType.arguments.firstOrNull()?.type?.classifier as? KClass<*>

val KFunction<*>.ownerClass: KClass<*>?
    get() {
        if (this is FunctionReference) {
            if (this.boundReceiver != CallableReference.NO_RECEIVER) {
                return this.boundReceiver::class
            }
            val c = this.owner as? KClass<*>
            if (c != null) {
                return c
            }
        } else {
            return this.javaMethod?.declaringClass?.kotlin
        }
        return null
    }
val KFunction<*>.ownerObject: Any?
    get() {
        if (this is FunctionReference) {
            if (this.boundReceiver != CallableReference.NO_RECEIVER) {
                return this.boundReceiver
            }
        }
        return null
    }


val KProperty<*>.ownerClass: KClass<*>?
    get() {
        if (this is CallableReference) {
            if (this.boundReceiver != CallableReference.NO_RECEIVER) {
                return this.boundReceiver::class
            }
            val c = this.owner as? KClass<*>
            if (c != null) {
                return c
            }
        } else {
            return this.javaField?.declaringClass?.kotlin
        }

        return null
    }

val KProperty<*>.ownerObject: Any?
    get() {
        if (this is CallableReference) {
            if (this.boundReceiver != CallableReference.NO_RECEIVER) {
                return this.boundReceiver::class
            }
        }
        return null
    }


fun KType.isClass(kcls: KClass<*>): Boolean {
    return this.classifier == kcls
}



fun KProperty<*>.getInstValue(inst: Any): Any? {
    if (this.getter.parameters.isEmpty()) {
        return this.getter.call()
    }
    return this.getter.call(inst)
}

fun KProperty<*>.getBindValue(): Any? {
    if (this.getter.parameters.isEmpty()) {
        return this.getter.call()
    }
    return null
}


fun KMutableProperty<*>.setInstValue(inst: Any, value: Any?) {
    this.setter.call(inst, value)
}

val KProperty<*>.isPublic: Boolean get() = this.visibility == KVisibility.PUBLIC


inline fun <reified T : Any> KClass<T>.newInstance(argCls: KClass<*>, argValue: Any): T {
    val c = this.constructors.first { it.parameters.size == 1 && it.parameters.first().type.classifier == argCls }
    return c.call(argValue)
}


@Suppress("UNCHECKED_CAST")
fun <T : Any> Any.javaInvoke(method: String, vararg args: Any): T? {
    val m = this.javaClass.getMethod(method) ?: error("No method named: $method")
    m.isAccessible = true
    return m.invoke(this, *args) as? T
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> Any.javaFieldGet(field: String): T? {
    val f = this.javaClass.getField(field) ?: error("No field named: $field")
    f.isAccessible = true
    return f.get(this) as? T
}

fun Any.javaFieldSet(field: String, value: Any?) {
    val f = this.javaClass.getField(field) ?: error("No field named: $field")
    f.isAccessible = true
    f.set(this, value)
}

