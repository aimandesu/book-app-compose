package com.redblazer.bookapp.book.domain.model

import java.util.Date

//@OptIn(ExperimentalSerializationApi::class)
//@Serializer(forClass = Date::class)
//class DateSerializer : KSerializer<Date> {
//    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//
//    //override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)
//
//    override fun serialize(encoder: Encoder, value: Date) {
//        val string = dateFormat.format(value)
//        encoder.encodeString(string)
//    }
//
//    override fun deserialize(decoder: Decoder): Date {
//        val string = decoder.decodeString()
//        return dateFormat.parse(string) ?: throw SerializationException("Invalid date format")
//    }
//}

//val BookModelNavType = object : NavType<BookModel>(isNullableAllowed = false) {
//    override fun put(bundle: Bundle, key: String, value: BookModel) {
//        bundle.putParcelable(key, value)
//    }
//
//    override fun get(bundle: Bundle, key: String): BookModel {
//        return bundle.getParcelable<BookModel>(key) as BookModel
//    }
//
//    override fun parseValue(value: String): BookModel {
//        return Json.decodeFromString<BookModel>(value)
//    }
//
//    override fun serializeAsValue(value: BookModel): String {
//        return Json.encodeToString(value)
//    }
//
//}

//@Serializable
//@Parcelize
data class BookModel(
    val id: Int?,
    val isbn: String,
    val name: String,
    val year: Int,
    val author: String,
    val description: String,
    val image: String,
//    @Serializable(with = DateSerializer::class)
    val createdAt: Date,
//    @Serializable(with = DateSerializer::class)
    val updatedAt: Date,
)
//    : Parcelable
