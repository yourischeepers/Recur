package me.partypronl.recur.util.serialization

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.createType

fun <T : Any> createNavType(
    clazz: KClass<T>,
    isNullableAllowed: Boolean = false,
): Pair<KType, NavType<Any?>> =
    clazz.createType(nullable = isNullableAllowed) to object : NavType<Any?>(isNullableAllowed = isNullableAllowed) {
        override fun get(bundle: Bundle, key: String): Any? {
            return bundle.getString(key)?.let(::parseValue)
        }

        override fun parseValue(value: String): Any? {
            return Json.decodeFromString(serializer(clazz.createType(nullable = isNullableAllowed)), Uri.decode(value))
        }

        override fun serializeAsValue(value: Any?): String {
            return Uri.encode(Json.encodeToString(serializer(clazz.createType(nullable = isNullableAllowed)), value))
        }

        override fun put(bundle: Bundle, key: String, value: Any?) {
            bundle.putString(key, serializeAsValue(value))
        }
    }
