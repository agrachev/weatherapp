package ru.agrachev.weather.forecast.data.local.processor

import ru.agrachev.core.domain.repository.LocalRepository
import ru.agrachev.feature.weather.forecast.persistence.proto.WeatherForecastHolder
import ru.agrachev.feature.weather.forecast.persistence.proto.copy
import ru.agrachev.weather.forecast.data.local.mappers.toDomainModel
import ru.agrachev.weather.forecast.data.local.mappers.toProtoModel
import ru.agrachev.weather.forecast.domain.model.WeatherForecast

internal object WeatherForecastDataTransformer :
    LocalRepository.DataTransformer<WeatherForecastHolder, WeatherForecast> {

    override suspend fun WeatherForecastHolder.readTransform() =
        (if (this.hasData()) this.data else null)?.toDomainModel()

    override suspend fun WeatherForecastHolder.writeTransform(
        writeData: WeatherForecast,
    ) = this.copy {
        data = writeData.toProtoModel()
    }
}
