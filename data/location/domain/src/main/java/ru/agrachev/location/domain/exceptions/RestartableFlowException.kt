package ru.agrachev.location.domain.exceptions

import ru.agrachev.location.domain.repository.RestartableLocationRepository

class RestartableFlowException(
    reason: Throwable,
    val repository: RestartableLocationRepository,
) : Exception(reason)
