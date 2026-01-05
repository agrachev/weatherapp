package ru.agrachev.weather.forecast.data.local.processor

import ru.agrachev.core.domain.repository.LocalRepository
import ru.agrachev.feature.weather.forecast.persistence.proto.WeatherForecastHolder
import java.io.InputStream
import java.io.OutputStream

internal object WeatherForecastSerializationApi :
    LocalRepository.SerializationApi<WeatherForecastHolder> {

    override fun getDefaultInstance(): WeatherForecastHolder =
        WeatherForecastHolder.getDefaultInstance()

    override fun parseFrom(input: InputStream): WeatherForecastHolder =
        WeatherForecastHolder.parseFrom(input)

    override fun writeTo(t: WeatherForecastHolder, output: OutputStream) =
        t.writeTo(output)
}
