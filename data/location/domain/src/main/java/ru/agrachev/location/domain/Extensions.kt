package ru.agrachev.location.domain

import ru.agrachev.core.domain.model.GeoLocation

typealias GeoLocationResult = Result<GeoLocation>

fun <T> T.asResult() = Result.success(this)
