@file:Suppress("SpellCheckingInspection")

package ru.agrachev.location.data.remote.dto

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
internal data class WhoIsDto(
    @Json(name = "About_Us") val aboutUs: String?,
    val ip: String,
    val success: Boolean,
    val type: String,
    val continent: String,
    @Json(name = "continent_code") val continentCode: String,
    val country: String,
    @Json(name = "country_code") val countryCode: String,
    val region: String,
    @Json(name = "region_code") val regionCode: String,
    val city: String,
    val latitude: Double,
    val longitude: Double,
    @Json(name = "is_eu") val isEU: Boolean,
    val postal: String,
    @Json(name = "calling_code") val callingCode: String,
    val capital: String,
    val borders: String,
    val flag: FlagDto,
    val connection: ConnectionDto,
    val timezone: TimezoneDto,
)

@Keep
@JsonClass(generateAdapter = true)
internal data class FlagDto(
    val img: String,
    val emoji: String,
    @Json(name = "emoji_unicode") val emojiUnicode: String,
)

@Keep
@JsonClass(generateAdapter = true)
internal data class ConnectionDto(
    val asn: Int,
    val org: String,
    val isp: String,
    val domain: String,
)

@Keep
@JsonClass(generateAdapter = true)
internal data class TimezoneDto(
    val id: String,
    val abbr: String,
    @Json(name = "is_dst") val isDst: Boolean,
    val offset: Int,
    val utc: String,
    @Json(name = "current_time") val currentTime: String,
)
