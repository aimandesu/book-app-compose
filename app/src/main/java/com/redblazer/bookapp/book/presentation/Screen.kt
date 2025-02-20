package com.redblazer.bookapp.book.presentation

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data class BookDetail(val bookId: Int) : Screen()

    @Serializable
    data class SearchBook(val bookTitle: String) : Screen()

    @Serializable
    data class CreateUpdateBook(val bookId: BookIdNullable) : Screen()

}

@Serializable
@Parcelize
class BookIdNullable(
    val id: Int?,
) : Parcelable

val bookIdNullable = object: NavType<BookIdNullable>(
    isNullableAllowed = true,
){
    override fun put(bundle: Bundle, key: String, value: BookIdNullable) {
        bundle.putParcelable(key, value)
    }
    override fun get(bundle: Bundle, key: String): BookIdNullable {
        return bundle.getParcelable<BookIdNullable>(key) as BookIdNullable
    }

    override fun parseValue(value: String): BookIdNullable {
        return Json.decodeFromString<BookIdNullable>(value)
    }

    override fun serializeAsValue(value: BookIdNullable): String {
        return Json.encodeToString(value)
    }

    override val name = "BookIdNullable"

}